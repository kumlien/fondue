package com.kumliens.fondue.oanda.datafetcher.representation;

import java.util.Date;



public class Price {
	
	private Instrument instrument;
	
	private Date time;
	
	private Double bid; 
	
	private Double ask;

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getBid() {
		return bid;
	}

	public void setBid(Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return ask;
	}

	public void setAsk(Double ask) {
		this.ask = ask;
	}
}
