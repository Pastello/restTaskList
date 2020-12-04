package it.lorenzo.microservizi.model;

import java.util.Date;

public class Task {

	private static int idCounter;
	
	public final static String STATUSCOMPLETED = "Completed";
	public final static String STATUSUNCOMPLETED = "Uncompleted";

	private int id;
	private String name;
	private String status;
	
	private String category;

	private Date createdDate;
	private Date updatedDate;
	private Date completedDate;
	
	public Task(String name) {
		this(name, null);
	}

	public Task(String name, String category) {
		this.id = ++Task.idCounter;

		this.setName(name);
		this.setStatus(Task.STATUSUNCOMPLETED);
		
		this.setCategory(category);

		this.createdDate = new Date();
		this.updatedDate = new Date();
	}

	private void validateStatus(String status) {
		if(
			status == null
			|| !(
				status.equals(Task.STATUSCOMPLETED)
				|| status.equals(Task.STATUSUNCOMPLETED)
			)
		) {
			throw new RuntimeException("Stato non ammesso");
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.updatedDate = new Date();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) throws RuntimeException {
		if(!status.equals(this.status)) {			
			this.validateStatus(status);

			if(status.equals(Task.STATUSCOMPLETED)) {
				this.completedDate = new Date();
			}

			else if(status.equals(Task.STATUSUNCOMPLETED)) {
				this.completedDate = null;
			}

			this.status = status;
			this.updatedDate = new Date();
		}
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category == null ? "Other" : category;
		this.updatedDate = new Date();
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public int getId() {
		return this.id;
	}
}
