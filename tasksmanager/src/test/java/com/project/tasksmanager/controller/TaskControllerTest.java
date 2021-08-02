package com.project.tasksmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tasksmanager.exceptions.TaskNotFoundException;
import com.project.tasksmanager.payloads.CreateTaskRequest;
import com.project.tasksmanager.payloads.TaskResponse;
import com.project.tasksmanager.payloads.UpdateTaskRequest;
import com.project.tasksmanager.services.TaskService;
import com.project.tasksmanager.utils.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TaskService service;

    @Test
    void shouldCreateTaskWithSuccess() throws Exception {
        CreateTaskRequest request = CreateTaskRequest.builder().title("Task1").description("Description1").build();

        Mockito.when(service.createTask(request)).thenReturn(1L);

        MvcResult result = mockMvc.perform(post("/tasks")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        assertEquals(1L, Long.valueOf(content));
    }

    @Test
    void shouldReturnTaskByIdWithSuccess() throws Exception {
        TaskResponse response = TaskResponse.builder()
                .id("1")
                .title("Task1")
                .description("Description1")
                .status("APPROVED")
                .build();

        Mockito.when(service.getTaskById(1L)).thenReturn(response);

        mockMvc.perform(get("/tasks/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTaskByIdWithExceptionWhenIdDoesntExist() throws Exception {

        Mockito.when(service.getTaskById(1L)).thenThrow(TaskNotFoundException.class);

        mockMvc.perform(get("/tasks/1")
                        .contentType("application/json"))
                        .andExpect(status()
                        .isNoContent());
    }

    @Test
    void shouldUpdateTaskWithSuccess() throws Exception {
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .title("Task1")
                .description("Description1")
                .status(TaskStatus.CREATED)
                .build();

        TaskResponse response = TaskResponse.builder()
                .id("1")
                .title("Task1")
                .description("Description1")
                .status("CREATED")
                .build();

        Mockito.when(service.updateTask(1L, request)).thenReturn(response);

        mockMvc.perform(put("/tasks/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldThrowExceptionWhenUpdateTaskByUnexistingId() throws Exception {
        UpdateTaskRequest request = UpdateTaskRequest.builder()
                .title("Task1")
                .description("Description1")
                .status(TaskStatus.CREATED)
                .build();

        Mockito.when(service.updateTask(1L, request)).thenThrow(TaskNotFoundException.class);

        mockMvc.perform(put("/tasks/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status()
                .isNoContent());
    }

    @Test
    void shouldDeleteWithSuccess() throws Exception {
        mockMvc.perform(delete("/tasks/1")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetDescriptionsWithSuccess() throws Exception {

        Mockito.when(service.getDescription(1L)).thenReturn("Description of task");

        MvcResult result = mockMvc.perform(get("/tasks/describe/1"))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("Description of task", content);
    }

    @Test
    void shouldThrowExceptionWhenGetDescriptionsTaskByUnexistingId() throws Exception {
        Mockito.when(service.getDescription(10L)).thenThrow(TaskNotFoundException.class);

        mockMvc.perform(get("/tasks/describe/10"))
                        .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnAllTasks() throws Exception {
        List<TaskResponse> allTasks = Arrays.asList(TaskResponse.builder().id("1").title("Task1").description("Description 1").build(), TaskResponse.builder().id("2").title("Task2").description("Description 2").build());

        Mockito.when(service.getAllTasks()).thenReturn(allTasks);

        MvcResult result = mockMvc.perform(get("/tasks"))
                                          .andExpect(status().isOk())
                                          .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(allTasks), content);
    }

    @Test
    void shouldReturnAllTasksDescriptions() throws Exception {
        List<String> allTasksDescriptions = Arrays.asList("Desc1", "Desc2","Desc3");

        Mockito.when(service.getAllTasksDescriptions()).thenReturn(allTasksDescriptions);

        MvcResult result = mockMvc.perform(get("/tasks/describe"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(allTasksDescriptions), content);
    }



}
