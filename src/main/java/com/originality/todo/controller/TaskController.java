package com.originality.todo.controller;

import com.originality.todo.domain.Member;
import com.originality.todo.domain.Task;
import com.originality.todo.dto.CreateTaskRequest;
import com.originality.todo.dto.TaskDto;
import com.originality.todo.global.annotation.RequestUser;
import com.originality.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/latest")
    public ResponseEntity<TaskDto> retrieveLatestTask(@RequestUser Member member) {
        Task latestTask = taskService.findLatest(member);

        if (latestTask == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.ok(new TaskDto(latestTask));
    }

    @GetMapping()
    public ResponseEntity<List<TaskDto>> listTasks(@RequestUser Member member) {
        List<Task> tasks = taskService.findAll(member);

        List<TaskDto> taskDtos = tasks.stream().map(TaskDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(taskDtos);
    }

    @PostMapping()
    public ResponseEntity<TaskDto> createTask(@RequestUser Member member, @Valid @RequestBody CreateTaskRequest createTaskDto) {
        Task task = createTaskDto.toEntity(member);
        taskService.createTask(task);
        return new ResponseEntity(new TaskDto(task), HttpStatus.CREATED);
    }
}
