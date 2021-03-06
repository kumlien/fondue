package com.kumliens.fondue.oanda.representation;

import java.io.UnsupportedEncodingException;	
import java.net.URLEncoder;

public enum Instrument {

	EUR_USD("EUR_USD"), 
	USD_JPY("USD_JPY"), 
	EUR_CAD("EUR_CAD");

	private final String code;

	private Instrument (final String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

    public static String asURLEncodedCommaSeparatedList() throws UnsupportedEncodingException {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Instrument.values().length; i++) {
            sb.append(Instrument.values()[i].getCode());
            if (i + 1 < Instrument.values().length) {
                sb.append(",");
            }
        }

        return URLEncoder.encode(sb.toString(), "UTF-8");
    }
}
