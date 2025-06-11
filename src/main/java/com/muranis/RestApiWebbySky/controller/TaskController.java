package com.muranis.RestApiWebbySky.controller;

import com.muranis.RestApiWebbySky.dto.TaskDto;
import com.muranis.RestApiWebbySky.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/new-task")
    public ResponseEntity<?> newTask(
            @RequestBody TaskDto taskDto,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        System.out.println(taskDto.getTime());
        return taskService.createNewTask(taskDto, authorizationHeader);
    }

    @GetMapping("/get-user-tasks")
    public ResponseEntity<?> getUserTasks(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        return taskService.getUserTasks(authorizationHeader);
    }

    @GetMapping("/get-user-tasks-by-date")
    public ResponseEntity<?> getUserTasksByDate(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String date
    ){
        return taskService.getUserTasksByDate(authorizationHeader, date);
    }

    @GetMapping("/get-task-details/{taskId}")
    public ResponseEntity<?> getTaskDetails(
            @PathVariable Long taskId
    ){
        return taskService.getTaskById(taskId);
    }

    @PostMapping("/delete-task/{taskId}")
    public ResponseEntity<?> deleteTaskById(
            @PathVariable Long taskId
    ){
        return taskService.deleteTaskById(taskId);
    }

    @GetMapping("/count-user-tasks-by-date")
    public ResponseEntity<?> countUserTasksByDate(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam String date
    ) {
        return taskService.countUserTasksByDate(authorizationHeader, date);
    }
}
