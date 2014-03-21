package com.kumliens.fondue.oanda;

import com.kumliens.fondue.oandabridge.health.TemplateHealthCheck;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class OandaService extends Service<OandaConfiguration> {

	public static void main(String... args) throws Exception {
		new OandaService().run(args);
	}

	@Override
	public void initialize(Bootstrap<OandaConfiguration> arg0) {
		arg0.setName("Oanda Service");
	}

	@Override
	public void run(OandaConfiguration configuration, Environment environment)
			throws Exception {
		final String template = configuration.getTemplate();
		final String defaultName = configuration.getDefaultName();
		environment.addResource(new QuoteResource(template, defaultName));
		environment.addHealthCheck(new TemplateHealthCheck(template));
	}

}
