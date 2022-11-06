package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(Member newMember, PasswordEncoder passwordEncoder) {
        Optional<Member> member = memberRepository.findByEmail(newMember.getEmail());
        if (member.isPresent()){
            throw new IllegalArgumentException("해당 email의 유저가 이미 존재합니다.");
        }

        newMember.setToken(UUID.randomUUID().toString());
        newMember.setPassword(
               passwordEncoder.encode(newMember.getPassword())
        );
        memberRepository.save(newMember);
    }

    public String login(String email, String password, PasswordEncoder passwordEncoder) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidParameterException("입력한 이메일의 계정이 존재하지 않습니다."));

        if (passwordEncoder.matches(password, member.getPassword())) {
            if (member.getToken() == "") {
                member.setToken(UUID.randomUUID().toString());
            }
            return member.getToken();
        }
        throw new IllegalStateException("비밀번호가 올바르지 않습니다.");
    }

    public Optional<Member> findByToken(String token) {
        return memberRepository.findByToken(token);
    }

    public void logout(Member member) {
        member.setToken("");
        memberRepository.save(member);
    }
}
