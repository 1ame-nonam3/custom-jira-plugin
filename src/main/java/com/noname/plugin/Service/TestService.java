package com.noname.plugin.Service;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.configurable.ObjectConfiguration;
import com.atlassian.configurable.ObjectConfigurationException;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.util.JiraHome;
import com.atlassian.jira.service.AbstractService;
import com.atlassian.jira.service.ServiceManager;
import com.noname.plugin.Component;
import com.noname.plugin.youtube.ao.Youtubers;
import com.opensymphony.module.propertyset.PropertySet;

public class TestService extends AbstractService
{
	private ActiveObjects ao;
	private JiraHome jiraHome;
	private Component component;
	private String s = "";

	public TestService(/*JiraHome jiraHome*/)
	{
//		this.jiraHome = jiraHome;
	}

	public void setActiveObjects(ActiveObjects ao)
	{
		this.ao = ao;
	}

	public void setComponent(Component component)
	{
		this.component = component;
	}

	@Override
	public void run()
	{
		SComponent sc = (SComponent)ComponentAccessor.getOSGiComponentInstanceOfType(ISComponent.class);
		Youtubers[] yt = sc.getAO().find(Youtubers.class);
		this.log.error("-------------------------------------------------Im ALIVE-------------------------------------------------");
//		this.log.error("Home: " + this.jiraHome.getHomePath());
		this.log.error(" Нашли: " + yt.length + "чел.");
		this.log.error(this.component.getString());
		this.log.error(this.s);
	}

	@Override
	public void init(PropertySet props) throws ObjectConfigurationException
	{
		super.init(props);
		if (this.hasProperty("testProp")) {
			this.s = this.getProperty("testProp");
		}
	}

	@Override
	public ObjectConfiguration getObjectConfiguration() throws ObjectConfigurationException
	{
		return this.getObjectConfiguration(
			"testservice",
			"services/testservice.xml",
			null
		);
	}
}
