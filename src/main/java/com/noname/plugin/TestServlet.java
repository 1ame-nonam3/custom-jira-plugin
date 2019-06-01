package com.noname.plugin;

import com.atlassian.jira.issue.IssueManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.atlassian.velocity.VelocityManager;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import com.google.common.collect.Maps;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class TestServlet extends HttpServlet
{
	private VelocityManager velocityManager;
	private PageBuilderService pageBuilderService;
	private IssueManager im;
	private Component c;
	private TemplateRenderer tr;

	public TestServlet(VelocityManager velocityManager, PageBuilderService pageBuilderService, IssueManager im, Component c, TemplateRenderer tr)
	{
		this.velocityManager = velocityManager;
		this.pageBuilderService = pageBuilderService;
		this.im = im;
		this.c = c;
		this.tr = tr;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.pageBuilderService
				.assembler()
				.resources()
				.requireWebResource("custom-jira-report:test-resource")
				.requireWebResource("custom-jira-report:test-resource2");

		Map<String, Object> context = Maps.newHashMap();
		context.put("myVar", this.c.getIssueCount());
		context.put("ic", this.im.getIssueCount());

		if ("1".equalsIgnoreCase(request.getParameter("test"))) {
			String content = this.velocityManager.getEncodedBody("/templates/", "servlet.vm", "UTF-8", context);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(content);
			response.getWriter().close();
		} else {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("Other case");
			response.getWriter().close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("POST WORKS");
		response.getWriter().close();
	}
}