package com.project.tasksmanager.repository;

import com.project.tasksmanager.domain.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
