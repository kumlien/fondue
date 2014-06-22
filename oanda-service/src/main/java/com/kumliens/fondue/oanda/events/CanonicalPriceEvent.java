package com.kumliens.fondue.oanda.events;

import java.util.Date;

import com.kumliens.fondue.oanda.responses.Price;

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
	
	private static final String serviceProvider = "oanda";
	
	public static class Builder {
		
		private final CanonicalPriceEvent event;
		
		public Builder() {
			event = new CanonicalPriceEvent();
		}
		
		public Builder withOandaPrice(Price oandaPrice) {
			event.date = oandaPrice.getTime();
			event.instrument = oandaPrice.getInstrument().getCode();
			event.setBid(oandaPrice.getBid());
			event.setAsk(oandaPrice.getAsk());
			
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


	public Double getAsk() {
		return ask;
	}


	public void setAsk(Double ask) {
		this.ask = ask;
	}


	public Double getBid() {
		return bid;
	}


	public void setBid(Double bid) {
		this.bid = bid;
	}


	public String getServiceprovider() {
		return serviceProvider;
	}

}
