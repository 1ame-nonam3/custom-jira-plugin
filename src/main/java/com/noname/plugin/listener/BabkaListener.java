package com.noname.plugin.listener;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.event.user.LoginEvent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BabkaListener implements InitializingBean, DisposableBean
{
	private final Logger logger = Logger.getLogger(this.getClass());

	private final EventPublisher eventPublisher;

	public BabkaListener(EventPublisher eventPublisher)
	{
		this.eventPublisher = eventPublisher;
	}

	public void afterPropertiesSet() throws Exception
	{
		this.eventPublisher.register(this);
	}

	public void destroy() throws Exception
	{
		this.eventPublisher.unregister(this);
	}

	@EventListener
	public void onIssueEvent(IssueEvent event) throws Exception
	{
		this.logger.error(event.toString());
		this.eventPublisher.publish(new SosedEvent("Ah tu padla, hvatit podslushivat"));
	}

	@EventListener
	public void onIssueEvent(LoginEvent event) throws Exception
	{
		this.logger.error(event.toString());
	}

	@EventListener
	public void onIssueEvent(SosedEvent event) throws Exception
	{
		this.logger.error("Mmmm sosed tolko shto skazal: " + event.toString() + ", narkoman naverno");
	}
}
