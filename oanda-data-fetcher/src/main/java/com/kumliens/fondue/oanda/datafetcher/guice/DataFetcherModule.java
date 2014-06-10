package com.kumliens.fondue.oanda.datafetcher.guice;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import java.io.UnsupportedEncodingException;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.kumliens.fondue.oanda.datafetcher.DataFetcherConfiguration;
import com.kumliens.fondue.oanda.datafetcher.health.OandaHealthCheck;
import com.kumliens.fondue.oanda.datafetcher.resources.AdminResourceImpl;
import com.kumliens.fondue.oanda.datafetcher.resources.RatesResourceImpl;
import com.kumliens.fondue.oanda.datafetcher.services.PriceFetcherService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Guice bindings for the DataFetcher service
 *
 * @author svante
 */
public class DataFetcherModule extends AbstractModule {

	private final Environment env;
	private final DataFetcherConfiguration config;

	public DataFetcherModule(final Environment env, final DataFetcherConfiguration config) {
		this.env = env;
		this.config = config;
	}

	@Override
	protected void configure() {

		final Client oandaClient = new JerseyClientBuilder(this.env).using(this.config.getJerseyClientConfiguration()).build("jerseyClient");
		oandaClient.setReadTimeout(10000);
        oandaClient.setConnectTimeout(2500);

		bind(WebResource.class)
			.annotatedWith(OandaInstrumentsResource.class)
			.toInstance(oandaClient.resource("http://api-sandbox.oanda.com/v1/instruments"));

		bind(WebResource.class)
			.annotatedWith(OandaPriceResource.class)
			.toInstance(oandaClient.resource("http://api-sandbox.oanda.com/v1/prices"));


		bind(RatesResourceImpl.class);
		bind(AdminResourceImpl.class);

        try {
            bind(PriceFetcherService.class).toInstance(new PriceFetcherService(this.config.interval));
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to create the PriceFetcherService", e);
        }

		bind(OandaHealthCheck.class);
        
        bind(EventBus.class).toInstance(new EventBus("The event bus"));

	}

}
