package it.lorenzo.microservizi.model;

public class SimpleResponse {
	private int statusCode;
	private String message;
	
	public SimpleResponse(int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
