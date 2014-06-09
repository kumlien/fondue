package com.kumliens.fondue.oanda.datafetcher.representation;

public enum ServiceAction {
	
	START("Start"), STOP("Stop"), STATUS("Status");
	
	private final String action;
	
	private ServiceAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	
}
