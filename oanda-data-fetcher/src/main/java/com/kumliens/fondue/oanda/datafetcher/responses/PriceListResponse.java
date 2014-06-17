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
	private List<Price> prices = new ArrayList<Price>();

	
	public List<Price> getPrices() {
		return prices;
	}

	
	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
}
