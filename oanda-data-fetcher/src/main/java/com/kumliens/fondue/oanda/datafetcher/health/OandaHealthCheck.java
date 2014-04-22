package com.kumliens.fondue.oanda.datafetcher.health;

import com.codahale.metrics.health.HealthCheck;
import com.sun.jersey.api.client.Client;

public class OandaHealthCheck extends HealthCheck {
	
	private final Client client;

	public OandaHealthCheck(Client client) {
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
