package com.kumliens.fondue.oanda.events;

import com.kumliens.fondue.oanda.responses.Price;

public class NewPriceAvailableEvent {

	private final Price price;
	
	public NewPriceAvailableEvent(Price price) {
		this.price = price;
	}

	public Price getPrice() {
		return price;
	}
	
	
}
