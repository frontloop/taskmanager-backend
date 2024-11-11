package com.example.taskmanager;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.model.dto.TaskDto;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.types.Priority;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Instant;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService service;

	@Test
	void createShouldReturnTaskFromService() throws Exception {
		Task task = new Task();
		task.setId(0L);
		task.setName("TestTask");
		task.setDone(false);
		task.setCreated(Instant.now());
		task.setPriority(Priority.NORMAL);
		
		TaskDto dto = new TaskDto(task);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
	    String json = gson.toJson(dto);
		
		when(service.create(any(TaskDto.class))).thenReturn(new Task(dto));
		this.mockMvc.perform(post("/api/task/create", dto)
				.content(json).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andReturn();
	}
}