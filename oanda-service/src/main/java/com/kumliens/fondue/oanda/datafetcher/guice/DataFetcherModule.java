package com.kumliens.fondue.oanda.datafetcher.guice;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.kumliens.fondue.oanda.datafetcher.DataFetcherConfiguration;
import com.kumliens.fondue.oanda.datafetcher.health.AmqpHealthCheck;
import com.kumliens.fondue.oanda.datafetcher.health.OandaHealthCheck;
import com.kumliens.fondue.oanda.datafetcher.rabbitmq.RabbitMQGateway;
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

		final Client oandaClient = new JerseyClientBuilder(this.env).using(this.config.getJerseyClientConfiguration()).build("jerseyClient");
		oandaClient.setReadTimeout(10000);
        oandaClient.setConnectTimeout(2500);

		bind(WebResource.class)
			.annotatedWith(OandaInstrumentsResource.class)
			.toInstance(oandaClient.resource("http://api-sandbox.oanda.com/v1/instruments"));

		bind(WebResource.class)
			.annotatedWith(OandaPriceResource.class)
			.toInstance(oandaClient.resource("http://api-sandbox.oanda.com/v1/prices"));

        bind(DataFetcherConfiguration.class).toInstance(this.config);

		bind(RatesResourceImpl.class);
		bind(AdminResourceImpl.class);

        final Cluster cassandra = this.config.getCassandraFactory().build(this.env);
        bind(Cluster.class).toInstance(cassandra);
        bind(Session.class).toInstance(cassandra.connect());

        final ConnectionFactory cf = createRabbitCF();
        bind(ConnectionFactory.class).toInstance(cf);

        bind(PriceFetcherService.class);
        bind(OandaHealthCheck.class);
		bind(AmqpHealthCheck.class);

        bind(RabbitMQGateway.class);

        bind(EventBus.class).toInstance(new EventBus("The event bus"));
	}

	private ConnectionFactory createRabbitCF() {
		final ConnectionFactory factory = new ConnectionFactory();
        factory.setConnectionTimeout(1000);//times out in 1s.
        factory.setUsername(this.config.getAmqp().getUsername());
        factory.setPassword(this.config.getAmqp().getPassword());
        factory.setHost(this.config.getAmqp().getHost());
        factory.setPort(this.config.getAmqp().getPort());
        return factory;
	}

}
