package com.noname.plugin.youtube.resource;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.noname.plugin.youtube.ao.Subscribers;
import com.noname.plugin.youtube.ao.Users;
import com.noname.plugin.youtube.ao.Videos;
import com.noname.plugin.youtube.ao.Youtubers;
import net.java.ao.Query;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("/youtube")
public class YoutubeResource
{
	private ActiveObjects activeObjects;

	public YoutubeResource(ActiveObjects activeObjects)
	{
		this.activeObjects = activeObjects;
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Path("/addYoutuber")
	public Response addYoutuber(@Context HttpServletRequest request, @FormParam("name") String name, @FormParam("description") String description)
	{
		Youtubers youtuber = this.activeObjects.create(Youtubers.class);
		youtuber.setName(request.getParameter("name"));
		youtuber.setDescription(request.getParameter("description"));
		youtuber.setIsActive(true);
		youtuber.setType(Youtubers.Type.OPEN);
		youtuber.save();

		return Response.ok().build();
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Path("/deleteYoutuber")
	public Response deleteYoutuber(@Context HttpServletRequest request, @FormParam("yid") String name)
	{
		int yid = Integer.parseInt(request.getParameter("yid"));
		Videos[] videos = this.activeObjects.find(
			Videos.class,
			Query.select().where("YOUTUBER_ID = ?", yid)
		);

		for (Videos video : videos) {
			this.activeObjects.delete(video);
		}

		Subscribers[] subscribers = this.activeObjects.find(
			Subscribers.class,
			Query.select().where("YOUTUBER_ID = ?", yid)
		);

		for (Subscribers subscriber : subscribers) {
			this.activeObjects.delete(subscriber);
		}

		Youtubers youtuber = this.activeObjects.get(
			Youtubers.class,
			yid
		);

		if (youtuber != null) {
			this.activeObjects.delete(youtuber);
		}

		return Response.ok().build();
	}

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Path("/addSubscriber")
	public Response addSubscriber(@Context HttpServletRequest request, @FormParam("user") String user, @FormParam("youtuber") String youtuber)
	{
		int userid = Integer.parseInt(request.getParameter("user"));
		int yid = Integer.parseInt(request.getParameter("youtuber"));

		Users userObj = this.activeObjects.get(Users.class, userid);
		Youtubers youtuberObj = this.activeObjects.get(Youtubers.class, yid);

		if (userObj != null && youtuberObj != null) {
			Subscribers[] s = this.activeObjects.find(
				Subscribers.class,
				Query.select().where("USER_ID = ? AND YOUTUBER_ID = ?", userObj.getID(), youtuberObj.getID())
			);

			if (s.length == 0) {
				Subscribers subscriber = this.activeObjects.create(Subscribers.class);
				subscriber.setUser(userObj);
				subscriber.setYoutuber(youtuberObj);
				subscriber.save();
			}
		}

		return Response.ok().build();
	}
}
