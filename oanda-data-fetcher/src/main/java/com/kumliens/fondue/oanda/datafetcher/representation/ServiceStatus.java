package com.kumliens.fondue.oanda.datafetcher.representation;

public class ServiceStatus {
	
	private final String name;
	
	private final String status;

	public ServiceStatus(String name, String status) {
		this.name = name;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}
	
	
}
