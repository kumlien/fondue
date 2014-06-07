package com.kumliens.fondue.oanda.datafetcher;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kumliens.fondue.oanda.datafetcher.guice.DataFetcherModule;
import com.kumliens.fondue.oanda.datafetcher.resources.RatesResource;

/**
 * Starter for this service. 
 * 
 * Start like: java -jar target/oanda-data-fetcher-0.0.1-SNAPSHOT.jar server src/main/resources/DataFetcher.yml
 * 
 * @author svante
 */
public class OandaDataFetcher extends Application<DataFetcherConfiguration> {

	private static final Logger logger = LoggerFactory
			.getLogger(OandaDataFetcher.class);

	public static void main(String[] args) throws Exception {
		new OandaDataFetcher().run(args);
	}

	@Override
	public String getName() {
		return "Oanda data-fetcher application";
	}

	@Override
	public void initialize(Bootstrap<DataFetcherConfiguration> config) {

	}

	@Override
	public void run(DataFetcherConfiguration config, Environment env) throws Exception {
		logger.debug("Configured interval is " + config.interval + " seconds");

		//Client jerseyClient = new JerseyClientBuilder(env).using(config.getJerseyClientConfiguration()).build("jerseyClient");
		Injector injector = Guice.createInjector(new DataFetcherModule(env, config));
		
		RatesResource rr = injector.getInstance(RatesResource.class);
		env.jersey().register(rr);
		// env.healthChecks().register("oanda", new OandaHealthCheck(jerseyClient));
	}

}
