package com.example.taskmanager.model.dto;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.types.Priority;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TaskDto {

	private Long id;

	private String name;
	
	private Boolean done;

	private Instant created;
	
	private Priority priority;

	public TaskDto() {
	}

	public TaskDto(Task task) {
		this.id = task.getId();
		this.name = task.getName();
		this.done = task.getDone();
		this.created = task.getCreated();
		this.priority = task.getPriority();
	}
}
