package it.lorenzo.microservizi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.lorenzo.microservizi.model.Task;
import it.lorenzo.microservizi.repository.Tasklist;

@Service
public class TasklistService {
	
	@Autowired
	private Tasklist myTasklist;

	public List<Task> getTasks(){
		List<Task> tasks = new ArrayList<Task>();
		for(Task task : this.myTasklist.getTasks()) {
			tasks.add(task);
		};
		return tasks;
	}

	public List<Task> getTask(String name) {
		List<Task> tasks = new ArrayList<Task>();
		Task task = this.myTasklist.getTask(name);
		if(task != null) tasks.add(task);
		return tasks;
	}

	public Task getTask(int id) throws Exception {
		Task task = this.myTasklist.getTask(id);
		if(task != null) return task;
		else throw new Exception("Task non trovato");
	}

	public boolean updateTask(String name, String category, String status) throws Exception {
		if(category == null && status == null) throw new Exception("Specificare almeno un parametro tra category e status");
		
		boolean done = false;
		if(category != null) done = this.changeTaskCategory(name, category);
		if(status != null) done = this.changeTaskStatus(name, status) || done;
		return done;
	}

	public boolean changeTaskStatus(String name, String status) throws Exception {
		if(status.equals(Task.STATUSCOMPLETED)) return this.myTasklist.markCompleted(name);
		if (status.equals(Task.STATUSUNCOMPLETED)) return this.myTasklist.markUncompleted(name);
		else throw new Exception("Stato non ammesso");
	}
	
	public boolean changeTaskCategory(String name, String category) {
		return this.myTasklist.changeCategory(name, category);
	}

	public List<Task> getTasksNewerThan(Date refDate){
		return this.myTasklist.getTasksNewerThan(refDate);
	}

	public List<Task> getTasksByCategory(String category){
		return this.myTasklist.getTasksByCategory(category);
	}

	public boolean addTask(String name, String category) {
		return this.myTasklist.addTask(name, category);
	}

	public boolean removeTask(String name) {
		return this.myTasklist.removeTask(name);
	}
}
