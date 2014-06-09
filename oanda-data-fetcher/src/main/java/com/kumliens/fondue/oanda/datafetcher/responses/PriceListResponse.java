package com.kumliens.fondue.oanda.datafetcher.responses;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;



public class PriceListResponse {
	
	@Override
	public String toString() {
		return "PriceList [prices=" + prices + "]";
	}

	@JsonProperty(value="prices")
	private List<PriceResponse> prices = new ArrayList<PriceResponse>();

	
	public List<PriceResponse> getPrices() {
		return prices;
	}

	
	public void setPrices(List<PriceResponse> prices) {
		this.prices = prices;
	}
}
