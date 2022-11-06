package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입")
    void join() {
        Member member = Member.builder()
                .username("violet")
                .email("violet@gmail.com")
                .password("abc123!").build();

        memberService.join(member);

        Optional<Member> newMember = memberRepository.findById(member.getId());
        assertThat(member.equals(newMember));
    }

    @Test
    @DisplayName("중복 계정 확인")
    void duplicateMember() {
        Member member = Member.builder()
                .username("violet")
                .email("violet@gmail.com")
                .password("abc123!").build();
        memberService.join(member);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Member newMember = Member.builder()
                    .username("violet")
                    .email("violet@gmail.com")
                    .password("aaa999!").build();
            memberService.join(newMember);
        });
        assertEquals("해당 email의 유저가 이미 존재합니다.", exception.getMessage());
    }
}