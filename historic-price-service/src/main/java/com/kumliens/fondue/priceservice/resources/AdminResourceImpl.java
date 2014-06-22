package com.kumliens.fondue.priceservice.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    public String hej() {
    	return "hej";
    }
}
