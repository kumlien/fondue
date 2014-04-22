package com.kumliens.fondue.oanda.datafetcher.representation;

import java.util.ArrayList;
import java.util.List;

public class PriceList {
	
	private List<Price> prices = new ArrayList<Price>();

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}
}
