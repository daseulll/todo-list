package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.domain.Task;
import com.originality.todo.repository.MemberRepository;
import com.originality.todo.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @DisplayName("최신 할일 1개 조회")
    void findLatest() {
        Member member = createMember("violet@gmail.com", "password123!");
        Task task1 = Task.builder()
                .member(member)
                .content("공부하기")
                .build();

        Task task2 = Task.builder()
                .member(member)
                .content("공부하기")
                .build();
        taskService.createTask(task1);
        taskService.createTask(task2);

        Task latestTask = taskService.findLatest(member);

        Assertions.assertThat(latestTask).isEqualTo(task2);
    }

    @Test
    @DisplayName("내 모든 할일 조회")
    void findAllTasks() {
        Member member = createMember("violet@gmail.com", "password123!");
        Task task1 = Task.builder()
                .member(member)
                .content("공부하기")
                .build();

        Task task2 = Task.builder()
                .member(member)
                .content("공부하기")
                .build();
        taskService.createTask(task1);
        taskService.createTask(task2);

        List<Task> tasks = taskService.findAll(member);

        Assertions.assertThat(tasks.size()).isEqualTo(2);
        Assertions.assertThat(tasks.get(0)).isEqualTo(task2);
        Assertions.assertThat(tasks.get(1)).isEqualTo(task1);
    }

    @Test
    @DisplayName("할일 추가")
    void createTask() {
        Member member = createMember("violet@gmail.com", "password123!");
        Task task = Task.builder()
                .member(member).build();

        taskService.createTask(task);

        Optional<Task> addedTask = taskRepository.findById(task.getId());
        Assertions.assertThat(addedTask.get()).isEqualTo(task);
        Assertions.assertThat(member.getTasks()).contains(task);
    }
}