package com.kumliens.fondue.oanda.model;

public class Quote {
	
	public final String instrument;
	public final String time;
	public final String bid;
	public final String ask;
	public final String status;
	private final long id;
	
	public Quote(long id, String instrument, String time, String bid, String ask, String status) {
		this.id=id;
		this.instrument=instrument;
		this.time=time;
		this.bid=bid;
		this.ask=ask;
		this.status=status;
	}

	public String getInstrument() {
		return instrument;
	}

	public String getTime() {
		return time;
	}

	public String getBid() {
		return bid;
	}

	public String getAsk() {
		return ask;
	}

	public long getId() {
		return id;
	}
}
