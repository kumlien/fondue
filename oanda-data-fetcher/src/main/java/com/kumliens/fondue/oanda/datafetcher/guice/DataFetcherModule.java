package com.kumliens.fondue.oanda.datafetcher.guice;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import com.google.inject.AbstractModule;
import com.kumliens.fondue.oanda.datafetcher.DataFetcherConfiguration;
import com.kumliens.fondue.oanda.datafetcher.resources.RatesResource;
import com.kumliens.fondue.oanda.datafetcher.resources.RatesResourceImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Bindings for the DataFetcher service
 * 
 * @author svante
 */
public class DataFetcherModule extends AbstractModule {
	
	private final Environment env;
	private final DataFetcherConfiguration config;

	public DataFetcherModule(Environment env, DataFetcherConfiguration config) {
		this.env = env;
		this.config = config;
	}

	@Override
	protected void configure() {
		
		Client oandaClient = new JerseyClientBuilder(env).using(config.getJerseyClientConfiguration()).build("jerseyClient");
		oandaClient.setReadTimeout(10000);
		
		bind(WebResource.class)
			.annotatedWith(InstrumentsResource.class)
			.toInstance(oandaClient.resource("http://api-sandbox.oanda.com/v1/instruments"));
		
		bind(WebResource.class)
			.annotatedWith(PriceResource.class)
			.toInstance(oandaClient.resource("http://api-sandbox.oanda.com/v1/prices"));
	
		
		bind(RatesResource.class).to(RatesResourceImpl.class);
		
	}

}
