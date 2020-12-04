package it.lorenzo.microservizi.repository;

import java.util.Date;
import java.util.LinkedList;

import org.springframework.stereotype.Repository;

import it.lorenzo.microservizi.model.Task;

@Repository
public class Tasklist {

	private String name;
	private LinkedList<Task> tasks;

	public Tasklist() {
		this("New List");
	}

	public Tasklist(String name) {
		this.tasks = new LinkedList<Task>();
		this.name = name;
	}

	public LinkedList<Task> getTasks() {
		return this.tasks;
	}

	public Task getTask(int id) {
		for(Task task : this.tasks) {
			if(task.getId() == id) {
				return task;
			}
		}
		return null;
	}

	public Task getTask(String name) {
		for(Task task : this.tasks) {
			if(task.getName().equals(name)) {
				return task;
			}
		}
		return null;
	}

	public boolean addTask(String name, String category) {
		this.tasks.add(new Task(name, category));
		return true;
	}

	public boolean removeTask(String name) {
		Task removedTask = null;
		for(Task task : this.tasks) {
			if(task.getName().equals(name)) {
				removedTask = task;
				break;
			}
		}

		if (removedTask == null) return false;
		else {
			this.tasks.remove(removedTask);
			return true;
		}
	}

	public boolean markCompleted(String name) {
		for(Task task : this.tasks) {
			if(task.getName().equals(name)) {
				task.setStatus(Task.STATUSCOMPLETED);
				return true;
			}
		}
		return false;
	}

	public boolean markUncompleted(String name) {
		for(Task task : this.tasks) {
			if(task.getName().equals(name)) {
				task.setStatus(Task.STATUSUNCOMPLETED);
				return true;
			}
		}
		return false;
	}

	private LinkedList<Task> getByStatus(String status){
		LinkedList<Task> respList = new LinkedList<Task>();
		for(Task task : this.tasks) {
			if(task.getStatus().equals(status)) {
				respList.add(task);
			}
		}
		return respList;
	}

	public boolean changeCategory(String name, String category) {
		for(Task task : this.tasks) {
			if(task.getName().equals(name)) {
				task.setCategory(category);;
				return true;
			}
		}
		return false;
	}

	public LinkedList<Task> getCompleted(){
		return this.getByStatus(Task.STATUSCOMPLETED);
	}

	public LinkedList<Task> getUncompleted(){
		return this.getByStatus(Task.STATUSUNCOMPLETED);
	}

	public LinkedList<Task> getTasksByCategory(String category){
		LinkedList<Task> respList = new LinkedList<Task>();
		for(Task task : this.tasks) {
			if(task.getCategory().equals(category)) {
				respList.add(task);
			}
		}
		return respList;
	}

	public LinkedList<Task> getTasksNewerThan(Date refDate){
		LinkedList<Task> respList = new LinkedList<Task>();
		for(Task task : this.tasks) {
			if(task.getCreatedDate().compareTo(refDate) >= 0) {
				respList.add(task);
			}
		}
		return respList;
	}
}
