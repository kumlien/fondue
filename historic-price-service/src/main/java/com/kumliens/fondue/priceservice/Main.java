package com.kumliens.fondue.priceservice;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kumliens.fondue.priceservice.guice.PriceserviceModule;
import com.kumliens.fondue.priceservice.rabbitmq.RabbitMQGateway;
import com.kumliens.fondue.priceservice.resources.AdminResourceImpl;

/**
 * Starting the service providing historic prices to the system. 
 * 
 *Start like: java -jar target/historic-price-service-0.0.1-SNAPSHOT.jar server src/main/resources/PriceService.yml
 * 
 * @author svante
 */
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
		final Injector injector = Guice.createInjector(new PriceserviceModule(env, config));
		logger.debug("Created Guice injector");
		
		final AdminResourceImpl ar = injector.getInstance(AdminResourceImpl.class);
		env.jersey().register(ar);
		logger.debug("Registered the Admin resource");
		
		env.lifecycle().manage(injector.getInstance(RabbitMQGateway.class));
		logger.debug("Registered the RabbitMQGateway instance to be lifecycle managed by Dropwizard");
		
		final DBIFactory factory = new DBIFactory();
	    final DBI jdbi = factory.build(env, config.getDataSourceFactory(), "postgresql");   
	    
	}

}
