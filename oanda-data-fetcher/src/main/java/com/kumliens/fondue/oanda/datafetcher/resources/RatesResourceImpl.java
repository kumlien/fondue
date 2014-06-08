package com.kumliens.fondue.oanda.datafetcher.resources;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.kumliens.fondue.oanda.datafetcher.guice.InstrumentsResource;
import com.kumliens.fondue.oanda.datafetcher.guice.PriceResource;
import com.kumliens.fondue.oanda.datafetcher.representation.Instrument;
import com.kumliens.fondue.oanda.datafetcher.representation.Price;
import com.kumliens.fondue.oanda.datafetcher.representation.PriceList;
import com.kumliens.fondue.oanda.datafetcher.responses.InstrumentDataResponse;
import com.kumliens.fondue.oanda.datafetcher.responses.InstrumentListResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;


/**
 * 
 * 
 * @author svante
 */
@Path("/rates")
@Produces(MediaType.APPLICATION_JSON)
public class RatesResourceImpl implements RatesResource {
	
	private static final Logger logger = LoggerFactory.getLogger(RatesResourceImpl.class);
	
	@Inject @InstrumentsResource
	private WebResource instrumentsResource;
	
	@Inject @PriceResource
	private WebResource priceResource;
	
	private final String INSTRUMENT_FIELDS;
	
	public RatesResourceImpl() throws UnsupportedEncodingException {
		INSTRUMENT_FIELDS = URLEncoder.encode("marginRate,displayName,pip,maxTradeUnits,precision,maxTrailingStop,minTrailingStop","UTF-8");
	}
	
	@GET
	@Path("prices/{instrument}")
	@Timed
	public PriceList getPrice(@PathParam("instrument") Instrument instrument) {
		logger.info("Fetching price for instrument " + instrument);
		
		try {
			PriceList priceList = priceResource.queryParam("instruments", instrument.getCode()).get(PriceList.class);
			logger.warn("Got a string: " + priceList);

			return priceList;
		} catch (Exception e) {			
			logger.error("Error fetching instrument prices", e);
			return null;
		}
	}
	
	@GET
	@Path("instruments")
	@Timed
	public List<InstrumentDataResponse> getsInstruments(@PathParam("instruments") @DefaultValue("") String instrumentList) {
		logger.info("Fetching list of instruments: " + instrumentList);
		try {
		InstrumentListResponse instrumentListResponse = instrumentsResource.queryParam("fields", INSTRUMENT_FIELDS).accept(MediaType.APPLICATION_JSON).get(InstrumentListResponse.class);
		logger.info("Fetched " + instrumentListResponse.getInstruments().size() + " instrument definitions");
		return instrumentListResponse.getInstruments();
		} catch (Exception e) {
			logger.error("Error fetching instruments", e);
			throw e;
		}
	}
	
	
}
