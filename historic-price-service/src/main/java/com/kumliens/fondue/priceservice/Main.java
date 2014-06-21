package com.kumliens.fondue.priceservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class Main extends Application<PriceserviceConfiguration> {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
	
	@Override
	public void initialize(Bootstrap<PriceserviceConfiguration> bootstrap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(PriceserviceConfiguration configuration, Environment environment) throws Exception {
		logger.debug("Starting...");
		
	}

}
