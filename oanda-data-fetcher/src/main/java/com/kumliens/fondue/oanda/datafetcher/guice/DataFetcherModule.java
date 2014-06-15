package com.kumliens.fondue.oanda.datafetcher.guice;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import java.io.UnsupportedEncodingException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.kumliens.fondue.oanda.datafetcher.DataFetcherConfiguration;
import com.kumliens.fondue.oanda.datafetcher.health.AmqpHealthCheck;
import com.kumliens.fondue.oanda.datafetcher.health.OandaHealthCheck;
import com.kumliens.fondue.oanda.datafetcher.resources.AdminResourceImpl;
import com.kumliens.fondue.oanda.datafetcher.resources.RatesResourceImpl;
import com.kumliens.fondue.oanda.datafetcher.services.PriceFetcherService;
import com.rabbitmq.client.ConnectionFactory;
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

		Client oandaClient = new JerseyClientBuilder(env).using(config.getJerseyClientConfiguration()).build("jerseyClient");
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

        bind(PriceFetcherService.class).toInstance(new PriceFetcherService(config.interval));

        Cluster cassandra = config.getCassandraFactory().build(this.env);
        bind(Cluster.class).toInstance(cassandra);
        bind(Session.class).toInstance(cassandra.connect());
        
        ConnectionFactory cf = createRabbitCF();
        bind(ConnectionFactory.class).toInstance(cf);

		bind(OandaHealthCheck.class);
		bind(AmqpHealthCheck.class);

        bind(EventBus.class).toInstance(new EventBus("The event bus"));
	}

	private ConnectionFactory createRabbitCF() {
		ConnectionFactory factory = new ConnectionFactory();
        factory.setConnectionTimeout(1000);//times out in 1s.
        factory.setUsername(config.getAmqp().getUsername());
        factory.setPassword(config.getAmqp().getPassword());
        factory.setHost(config.getAmqp().getHost());
        factory.setPort(config.getAmqp().getPort());
        return factory;
	}

}
