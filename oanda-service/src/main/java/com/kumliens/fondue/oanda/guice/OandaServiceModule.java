package com.kumliens.fondue.oanda.guice;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Environment;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.kumliens.fondue.oanda.OandaConfiguration;
import com.kumliens.fondue.oanda.OandaServiceConfiguration;
import com.kumliens.fondue.oanda.health.AmqpHealthCheck;
import com.kumliens.fondue.oanda.health.OandaHealthCheck;
import com.kumliens.fondue.oanda.rabbitmq.RabbitMQGateway;
import com.kumliens.fondue.oanda.resources.AdminResourceImpl;
import com.kumliens.fondue.oanda.resources.RatesResourceImpl;
import com.kumliens.fondue.oanda.services.PriceFetcherService;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Guice bindings for the oanda-service
 *
 * @author svante
 */
public class OandaServiceModule extends AbstractModule {

	private final Environment env;
	private final OandaServiceConfiguration config;

	public OandaServiceModule(final Environment env, final OandaServiceConfiguration config) {
		this.env = env;
		this.config = config;
	}

	@Override
	protected void configure() {

		bindOandaServices();

        bind(OandaServiceConfiguration.class).toInstance(this.config);

		bind(RatesResourceImpl.class);
		bind(AdminResourceImpl.class);

        final Cluster cassandra = this.config.getCassandraFactory().build(this.env);
        bind(Cluster.class).toInstance(cassandra);
        bind(Session.class).toInstance(cassandra.connect());

        bind(ConnectionFactory.class).toInstance(createRabbitCF());

        bind(PriceFetcherService.class);
        bind(OandaHealthCheck.class);
		bind(AmqpHealthCheck.class);

        bind(RabbitMQGateway.class);

        bind(EventBus.class).toInstance(new EventBus("The event bus"));
	}

	private void bindOandaServices() {
		final Client oandaClient = new JerseyClientBuilder(this.env).using(this.config.getJerseyClientConfiguration()).build("jerseyClient");
		oandaClient.setReadTimeout(10000);
        oandaClient.setConnectTimeout(2500);
        String rootUrl = config.getOanda().getRestApiUrl();
        String authHeaderContent = "";

		bind(WebResource.class)
			.annotatedWith(OandaInstrumentsResource.class)
			.toInstance(oandaClient.resource(rootUrl + "/v1/instruments"));

		bind(WebResource.class)
			.annotatedWith(OandaPriceResource.class)
			.toInstance(oandaClient.resource(rootUrl + "/v1/prices"));
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
