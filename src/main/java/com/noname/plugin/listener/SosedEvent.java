package com.noname.plugin.listener;

public class SosedEvent
{
	private String message;

	public SosedEvent(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return this.message;
	}

	public String toString()
	{
		return "[" + this.message + "]";
	}
}
