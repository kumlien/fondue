package com.kumliens.fondue.oanda.responses;

import java.util.List;

public class InstrumentListResponse {
	
	private List<InstrumentDataResponse> instruments;


	public List<InstrumentDataResponse> getInstruments() {
		return instruments;
	}


	public void setInstruments(List<InstrumentDataResponse> instruments) {
		this.instruments = instruments;
	}

}
