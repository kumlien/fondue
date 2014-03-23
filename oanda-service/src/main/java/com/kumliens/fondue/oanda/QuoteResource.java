package com.kumliens.fondue.oanda;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.kumliens.fondue.oanda.model.Quote;
import com.yammer.metrics.annotation.Timed;


@Path("/quote")
@Produces(MediaType.APPLICATION_JSON)
public class QuoteResource {
	
	private final AtomicLong counter;
	private String template;
	private String defaultName;
	
	public QuoteResource(String template, String defaultName) {
		this.template = template;
		this.defaultName = defaultName;
		this.counter = new AtomicLong();
	}
	
	@GET
	@Timed
	public Quote getQuote(@QueryParam("instrument") String instrument) {
		// Apply RFC3339 format using JODA-TIME
		DateTime dt = new DateTime(System.currentTimeMillis());
		DateTimeFormatter dateFormatter = ISODateTimeFormat.dateTime();
		return new Quote(
				counter.incrementAndGet(),
				instrument, 
				dt.toString(dateFormatter), 
				"1.0", "1.1",
				"ok");
	}
}
