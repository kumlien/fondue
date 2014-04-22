package com.kumliens.fondue.oanda;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OandaService extends Application<OandaConfiguration> {

	public static void main(String... args) throws Exception {
		new OandaService().run(args);
	}

	@Override
	public void initialize(Bootstrap<OandaConfiguration> arg0) {
		
	}

	@Override
	public void run(OandaConfiguration configuration, Environment environment) throws Exception {
		final String template = configuration.getTemplate();
		final String defaultName = configuration.getDefaultName();
	}

}
