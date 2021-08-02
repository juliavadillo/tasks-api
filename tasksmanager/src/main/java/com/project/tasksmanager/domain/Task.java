package com.project.tasksmanager.domain;

import com.project.tasksmanager.utils.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.project.tasksmanager.utils.TaskStatus.CREATED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.ORDINAL)
    private TaskStatus status = CREATED;
}
