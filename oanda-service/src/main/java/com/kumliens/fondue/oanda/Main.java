package com.kumliens.fondue.oanda;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.Service;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kumliens.fondue.oanda.guice.OandaServiceModule;
import com.kumliens.fondue.oanda.health.AmqpHealthCheck;
import com.kumliens.fondue.oanda.health.OandaHealthCheck;
import com.kumliens.fondue.oanda.rabbitmq.RabbitMQGateway;
import com.kumliens.fondue.oanda.resources.AdminResourceImpl;
import com.kumliens.fondue.oanda.resources.RatesResourceImpl;
import com.kumliens.fondue.oanda.services.PriceFetcherService;

/**
 * Starter for this service.
 *
 * Start like: java -jar target/oanda-data-fetcher-0.0.1-SNAPSHOT.jar server src/main/resources/DataFetcher.yml
 *
 * @author svante
 */
public class Main extends Application<OandaServiceConfiguration> {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(final String[] args) throws Exception {
		new Main().run(args);
	}

	@Override
	public String getName() {
		return "Oanda Service application";
	}

	@Override
	public void initialize(final Bootstrap<OandaServiceConfiguration> config) {

	}

	@Override
	public void run(final OandaServiceConfiguration config, final Environment env) throws Exception {

		final Injector injector = Guice.createInjector(new OandaServiceModule(env, config));

		final RatesResourceImpl rr = injector.getInstance(RatesResourceImpl.class);
		env.jersey().register(rr);

		final AdminResourceImpl ar = injector.getInstance(AdminResourceImpl.class);
		env.jersey().register(ar);

		env.healthChecks().register("Oanda API", injector.getInstance(OandaHealthCheck.class));
		env.healthChecks().register("RabbitMQ", injector.getInstance(AmqpHealthCheck.class));

        env.lifecycle().manage(injector.getInstance(RabbitMQGateway.class));

        Service priceService = injector.getInstance(PriceFetcherService.class);
        priceService = priceService.startAsync();

	}

}
