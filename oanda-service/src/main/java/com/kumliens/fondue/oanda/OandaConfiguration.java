package com.kumliens.fondue.oanda;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OandaConfiguration {
	
	@JsonProperty
	private Long fetchInterval;
	
	
	@NotEmpty
	@JsonProperty
	private String apiKey;
	
	@NotEmpty
	@JsonProperty
	private String restApiUrl;
	
	@NotEmpty
	@JsonProperty
	private String streamingUrlApi;


	public Long getFetchInterval() {
		return fetchInterval;
	}


	public void setFetchInterval(Long fetchInterval) {
		this.fetchInterval = fetchInterval;
	}


	public String getApiKey() {
		return apiKey;
	}


	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	public String getRestApiUrl() {
		return restApiUrl;
	}


	public String getStreamingUrlApi() {
		return streamingUrlApi;
	}


	public void setStreamingUrlApi(String streamingUrlApi) {
		this.streamingUrlApi = streamingUrlApi;
	}

}
