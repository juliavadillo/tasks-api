package com.project.tasksmanager.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException (String message){super(message);}
}
