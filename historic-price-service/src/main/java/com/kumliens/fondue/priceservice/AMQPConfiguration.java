package com.kumliens.fondue.priceservice;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rabbitmq.client.ConnectionFactory;

public class AMQPConfiguration {

	/**
	 * Shared topic exchange used for publishing any market data (e.g. instrument prices)
	 */
	@Valid
	@JsonProperty("exchange")
	@NotNull
	@NotEmpty
	private String exchange;

	/**
	 * Our consumer queue.
	 */
	@Valid
	@JsonProperty("queue")
	@NotNull
	@NotEmpty
	private String queue;

	/**
	 * Key that clients will use to send to the stock request queue via the
	 * default direct exchange.
	 */
	@JsonProperty
	private String routingKey = getQueue();

	@NotEmpty
	@JsonProperty
	private String host;

	@Min(1)
	@Max(65535)
	@JsonProperty
	private int port = ConnectionFactory.DEFAULT_AMQP_PORT;

	@NotEmpty
	@JsonProperty
	private String username = ConnectionFactory.DEFAULT_USER;

	@NotEmpty
	@JsonProperty
	private String password = ConnectionFactory.DEFAULT_PASS;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getExchange() {
		return exchange;
	}

	public String getQueue() {
		return queue;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}
}
