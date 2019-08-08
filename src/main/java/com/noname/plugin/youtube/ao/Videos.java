package com.noname.plugin.youtube.ao;

import net.java.ao.Entity;

public interface Videos extends Entity
{
	Youtubers getYoutuber();

	String getName();
	void setName(String name);

	int getViews();
	void setViews(int views);
}