package com.originality.todo.service;

import com.originality.todo.domain.Member;
import com.originality.todo.domain.Task;
import com.originality.todo.domain.TaskStatus;
import com.originality.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void createTask(Task task) {
        Member member = task.getMember();
        member.addTask(task);
        taskRepository.save(task);
    }

    public void updateTaskStatus(Member member, Long taskId, TaskStatus status) {
        Optional<Task> findTask = taskRepository.findById(taskId);
        if (findTask.isPresent()) {
            Task task = findTask.get();

            if (task.getMember() != member) {
                throw new IllegalStateException("Task를 소유한 유저만 수정할 수 있습니다.");
            }
            task.setStatus(status);
            taskRepository.save(task);
        }
    }
}
