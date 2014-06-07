package com.kumliens.fondue.oanda.datafetcher.responses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data from Oanda for an Instrument
 * 
 * @author svante
 */
public class InstrumentDataResponse {
	
	public String getInstrument() {
		return instrument;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Double getPip() {
		return pip;
	}

	public Long getMaxTradeUnits() {
		return maxTradeUnits;
	}

	public Double getPrecision() {
		return precision;
	}

	public Integer getMaxTrailingStop() {
		return maxTrailingStop;
	}

	public Integer getMinTrailingStop() {
		return minTrailingStop;
	}

	public Double getMarginRate() {
		return marginRate;
	}

	public final String instrument;
	
	public final String displayName;
	
	public final Double pip;
	
	public final Long maxTradeUnits;
	
	public final Double precision;
	
	public final Integer maxTrailingStop;
	
	public final Integer minTrailingStop;
	
	public final Double marginRate;
	
	@JsonCreator
	public InstrumentDataResponse(
			@JsonProperty("instrument") String instrument, 
			@JsonProperty("displayName") String displayName, 
			@JsonProperty("pip") Double pip, 
			@JsonProperty("maxTradeUnits") Long maxTradeUnits,
			@JsonProperty("precision") Double precision,
			@JsonProperty("maxTrailingStop") Integer maxTrailingStop,
			@JsonProperty("minTrailingStop") Integer minTrailingStop,
			@JsonProperty("marginRate") Double marginRate) {
		this.instrument = instrument;
		this.displayName = displayName;
		this.pip = pip;
		this.maxTradeUnits = maxTradeUnits;
		this.precision = precision;
		this.maxTrailingStop = maxTrailingStop;
		this.minTrailingStop = minTrailingStop;
		this.marginRate = marginRate;
	}
}
