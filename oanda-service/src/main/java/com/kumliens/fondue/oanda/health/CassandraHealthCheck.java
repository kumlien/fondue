package com.kumliens.fondue.oanda.health;

import com.codahale.metrics.health.HealthCheck;

public class CassandraHealthCheck extends HealthCheck {

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}

}
