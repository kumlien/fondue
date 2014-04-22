package com.kumliens.fondue.oanda.datafetcher.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.kumliens.fondue.oanda.datafetcher.representation.PriceList;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminResource.class);
	private Client oandaClient;
	
	public AdminResource(Client oandaClient) {
		this.oandaClient = oandaClient;
	}
	
	@GET
	@Timed
	public String getPrice(@QueryParam("instrument") String instrument){
		logger.debug("Fetching price for instrument " + instrument);
		WebResource resource = oandaClient.resource("http://api-sandbox.oanda.com/v1/quote?instruments=EUR_USD%2CUSD_JPY%2CEUR_CAD");
		PriceList priceList = resource.get(PriceList.class);
		return "OK " + priceList;
	}
	
	
}
