package com.originality.todo.dto;

import com.originality.todo.domain.Member;
import com.originality.todo.domain.Task;
import lombok.Data;

@Data
public class CreateTaskRequest {
    private String content;

    public Task toEntity(Member member) {
        return Task.builder()
                .member(member)
                .content(this.content)
                .build();
    }
}
