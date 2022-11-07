package com.originality.todo.dto;

import com.originality.todo.domain.Task;
import com.originality.todo.domain.TaskStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TaskDto implements Serializable {
    private Long id;
    private String content;
    private TaskStatus status;
    private LocalDateTime createDateTime;

    public TaskDto() {
    }

    public TaskDto(Task task) {
        this.id = task.getId();
        this.content = task.getContent();
        this.status = task.getStatus();
        this.createDateTime = task.getCreateDateTime();
    }
}
