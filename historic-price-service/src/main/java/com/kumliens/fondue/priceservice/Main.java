package com.kumliens.fondue.priceservice;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kumliens.fondue.priceservice.guice.PriceserviceModule;
import com.kumliens.fondue.priceservice.rabbitmq.RabbitMQGateway;
import com.kumliens.fondue.priceservice.resources.AdminResourceImpl;

public class Main extends Application<PriceserviceConfiguration> {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(final String[] args) throws Exception {
		new Main().run(args);
	}

	@Override
	public String getName() {
		return "Historic price service application";
	}
	
	
	@Override
	public void initialize(Bootstrap<PriceserviceConfiguration> bootstrap) {
		
		
	}

	@Override
	public void run(PriceserviceConfiguration config, Environment env) throws Exception {
		logger.debug("Starting...");
		logger.warn("The config: " + config.getAmqp().getExchange());
		final Injector injector = Guice.createInjector(new PriceserviceModule(env, config));
		
		final AdminResourceImpl ar = injector.getInstance(AdminResourceImpl.class);
		logger.warn("The admin resource: " + ar);
		env.jersey().register(ar);
		
		env.lifecycle().manage(injector.getInstance(RabbitMQGateway.class));
	}

}
