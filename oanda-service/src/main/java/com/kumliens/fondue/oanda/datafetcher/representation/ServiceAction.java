package com.kumliens.fondue.oanda.datafetcher.representation;

public enum ServiceAction {

    START("Start"),
    PAUSE("Pause"),
    RESUME("Resume"),
    STOP("Stop"),
    STATUS("Status");

	private final String action;

	private ServiceAction(final String action) {
		this.action = action;
	}

	public String getAction() {
		return this.action;
	}


}
