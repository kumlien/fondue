package com.kumliens.fondue.priceservice;

import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.stuartgunter.dropwizard.cassandra.CassandraFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceserviceConfiguration extends Configuration {
	
	@Valid
    @NotNull
    @JsonProperty("amqp")
    private AMQPConfiguration amqp;
	
	@Valid
    @NotNull
    @JsonProperty("cassandra")
    private CassandraFactory cassandra;

	public AMQPConfiguration getAmqp() {
		return amqp;
	}

	public void setAmqp(AMQPConfiguration amqp) {
		this.amqp = amqp;
	}

	public CassandraFactory getCassandra() {
		return cassandra;
	}

	public void setCassandra(CassandraFactory cassandra) {
		this.cassandra = cassandra;
	}
}
