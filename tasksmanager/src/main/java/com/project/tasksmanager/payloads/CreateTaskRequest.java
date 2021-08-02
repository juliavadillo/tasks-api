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
public class CreateTaskRequest {

    String title;
    String description;

    public Task toTaskDomain() {
        return Task.builder()
                .title(this.title)
                .description(this.description)
                .build();
    }
}
