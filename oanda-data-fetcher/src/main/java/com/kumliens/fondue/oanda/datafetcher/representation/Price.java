package com.kumliens.fondue.oanda.datafetcher.representation;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Price {
	
	@Override
	public String toString() {
		return "Price [instrument=" + instrument + ", time=" + time + ", bid="
				+ bid + ", ask=" + ask + "]";
	}

	@JsonProperty
	private Instrument instrument;
	
	@JsonProperty
	private Date time;
	
	@JsonProperty
	private Double bid; 
	
	@JsonProperty
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
