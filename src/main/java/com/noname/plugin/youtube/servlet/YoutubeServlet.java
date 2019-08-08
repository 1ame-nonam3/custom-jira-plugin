package com.noname.plugin.youtube.servlet;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.velocity.VelocityManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.noname.plugin.youtube.ao.Subscribers;
import com.noname.plugin.youtube.ao.Users;
import com.noname.plugin.youtube.ao.Youtubers;
import net.java.ao.EntityStreamCallback;
import net.java.ao.Query;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class YoutubeServlet extends HttpServlet
{
	private VelocityManager velocityManager;

	private ActiveObjects activeObjects;

	public YoutubeServlet(VelocityManager velocityManager, ActiveObjects activeObjects)
	{
		this.velocityManager = velocityManager;
		this.activeObjects = activeObjects;
	}

	private void streamUsers()
	{
		List<Users> list = Lists.newArrayList();
		Date date1 = new Date();
		this.activeObjects.stream(
			Users.class,
			Query.select("ID, NAME"),
			new EntityStreamCallback<Users, Integer>()
			{
				@Override
				public void onRowRead(Users us)
				{
					list.add(us);
				}
			}
		);
		Date date2 = new Date();
		System.out.println("STREAM SELECT: " + ((date2.getTime() - date1.getTime() * 1.) / 1000));
	}

	private void getUsers()
	{
		Date date1 = new Date();
		Users[] us = this.activeObjects.find(
			Users.class,
			Query.select()
		);
		Date date2 = new Date();
		System.out.println("GET SELECT: " + ((date2.getTime() - date1.getTime() * 1.) / 1000));
	}

	private Map<Integer, Integer> getSubscribers(Youtubers[] youtubers)
	{
		Map<Integer, Integer> result = Maps.newHashMap();

		Date d1 = new Date();
		for(Youtubers y: youtubers) {
			int cnt = this.activeObjects.count(
				Subscribers.class,
				Query.select().where("YOUTUBER_ID = ?", y.getID())
			);
			result.put(y.getID(), cnt);
		}
		Date d2 = new Date();
		System.out.println("SUBS STREAM SELECT: " + ((d2.getTime() - d1.getTime() * 1.) / 1000));

		return result;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Youtubers[] youtubers = this.activeObjects.find(
			Youtubers.class,
			Query.select()
		);

//		this.getUsers();
//		this.streamUsers();

		Map<String, Object> context = Maps.newHashMap();
		context.put("youtubers", youtubers);
		context.put("subscribers", this.getSubscribers(youtubers));

		String content = this.velocityManager.getEncodedBody("/templates/youtube/", "servlet.vm", "UTF-8", context);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(content);
		response.getWriter().close();
	}
}