package com.example.taskmanager.domain;

import com.example.taskmanager.model.dto.TaskDto;
import com.example.taskmanager.types.Priority;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TASK")
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Task {

	@Id
	@SequenceGenerator(name = "TASK_ID_GENERATOR", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_ID_GENERATOR")
	private Long id;

	@Column
	private String name;
	
	@Column
	private Boolean done;
	
	@Column
	private Instant created;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Priority priority;

	public Task() {
	}

	public Task(TaskDto task) {
		this.name = task.getName();
		this.done = task.getDone();
		this.created = task.getCreated();
		this.priority = task.getPriority();
	}
}
