package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    private Member createMember(String email, String password) {
        Member member = Member.builder()
                .username("member1")
                .email(email)
                .password(password)
                .token(UUID.randomUUID().toString())
                .build();

        memberService.join(member);
        return member;
    }

    @Test
    @DisplayName("회원가입 테스트")
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
    @DisplayName("중복 계정 가입 테스트")
    void duplicateMember() {
        createMember("violet@gmail.com", "password123");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Member newMember = Member.builder()
                    .username("violet")
                    .email("violet@gmail.com")
                    .password("aaa999!").build();
            memberService.join(newMember);
        });
        assertEquals("해당 email의 유저가 이미 존재합니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원 로그인 테스트")
    void login() {
        Member member = createMember("violet@gmail.com", "password123");

        String token = memberService.login("violet@gmail.com", "password123");

        assertEquals(member.getToken(), token);
    }

    @Test
    @DisplayName("잘못된 로그인 정보 예외 발생")
    void validateLogin() {
        createMember("violet@gmail.com", "password123");

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.login("v@gmail.com", "password123");
        });
        assertThrows(IllegalStateException.class, () -> {
            memberService.login("violet@gmail.com", "invalidPassword");
        });
    }
}