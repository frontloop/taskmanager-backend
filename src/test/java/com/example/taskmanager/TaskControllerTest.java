package com.example.taskmanager;

import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.taskmanager.controller.TaskController;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.model.dto.TaskDto;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.types.Priority;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService service;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	TaskControllerTest() {
		mapper.registerModule(new JavaTimeModule());
	}

	@Test
	void createShouldReturnTaskFromService() throws Exception {
		
		Task task = new Task();
		task.setId(0L);
		task.setName("TestTask");
		task.setDone(false);
		task.setCreated(Instant.now());
		task.setPriority(Priority.NORMAL);
		
		TaskDto dto = new TaskDto(task);
		
		when(service.create(any(TaskDto.class))).thenReturn(new Task(dto));
		
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/task/create")
						.content(mapper.writeValueAsString(dto)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		
	    var returnTask = mapper.readValue(result.getResponse().getContentAsString(), TaskDto.class);
	    
		assertThat(returnTask.getPriority()).isEqualTo(Priority.NORMAL);
	}
}