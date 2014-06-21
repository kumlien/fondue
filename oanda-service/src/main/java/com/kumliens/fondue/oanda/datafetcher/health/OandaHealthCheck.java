package com.kumliens.fondue.oanda.datafetcher.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.kumliens.fondue.oanda.datafetcher.guice.OandaPriceResource;
import com.kumliens.fondue.oanda.datafetcher.representation.Instrument;
import com.kumliens.fondue.oanda.datafetcher.responses.PriceListResponse;
import com.sun.jersey.api.client.WebResource;

public class OandaHealthCheck extends HealthCheck {
	
	@Inject @OandaPriceResource
	private WebResource priceResource;

	

	@Override
	protected Result check() throws Exception {
		PriceListResponse priceList = priceResource.queryParam("instruments", Instrument.EUR_CAD.getCode()).get(PriceListResponse.class);
		return Result.healthy("Oanda healthcheck succesfully fetched price for EUR_CAD: " + priceList);
	}

}
