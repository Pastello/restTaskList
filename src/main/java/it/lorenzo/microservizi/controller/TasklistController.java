package it.lorenzo.microservizi.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.lorenzo.microservizi.model.Task;
import it.lorenzo.microservizi.model.TaskGenericResponse;
import it.lorenzo.microservizi.model.TaskListGenericResponse;
import it.lorenzo.microservizi.model.TaskListInsertInput;
import it.lorenzo.microservizi.model.TaskListUpdateInput;
import it.lorenzo.microservizi.service.TasklistService;

@RestController
public class TasklistController {

	private final String API_TASK_PATH = "/task";
	private final String TASK_NOT_FOUND = "Task non trovato";
	private final String BAD_USAGE = "Errore nell'utilizzo del metodo";

	@Autowired
	private TasklistService tasklistService;

	@GetMapping(API_TASK_PATH)
	public TaskListGenericResponse getTask(
					@RequestParam(required = false) String name,
					@RequestParam(value="createdAfter", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date createdDate,
					@RequestParam(required = false) String category
	) {
		List<Task> responseList;

		// Dubbio: Chi deve gestire filtri in catena?
		if (name != null) responseList = this.tasklistService.getTask(name);
		else if (createdDate != null) responseList = this.tasklistService.getTasksNewerThan(createdDate);
		else if (category != null) responseList = this.tasklistService.getTasksByCategory(category);
		else responseList = this.tasklistService.getTasks();

		return new TaskListGenericResponse(200, "OK", responseList);
	}

	@GetMapping(API_TASK_PATH + "/{id}")
	public TaskGenericResponse getTaskById(@PathVariable int id) {
		try {
			return new TaskGenericResponse(200, "OK", this.tasklistService.getTask(id));
		} catch (Exception e) {
			return new TaskGenericResponse(404, this.TASK_NOT_FOUND);
		}
	}

	@PatchMapping(API_TASK_PATH)
	public TaskListGenericResponse changeStatus(@RequestBody TaskListUpdateInput task) {
		try {
			boolean done = this.tasklistService.changeTaskStatus(task.name, task.status);
			return new TaskListGenericResponse(done ? 200 : 404, done ? "Stato del task aggiornato" : this.TASK_NOT_FOUND);
		} catch (Exception e) {
			return new TaskListGenericResponse(400, this.BAD_USAGE);
		}
	}

	@PutMapping(API_TASK_PATH)
	public TaskListGenericResponse updateTask(@RequestBody TaskListUpdateInput task) {
		try {
			Boolean done = this.tasklistService.updateTask(task.name, task.category, task.status);
			return new TaskListGenericResponse(done ? 200 : 404, done ? "Task aggiornato" : this.TASK_NOT_FOUND);
		} catch (Exception e) {
			return new TaskListGenericResponse(400, this.BAD_USAGE);
		}
	}

	@PostMapping(API_TASK_PATH)
	public TaskListGenericResponse addTask(@RequestBody TaskListInsertInput task) {
		this.tasklistService.addTask(task.name, task.category);
		return new TaskListGenericResponse(200, "Task aggiunto");
	}

	@DeleteMapping(API_TASK_PATH)
	public TaskListGenericResponse removeTask(@RequestParam String name) {
		boolean done = this.tasklistService.removeTask(name);
		return new TaskListGenericResponse(done ? 200 : 404, done ? "Task rimosso" : this.TASK_NOT_FOUND);
	}
}
