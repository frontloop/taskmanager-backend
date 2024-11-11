package com.example.taskmanager.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.model.dto.TaskDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<TaskDto> getAll() {
		final List<Task> tasks = taskRepository.findAllByOrderByIdAsc();
		return tasks.stream().map(TaskDto::new).collect(Collectors.toList());
	}
	
	@Transactional
    public Task create(TaskDto transferObject) {
        Task task = new Task();
        
        task.setName(transferObject.getName());
        task.setDone(transferObject.getDone());
        task.setCreated(Instant.now());
        task.setPriority(transferObject.getPriority());
        
        task = taskRepository.save(task);
        
        return task;
    }
	
	@Transactional
    public Task save(Long taskId, TaskDto transferObject) {
        Task task = getTask(taskId);

        task.setName(transferObject.getName());
        task.setDone(transferObject.getDone());
        task.setPriority(transferObject.getPriority());
        
        task = taskRepository.save(task);
        
        return task;
    }
	
	@Transactional
    public Task delete(Long taskId) {
        Task task = getTask(taskId);
        taskRepository.delete(task);
        return task;
    }
 
	public Task getTask(Long id) {
        Optional<Task> oTask = taskRepository.findById(id);
        return oTask.orElse(null);
	 }

}
