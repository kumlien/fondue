package com.kumliens.fondue.oanda.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.kumliens.fondue.oanda.guice.OandaPriceResource;
import com.kumliens.fondue.oanda.representation.Instrument;
import com.kumliens.fondue.oanda.responses.PriceListResponse;
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
