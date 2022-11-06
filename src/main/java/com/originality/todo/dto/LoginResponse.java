package com.originality.todo.dto;

import com.originality.todo.domain.Member;
import lombok.Data;

@Data
public class LoginResponse {

    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
