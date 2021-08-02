package com.project.tasksmanager.services;

import com.project.tasksmanager.payloads.CreateTaskRequest;
import com.project.tasksmanager.payloads.TaskResponse;
import com.project.tasksmanager.payloads.UpdateTaskRequest;

import java.util.List;


public interface TaskService {

    Long createTask(CreateTaskRequest createRequest);

    TaskResponse getTaskById(Long id);

    TaskResponse updateTask(Long id, UpdateTaskRequest request);

    void deleteTask(Long id);

    String getDescription(Long id);

    List<TaskResponse> getAllTasks();

    List<String> getAllTasksDescriptions();
}
