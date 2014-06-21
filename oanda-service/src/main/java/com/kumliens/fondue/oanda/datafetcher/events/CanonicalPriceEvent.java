package com.kumliens.fondue.oanda.datafetcher.events;

import java.util.Date;

import com.kumliens.fondue.oanda.datafetcher.responses.Price;

/**
 * The canonical representation of a price event in the system.
 * 
 * @author svante
 */
public class CanonicalPriceEvent {
	
	private String instrument;
	
	private Date date;
	
	private Double bid;
	
	private Double ask;
	
	public static class Builder {
		
		private final CanonicalPriceEvent event;
		
		public Builder() {
			event = new CanonicalPriceEvent();
		}
		
		public Builder withOandaPrice(Price oandaPrice) {
			event.date = oandaPrice.getTime();
			event.instrument = oandaPrice.getInstrument().getCode();
			event.bid = oandaPrice.getBid();
			event.ask = oandaPrice.getAsk();
			
			return this;
		}
		
		public CanonicalPriceEvent build() {
			return event;
		}
	}

	public Date getDate() {
		return date;
	}


	public String getInstrument() {
		return instrument;
	}

}
