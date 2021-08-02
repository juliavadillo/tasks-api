package com.project.tasksmanager.services.impl;

import com.project.tasksmanager.domain.Task;
import com.project.tasksmanager.exceptions.TaskNotFoundException;
import com.project.tasksmanager.payloads.CreateTaskRequest;
import com.project.tasksmanager.payloads.TaskResponse;
import com.project.tasksmanager.payloads.UpdateTaskRequest;
import com.project.tasksmanager.repository.TaskRepository;
import com.project.tasksmanager.utils.TaskStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void shouldCreateTaskWithSuccess(){
        CreateTaskRequest request = CreateTaskRequest.builder().title("Task").description("Desc").build();
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(Task.builder().id(1L).build());
        Long createdId = taskService.createTask(request);
        assertEquals(1L,createdId);
    }

    @Test
    void shouldGetTaskByIdWithSuccess(){
        Task taskWithId1 = Task.builder().id(1L).title("Task to do").description("Things to do").status(TaskStatus.CREATED).build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(taskWithId1));
        TaskResponse response = taskService.getTaskById(1L);
        assertEquals(taskWithId1.getId(),Long.valueOf(response.getId()));
        assertEquals(taskWithId1.getTitle(),response.getTitle());
        assertEquals(taskWithId1.getDescription(),response.getDescription());
        assertEquals(taskWithId1.getStatus().name(),response.getStatus());
    }

    @Test
    void whenGetByIdShouldThrowExceptionWhenIdDoesntExist(){
        Mockito.when(taskRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(5L));
    }

    @Test
    void shouldUpdateTaskWithSuccess(){
        Task taskToBeUpdated = Task.builder().id(1L).title("Task to do").description("Things to do").status(TaskStatus.CREATED).build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(taskToBeUpdated));

        Task taskWithNewValues = Task.builder().id(1L).title("New title").description("New Desc").status(TaskStatus.BLOCKED).build();
        Mockito.when(taskRepository.save(taskToBeUpdated)).thenReturn(taskWithNewValues);

        UpdateTaskRequest taskRequest = UpdateTaskRequest.builder().title("New title").description("New Desc").status(TaskStatus.BLOCKED).build();

        TaskResponse response = taskService.updateTask(1L, taskRequest);

        assertEquals(taskToBeUpdated.getId(), Long.valueOf(response.getId()));
        assertEquals(taskWithNewValues.getTitle(),response.getTitle());
        assertEquals(taskWithNewValues.getDescription(),response.getDescription());
        assertEquals(taskWithNewValues.getStatus().name(),response.getStatus());
    }
    @Test
    void whenUpdateTaskShouldThrowExceptionWhenIdDoesntExist(){
        Mockito.when(taskRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(5L,UpdateTaskRequest.builder().build()));
    }

    @Test
    void shouldReturnTaskDescriptionWithSuccess(){
        Task taskToBeUpdated = Task.builder().id(1L).title("Task to do").description("Things to do").status(TaskStatus.CREATED).build();
        Mockito.when(taskRepository.findById(1L)).thenReturn(Optional.of(taskToBeUpdated));

        String description = taskService.getDescription(1L);
        assertEquals("Description of Task [1: Task to do] is: Things to do",description);
    }

    @Test
    void whenGetTaskDescriptionShouldThrowExceptionWhenIdDoesntExist(){
        Mockito.when(taskRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getDescription(5L));
    }

    @Test
    void whenDeleteTaskShouldThrowExceptionWhenIdDoesntExist(){
        Mockito.when(taskRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(5L));
    }

    @Test
    void shouldReturnAllTasksRegisteredWithSuccess(){
        Iterable<Task> tasksList = Arrays.asList(Task.builder().id(1L).title("Task to do").description("Things to do").status(TaskStatus.CREATED).build(),
                                             Task.builder().id(2L).title("Task to do2").description("Things to do2").status(TaskStatus.CREATED).build());

        Mockito.when(taskRepository.findAll()).thenReturn(tasksList);

        List<TaskResponse> responseList = Arrays.asList(TaskResponse.builder().id("1").title("Task to do").description("Things to do").status(TaskStatus.CREATED.name()).build(),
                TaskResponse.builder().id("2").title("Task to do2").description("Things to do2").status(TaskStatus.CREATED.name()).build());

        List<TaskResponse> resultList = taskService.getAllTasks();
        assertEquals(responseList,resultList);
    }

    @Test
    void shouldReturnAllTasksDescriptions(){
        Iterable<Task> tasksList = Arrays.asList(Task.builder().id(1L).title("Task to do").description("Things to do").status(TaskStatus.CREATED).build(),
                Task.builder().id(2L).title("Task to do2").description("Things to do2").status(TaskStatus.CREATED).build());

        Mockito.when(taskRepository.findAll()).thenReturn(tasksList);

        List<String> listDescriptions = Arrays.asList("Description of Task [1: Task to do] is: Things to do", "Description of Task [2: Task to do2] is: Things to do2");

        List<String> responseDescriptions = taskService.getAllTasksDescriptions();
        assertEquals(listDescriptions,responseDescriptions);
    }



}
