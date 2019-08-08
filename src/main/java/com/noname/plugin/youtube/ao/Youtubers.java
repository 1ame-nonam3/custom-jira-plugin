package com.noname.plugin.youtube.ao;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.OneToMany;
import net.java.ao.schema.Default;
import net.java.ao.schema.Indexed;
import net.java.ao.schema.StringLength;
import net.java.ao.schema.Unique;

import java.util.Date;

public interface Youtubers extends Entity
{
	enum Type
	{
		OPEN,
		CLOSED
	}

	@Unique
	@Default("default")
	String getName();
	void setName(String name);

	@StringLength(StringLength.UNLIMITED)
	String getDescription();
	void setDescription(String description);

	Type getType();
	void setType(Type type);

	@Indexed
	@Default("true")
	boolean getIsActive();
	void setIsActive(boolean isActive);

	@OneToMany
	Videos[] getVideos();

	@ManyToMany(value = Subscribers.class)
	Users[] getSubscribers();

	@Default("0")
	int getYear();
	void setYear(int year);

	@Default("0")
	double getPopularity();
	void setPopularity();

	Date getCreatedAt();
	void setCreatedAt(Date date);
}