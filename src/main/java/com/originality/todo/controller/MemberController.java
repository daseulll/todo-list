package com.originality.todo.controller;

import com.originality.todo.domain.Member;
import com.originality.todo.dto.CreateMemberRequest;
import com.originality.todo.dto.LoginRequest;
import com.originality.todo.dto.LoginResponse;
import com.originality.todo.global.annotation.RequestUser;
import com.originality.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> signup(@Valid @RequestBody CreateMemberRequest requestDto) {
        Member member = requestDto.toEntity();
        memberService.join(member, passwordEncoder);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest requestDto) {
        String token = memberService.login(requestDto.getEmail(), requestDto.getPassword(), passwordEncoder);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestUser Member member) {
        memberService.logout(member);
        return ResponseEntity.ok().body("로그아웃이 완료되었습니다.");
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteAccount(@RequestUser Member member) {
        memberService.deleteAccount(member);
        return ResponseEntity.ok().body("탈퇴가 완료되었습니다.");
    }

}
