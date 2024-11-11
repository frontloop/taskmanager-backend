package com.example.taskmanager.controller;

import com.example.taskmanager.model.dto.TaskDto;
import com.example.taskmanager.service.TaskService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("${app.endpoint.api}/task")
@CrossOrigin(origins = "${allowed.origins}", allowCredentials = "true")
public class TaskController {

	private static final Logger LOG = LoggerFactory.getLogger(TaskController.class);

	private final TaskService taskService;

	protected TaskController(TaskService service) {
		this.taskService = service;
	}

	@GetMapping("/all")
	public ResponseEntity<List<TaskDto>> getAll() {
		List<TaskDto> dtos = this.taskService.getAll();
		return ResponseEntity.ok(dtos);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<TaskDto> create(@RequestBody TaskDto entity) {
		var task = this.taskService.create(entity);
		TaskDto dto = new TaskDto(task);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/{taskId}/save")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<TaskDto> save(@PathVariable("taskId") final long taskId,
			@RequestBody TaskDto entity) {
		var task = this.taskService.save(taskId, entity);
		TaskDto dto = new TaskDto(task);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/delete")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<TaskDto> delete(@RequestBody Long taskId) {
		var task = this.taskService.delete(taskId);
		TaskDto dto = new TaskDto(task);
		return ResponseEntity.ok(dto);
	}
}
