package com.noname.plugin.v1;

import com.atlassian.jira.ofbiz.OfBizDelegator;
import com.atlassian.plugins.rest.common.security.AnonymousAllowed;
import com.google.common.collect.Lists;
import com.noname.plugin.Param;
import org.ofbiz.core.entity.EntityExpr;
import org.ofbiz.core.entity.EntityOperator;
import org.ofbiz.core.entity.GenericEntityException;
import org.ofbiz.core.entity.GenericValue;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/test-api")
public class Resource
{
	private OfBizDelegator delegator;

	public Resource(OfBizDelegator delegator)
	{
		this.delegator = delegator;
	}

	@GET
	@Path("/sprints")
	public Response getSprints() throws GenericEntityException
	{
		List<GenericValue> values = this.delegator.findAll("Sprint");

		EntityExpr c1 = new EntityExpr(
			"id",
			EntityOperator.EQUALS,
			1
		);
		EntityExpr c2 = new EntityExpr(
			"id",
			EntityOperator.EQUALS,
			3
		);

		EntityExpr c3 = new EntityExpr(
			c1,
			EntityOperator.OR,
			c2
		);

		List<GenericValue> values2 = this.delegator.findByCondition("Sprint", c3, Lists.newArrayList());
		List<GenericValue> related = values2.get(0).getRelated("ParentRapidView");
		values2.get(0).set("name", "New Sprint Name");
		values2.get(0).store();

		return Response.ok(values.toString()).build();
	}

	@GET
	@Path("/get1")
	public Response get1()
	{
		return Response.ok("GET1").build();
	}

	@GET
	@Path("/get2")
	public Response get2(@Context HttpServletRequest request)
	{
		//request.getParameter("...")
		return Response.ok("GET2").build();
	}

	@GET
	@Path("/get3")
	public Response get3(@Context HttpServletRequest request, @QueryParam("param1") int param1, @QueryParam("param2") List<String> param2)
	{
		//request.getParameter("...")
		return Response.ok("GET3: param1:" + param1 + ", param2:" + param2.toString()).build();
	}

	@GET
	@AnonymousAllowed
	@Path("/get4")
	public Response get4()
	{
		return Response.ok("GET4").build();
	}

	@POST
	@Path("/post1")
	@Produces({MediaType.TEXT_PLAIN})
	public Response post1()
	{
		return Response.ok("POST1").build();
	}

	@POST
	@Path("/post2")
	@Produces({MediaType.APPLICATION_JSON})
	public Response post2(Map<String, Object> params)
	{
		return Response.ok("POST2 params:" + params.toString()).build();
	}

	@POST
	@Path("/post3")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response post3(Map<String, Object> params)
	{
		return Response.ok("POST3 params:" + params.toString()).build();
	}

	@POST
	@Path("/post4")
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	@Produces({MediaType.APPLICATION_JSON})
	public Response post4(@FormParam("id") String id)
	{
		return Response.ok("POST4 id:" + id).build();
	}

	@POST
	@Path("/post5")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response post5(Param param)
	{
		return Response.ok("POST5 id:" + param.getId()).build();
	}
}
