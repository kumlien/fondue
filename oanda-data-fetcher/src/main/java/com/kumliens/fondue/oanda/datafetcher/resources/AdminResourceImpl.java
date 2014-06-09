package com.kumliens.fondue.oanda.datafetcher.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.kumliens.fondue.oanda.datafetcher.representation.ServiceAction;
import com.kumliens.fondue.oanda.datafetcher.representation.ServiceStatus;


/**
 * Resource with a rpc-style api like start/stop stuff.
 * 
 * @author svante
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResourceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminResourceImpl.class);
	
	@GET
	@Path("pricefetcher")
	@Timed
	public Response doPriceFetcherAction(@QueryParam("action") ServiceAction action) {
		logger.info("Action is " + action);
		ServiceStatus ss = new ServiceStatus("PriceFetcher", "running");
		
		return Response.ok().entity(ss).build();
	}
}
