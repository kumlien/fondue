package com.kumliens.fondue.oanda.representation;

public class ServiceStatus {
	
	private final String name;
	
	private final Status status;

	public ServiceStatus(String name, Status status) {
		this.name = name;
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public String getName() {
		return name;
	}
}
