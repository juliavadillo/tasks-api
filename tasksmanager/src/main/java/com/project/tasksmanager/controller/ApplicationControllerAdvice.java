package com.project.tasksmanager.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.project.tasksmanager.exceptions.TaskNotFoundException;
import com.project.tasksmanager.utils.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> taskNotFoundException (TaskNotFoundException exception, WebRequest request){
        log.warn(exception.getLocalizedMessage());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> invalidFormatException(InvalidFormatException exception, WebRequest request){
        log.warn(exception.getLocalizedMessage());
        if (exception.getPathReference().contains("status")) {
            return ResponseEntity.badRequest().body("Available statuses are: CREATED, APPROVED, REJECTED, BLOCKED, DONE");
        }
        return ResponseEntity.badRequest().build();
    }

}
