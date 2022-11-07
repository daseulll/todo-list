package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.domain.Task;
import com.originality.todo.repository.MemberRepository;
import com.originality.todo.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskServiceTest {
    @Autowired TaskService taskService;
    @Autowired TaskRepository taskRepository;
    @Autowired MemberRepository memberRepository;

    private Member createMember(String email, String password) {
        Member member = Member.builder()
                .username("member1")
                .email(email)
                .password(password)
                .build();
        memberRepository.save(member);
        return member;
    }

    @Test
    @DisplayName("최신 1개 할일 조회")
    void retrieve() {
        Member member = createMember("violet@gmail.com", "password123!");
        Task task = Task.builder()
                .member(member)
                .content("공부하기")
                .build();
        taskService.add(task);

        taskService.retrieve(member);

//        Assertions.assertThat(findTask.get()).isEqualTo(task);
    }

    @Test
    @DisplayName("할일 추가")
    void add() {
        Member member = createMember("violet@gmail.com", "password123!");
        Task task = Task.builder()
                .member(member).build();

        taskService.add(task);

        Optional<Task> addedTask = taskRepository.findById(task.getId());
        Assertions.assertThat(addedTask.get()).isEqualTo(task);
        Assertions.assertThat(member.getTasks()).contains(task);
    }
}