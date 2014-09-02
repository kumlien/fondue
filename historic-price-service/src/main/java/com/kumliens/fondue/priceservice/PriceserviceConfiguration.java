package com.kumliens.fondue.priceservice;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

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
	
	@Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

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
