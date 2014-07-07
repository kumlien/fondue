package com.kumliens.fondue.oanda;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.stuartgunter.dropwizard.cassandra.CassandraFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OandaServiceConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private final HttpClientConfiguration httpClient = new HttpClientConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private final JerseyClientConfiguration jersyClientConfiguration = new JerseyClientConfiguration();
    
    @Valid
    @NotNull
    @JsonProperty("amqp")
    private AMQPConfiguration amqp = new AMQPConfiguration();
    
  
    @Valid
    @NotNull
    @JsonProperty("oanda")
    private OandaConfiguration oanda = new OandaConfiguration();
    

    @Valid
    @NotNull
    @JsonProperty("cassandra")
    private CassandraFactory cassandra = new CassandraFactory();

    public CassandraFactory getCassandraFactory() {
        return this.cassandra;
    }

    public void setCassandraFactory(final CassandraFactory cassandra) {
        this.cassandra = cassandra;
    }

    public HttpClientConfiguration getHttpClientConfiguration() {
        return this.httpClient;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return this.jersyClientConfiguration;
    }

	public AMQPConfiguration getAmqp() {
		return amqp;
	}

	public void setAmqp(AMQPConfiguration amqp) {
		this.amqp = amqp;
	}

	public OandaConfiguration getOanda() {
		return oanda;
	}

	public void setOanda(OandaConfiguration oanda) {
		this.oanda = oanda;
	}
}
