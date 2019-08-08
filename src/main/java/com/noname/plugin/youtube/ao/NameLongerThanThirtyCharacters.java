package com.noname.plugin.youtube.ao;

import net.java.ao.Entity;
import net.java.ao.schema.Table;

@Table("CUSTOM_NAME")
public interface NameLongerThanThirtyCharacters extends Entity
{
	String getName();
	void setName(String name);
}