package com.noname.plugin;

import javax.xml.bind.annotation.XmlElement;

public class Param
{
	@XmlElement
	private String id;

	public String getId()
	{
		return this.id;
	}
}
