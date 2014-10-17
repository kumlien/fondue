package com.kumliens.fondue.priceservice.guice;

import io.dropwizard.setup.Environment;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.kumliens.fondue.priceservice.PriceserviceConfiguration;
import com.kumliens.fondue.priceservice.resources.AdminResourceImpl;
import com.rabbitmq.client.ConnectionFactory;

/**
 * This is the Guice module for this service. Binds classes or 
 * instances to the guice context. These classes can the @Inject
 * other bound types.
 * 
 * @author svante
 */
public class PriceserviceModule extends AbstractModule {

	private final Environment env;
	private final PriceserviceConfiguration config;

	public PriceserviceModule(final Environment env, final PriceserviceConfiguration config) {
		this.env = env;
		this.config = config;
	}

	@Override
	protected void configure() {
		
		bind(PriceserviceConfiguration.class).toInstance(this.config);
		bind(ConnectionFactory.class).toInstance(createRabbitCF());
		bind(EventBus.class).toInstance(new EventBus("The event bus"));
		bind(AdminResourceImpl.class);
		
	}

	private ConnectionFactory createRabbitCF() {
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setConnectionTimeout(1000);// times out in 1s.
		factory.setUsername(this.config.getAmqp().getUsername());
		factory.setPassword(this.config.getAmqp().getPassword());
		factory.setHost(this.config.getAmqp().getHost());
		factory.setPort(this.config.getAmqp().getPort());
		return factory;
	}

}
