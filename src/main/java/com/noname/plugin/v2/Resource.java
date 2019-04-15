package com.noname.plugin.v2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test-api")
public class Resource
{
	public Resource()
	{
	}

	@GET
	@Path("/get1")
	public Response get1()
	{
		return Response.ok("GET2.1").build();
	}
}
