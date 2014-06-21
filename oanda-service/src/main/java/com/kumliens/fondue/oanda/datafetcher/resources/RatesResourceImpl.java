package com.kumliens.fondue.oanda.datafetcher.resources;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.kumliens.fondue.oanda.datafetcher.guice.OandaInstrumentsResource;
import com.kumliens.fondue.oanda.datafetcher.guice.OandaPriceResource;
import com.kumliens.fondue.oanda.datafetcher.representation.Instrument;
import com.kumliens.fondue.oanda.datafetcher.responses.InstrumentListResponse;
import com.kumliens.fondue.oanda.datafetcher.responses.PriceListResponse;
import com.sun.jersey.api.client.WebResource;


/**
 * Handler for the 'rates' resource (which is not really a resource, but it will do for now...)
 * 
 * TODO Refac to 'real' resource responsibility 
 * 
 * @author svante
 */
@Path("/rates")
@Produces(MediaType.APPLICATION_JSON)
public class RatesResourceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(RatesResourceImpl.class);
	
	@Inject @OandaInstrumentsResource
	private WebResource instrumentsResource;
	
	@Inject @OandaPriceResource
	private WebResource priceResource;
	
	private final String INSTRUMENT_FIELDS;
	
	public RatesResourceImpl() throws UnsupportedEncodingException {
		INSTRUMENT_FIELDS = URLEncoder.encode("marginRate,displayName,pip,maxTradeUnits,precision,maxTrailingStop,minTrailingStop","UTF-8");
	}
	
	
	@GET
	@Path("prices/{instrument}")
	@Produces(MediaType.APPLICATION_JSON)
	@Timed
	public Response getPrice(@PathParam("instrument") Instrument instrument) {
		logger.info("Fetching price for instrument " + instrument);
		
		try {
			PriceListResponse priceList = priceResource.queryParam("instruments", instrument.getCode()).get(PriceListResponse.class);
			return Response.ok(priceList, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {			
			logger.error("Error fetching instrument prices", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("instruments")
	@Timed
	public Response getsInstruments(@PathParam("instruments") @DefaultValue("") String instrumentList) {
		logger.info("Fetching list of instruments: " + instrumentList);
		try {
			InstrumentListResponse instrumentListResponse = instrumentsResource.queryParam("fields", INSTRUMENT_FIELDS).accept(MediaType.APPLICATION_JSON).get(InstrumentListResponse.class);
			logger.info("Fetched " + instrumentListResponse.getInstruments().size() + " instrument definitions");
			return Response.ok(instrumentListResponse, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			logger.error("Error fetching instruments", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	
}
