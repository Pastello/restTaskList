package it.lorenzo.microservizi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskGenericResponse {
	
	private SimpleResponse response;
	private Task taskResponse;

	public TaskGenericResponse(int statusCode, String message) {
		this(statusCode, message, null);
	}

	public TaskGenericResponse(int statusCode, String message, Task task) {
		this.response = new SimpleResponse(statusCode, message);
		this.setTask(task);
	}

	public void setTask(Task task) {
		this.taskResponse = task;
	}

	@JsonProperty("code")
	public int getStatusCode() {
		return this.response.getStatusCode();
	}

	@JsonProperty("message")
	public String getMessage() {
		return this.response.getMessage();
	}

	@JsonProperty("task")
	public Task getTasklistResponse() {
		return this.taskResponse;
	}
}
