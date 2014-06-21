package com.kumliens.fondue.oanda.health;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AmqpHealthCheck extends HealthCheck {

	private static final Logger LOG = LoggerFactory
			.getLogger(AmqpHealthCheck.class);

	@Inject
	ConnectionFactory cf;

	@Override
	protected Result check() throws Exception {
		Connection conn = null;
		try {
			conn = cf.newConnection();
			if (conn.isOpen()) {
				return Result.healthy();
			} else {
				return Result.unhealthy("AMQP connection is closed.");
			}
		} catch (IOException e) {
			return Result.unhealthy("Cannot open AMQP connection. "
					+ e.getMessage());
		} finally {
			if (conn != null)
				try {
					LOG.info("Closing AMQP connection.");
					conn.close();
				} catch (IOException e) {
					conn = null;
					LOG.info("Error closing AMQP connection.");
				}
		}
	}

}
