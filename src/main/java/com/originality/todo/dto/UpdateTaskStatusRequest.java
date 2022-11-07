package com.originality.todo.dto;

import com.originality.todo.domain.TaskStatus;
import lombok.Data;

@Data
public class UpdateTaskStatusRequest {
    private Long taskId;
    private TaskStatus status;
}
