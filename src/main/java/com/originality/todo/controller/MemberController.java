package com.originality.todo.controller;

import com.originality.todo.domain.Member;
import com.originality.todo.dto.CreateMemberRequest;
import com.originality.todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> signup(@Valid @RequestBody CreateMemberRequest requestDto) {
        Member member = requestDto.toEntity();
        memberService.join(member);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
