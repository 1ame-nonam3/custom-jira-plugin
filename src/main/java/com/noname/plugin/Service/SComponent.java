package com.noname.plugin.Service;

import com.atlassian.activeobjects.external.ActiveObjects;

public class SComponent implements ISComponent
{
	private ActiveObjects ao;

	public SComponent(ActiveObjects ao)
	{
		this.ao = ao;
	}

	@Override
	public ActiveObjects getAO()
	{
		return this.ao;
	}
}
