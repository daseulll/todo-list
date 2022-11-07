package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.domain.Task;
import com.originality.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task findLatest(Member member) {
        return taskRepository.findFirstByMember(member, Sort.by(Sort.Order.desc("id")));
    }

    public List<Task> findAll(Member member) {
        return taskRepository.findByMember(member, Sort.by(Sort.Order.desc("id")));
    }

    public void add(Task task) {
        Member member = task.getMember();
        member.addTask(task);
        taskRepository.save(task);
    }
}
