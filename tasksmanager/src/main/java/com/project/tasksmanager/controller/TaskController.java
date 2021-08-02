package com.project.tasksmanager.controller;

import com.project.tasksmanager.payloads.CreateTaskRequest;
import com.project.tasksmanager.payloads.TaskResponse;
import com.project.tasksmanager.payloads.UpdateTaskRequest;
import com.project.tasksmanager.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Validated
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Long> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok().body(taskService.createTask(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(taskService.getTaskById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable("id") Long id, @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok().body(taskService.updateTask(id, request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/describe/{id}")
    public ResponseEntity<String> getDescription(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(taskService.getDescription(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @GetMapping(path = "/describe")
    public ResponseEntity<List<String>> describeAllTasks() {
        return ResponseEntity.ok().body(taskService.getAllTasksDescriptions());
    }


}
