package com.kumliens.fondue.oanda.datafetcher;

import io.dropwizard.Application;
import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kumliens.fondue.oanda.datafetcher.health.OandaHealthCheck;
import com.kumliens.fondue.oanda.datafetcher.resources.AdminResource;
import com.sun.jersey.api.client.Client;

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

		Client jerseyClient = new JerseyClientBuilder(env).using(config.getJerseyClientConfiguration()).build("jerseyClient");
	
		
		AdminResource ar = new AdminResource(jerseyClient);
		env.jersey().register(ar);
		env.healthChecks().register("oanda", new OandaHealthCheck(jerseyClient));
	}

}
