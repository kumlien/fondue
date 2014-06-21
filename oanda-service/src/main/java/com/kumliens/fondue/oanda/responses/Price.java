package com.kumliens.fondue.oanda.responses;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kumliens.fondue.oanda.representation.Instrument;



public class Price {

	@Override
	public String toString() {
		return "Price [instrument=" + this.instrument + ", time=" + this.time + ", bid="
				+ this.bid + ", ask=" + this.ask + "]";
	}

	@JsonProperty
	private Instrument instrument;

	@JsonProperty
	private Date time;

	@JsonProperty
	private Double bid;

	@JsonProperty
	private Double ask;
    
    @JsonProperty(required = false)
    private String status;

	public Instrument getInstrument() {
		return this.instrument;
	}

	public void setInstrument(final Instrument instrument) {
		this.instrument = instrument;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(final Date time) {
		this.time = time;
	}

	public Double getBid() {
		return this.bid;
	}

	public void setBid(final Double bid) {
		this.bid = bid;
	}

	public Double getAsk() {
		return this.ask;
	}

	public void setAsk(final Double ask) {
		this.ask = ask;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
