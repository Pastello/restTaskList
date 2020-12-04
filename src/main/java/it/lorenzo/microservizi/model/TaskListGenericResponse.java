package it.lorenzo.microservizi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskListGenericResponse {
	
	private SimpleResponse response;
	private List<Task> tasklistResponse;

	public TaskListGenericResponse(int statusCode, String message) {
		this(statusCode, message, new ArrayList<Task>());
	}

	public TaskListGenericResponse(int statusCode, String message, List<Task> tasklist) {
		this.response = new SimpleResponse(statusCode, message);
		this.setTaskList(tasklist);
	}

	public void setTaskList(List<Task> tasklist) {
		this.tasklistResponse = tasklist;
	}

	@JsonProperty("code")
	public int getStatusCode() {
		return this.response.getStatusCode();
	}

	@JsonProperty("message")
	public String getMessage() {
		return this.response.getMessage();
	}

	@JsonProperty("list")
	public List<Task> getTasklistResponse() {
		return this.tasklistResponse;
	}
}
