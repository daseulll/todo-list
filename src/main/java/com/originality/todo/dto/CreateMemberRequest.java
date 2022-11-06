package com.originality.todo.dto;

import com.originality.todo.domain.Member;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateMemberRequest {
    @NotEmpty(message = "이메일은 필수 입력값 입니다.")
    private String email;

    @NotEmpty(message = "닉네임은 필수 입력값 입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수 입력값 입니다.")
    private String password;

    public Member toEntity() {
        return Member.builder()
                .email(this.email)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
