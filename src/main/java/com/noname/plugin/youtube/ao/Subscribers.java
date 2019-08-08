package com.noname.plugin.youtube.ao;

import net.java.ao.Entity;

public interface Subscribers extends Entity
{
	Youtubers getYoutuber();
	void setYoutuber(Youtubers youtuber);

	Users getUser();
	void setUser(Users user);
}