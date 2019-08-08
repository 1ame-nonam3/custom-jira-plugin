package com.noname.plugin.youtube.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

public interface Users extends Entity
{
	String getName();
	void setName(String name);

	@ManyToMany(value = Subscribers.class)
	Youtubers getYoutubers();
}
