package com.kumliens.fondue.oanda.datafetcher;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.stuartgunter.dropwizard.cassandra.CassandraFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataFetcherConfiguration extends Configuration {

    @JsonProperty
    public int interval;

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
    private CassandraFactory cassandra = new CassandraFactory();

    @JsonProperty("cassandra")
    public CassandraFactory getCassandraFactory() {
        return this.cassandra;
    }

    @JsonProperty("cassandra")
    public void setCassandraFactory(final CassandraFactory cassandra) {
        this.cassandra = cassandra;
    }

    public HttpClientConfiguration getHttpClientConfiguration() {
        return this.httpClient;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return this.jersyClientConfiguration;
    }
}
