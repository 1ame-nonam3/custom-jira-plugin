package com.noname.plugin;

import com.atlassian.jira.issue.IssueManager;

public class Component
{
	private IssueManager im;
	public Component(IssueManager im)
	{
		this.im = im;
	}

	public String getString()
	{
		return "HELLO MAZAFAKER";
	}

	public long getIssueCount()
	{
		return this.im.getIssueCount();
	}
}
