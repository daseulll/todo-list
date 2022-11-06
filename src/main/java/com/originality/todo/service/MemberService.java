package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(Member newMember) {
        Optional<Member> member = memberRepository.findByEmail(newMember.getEmail());
        if (member.isPresent()){
            throw new IllegalArgumentException("해당 email의 유저가 이미 존재합니다.");
        }

        newMember.setPassword(
                passwordEncoder.encode(newMember.getPassword())
        );
        memberRepository.save(newMember);
    }
}
