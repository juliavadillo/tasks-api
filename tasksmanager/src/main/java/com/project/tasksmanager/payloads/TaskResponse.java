package com.project.tasksmanager.payloads;

import com.project.tasksmanager.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private String status;

    public static TaskResponse valueOf(Task task) {
        return TaskResponse.builder()
                .id(String.valueOf(task.getId()))
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus().name())
                .build();
    }
}
