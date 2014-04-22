package com.kumliens.fondue.oanda.datafetcher.representation;

public enum Instrument {
	
	EUR_USD("EUR_USD"), USD_JPY("USD_JPY"), EUR_CAD("EUR_CAD");
	
	private final String code;

	private Instrument (String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
