package com.project.tasksmanager.services.impl;

import com.project.tasksmanager.domain.Task;
import com.project.tasksmanager.exceptions.TaskNotFoundException;
import com.project.tasksmanager.payloads.CreateTaskRequest;
import com.project.tasksmanager.payloads.TaskResponse;
import com.project.tasksmanager.payloads.UpdateTaskRequest;
import com.project.tasksmanager.repository.TaskRepository;
import com.project.tasksmanager.services.TaskService;
import com.project.tasksmanager.utils.DescriptionMapper;
import com.project.tasksmanager.utils.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public Long createTask(CreateTaskRequest createRequest) {
        Task newTask = createRequest.toTaskDomain();
        newTask.setStatus(TaskStatus.CREATED);
        return this.repository.save(newTask).getId();
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        return TaskResponse.valueOf(this.repository.findById(id).orElseThrow(() -> new TaskNotFoundException("There is no task for the given id: "+ id)));
    }

    @Override
    public TaskResponse updateTask(Long id, UpdateTaskRequest request) {
       Task taskToBeUpdated = this.repository.findById(id).orElseThrow(() -> new TaskNotFoundException("There is no task for the given id: "+ id));
       taskToBeUpdated.setTitle(request.getTitle());
       taskToBeUpdated.setDescription(request.getDescription());
       taskToBeUpdated.setStatus(request.getStatus());

       return TaskResponse.valueOf(this.repository.save(taskToBeUpdated));
    }

    @Override
    public void deleteTask(Long id) {
        Task taskToBeDeleted = this.repository.findById(id).orElseThrow(() -> new TaskNotFoundException("There is no task for the given id: "+ id));
        this.repository.delete(taskToBeDeleted);
    }

    @Override
    public String getDescription(Long id) {
        Task task = this.repository.findById(id).orElseThrow(() -> new TaskNotFoundException("There is no task for the given id: "+ id));
        return DescriptionMapper.mapToDescriptionMessage(task.getDescription(), task.getId(), task.getTitle());
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        Iterable<Task> allTasks = this.repository.findAll();
        return StreamSupport.stream(allTasks.spliterator(), false)
                .map(TaskResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTasksDescriptions() {
        Iterable<Task> allTasks = this.repository.findAll();
        return StreamSupport.stream(allTasks.spliterator(), false)
                .map(task -> DescriptionMapper.mapToDescriptionMessage(task.getDescription(), task.getId(), task.getTitle()))
                .collect(Collectors.toList());
    }
}
