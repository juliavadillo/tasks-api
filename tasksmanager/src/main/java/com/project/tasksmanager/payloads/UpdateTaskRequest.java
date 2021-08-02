package com.project.tasksmanager.payloads;

import com.project.tasksmanager.utils.TaskStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Builder
@Data
@Validated
public class UpdateTaskRequest {
    String title;
    String description;
    TaskStatus status;
}
