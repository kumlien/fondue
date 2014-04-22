package com.kumliens.fondue.oanda.datafetcher;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataFetcherConfiguration extends Configuration {
	
	@JsonProperty
	public int interval;
	
	@Valid
    @NotNull
    @JsonProperty
    private HttpClientConfiguration httpClient = new HttpClientConfiguration();

	@Valid
    @NotNull
    @JsonProperty
	private JerseyClientConfiguration jersyClientConfiguration = new JerseyClientConfiguration();

    public HttpClientConfiguration getHttpClientConfiguration() {
        return httpClient;
    }

	public JerseyClientConfiguration getJerseyClientConfiguration() {
		return jersyClientConfiguration;
	}
}
