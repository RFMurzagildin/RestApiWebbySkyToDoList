package com.muranis.RestApiWebbySky.service;

import com.muranis.RestApiWebbySky.dto.TaskDto;
import com.muranis.RestApiWebbySky.exceptions.ApiResponse;
import com.muranis.RestApiWebbySky.model.Task;
import com.muranis.RestApiWebbySky.model.User;
import com.muranis.RestApiWebbySky.repository.TaskRepository;
import com.muranis.RestApiWebbySky.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    public ResponseEntity<?> createNewTask(TaskDto taskDto, String authorizationHeader){
        String token = authorizationHeader.substring(7);
        String username = jwtTokenUtils.getUsername(token);
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Task task = Task.builder()
                    .description(taskDto.getDescription())
                    .time(taskDto.getTime())
                    .user(user)
                    .build();
            taskRepository.save(task);
            return new ResponseEntity<>(new ApiResponse(true, "Task added"), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ApiResponse(false, "Missing token"), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> getUserTasks(String authorization) {
        String token = authorization.substring(7);
        String username = jwtTokenUtils.getUsername(token);
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Task> tasks = taskRepository.findByUser(user);
            List<TaskDto> responseTasks = tasks.stream()
                    .map(TaskDto::new)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(responseTasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getUserTasksByDate(String authorization, String date) {
        String token = authorization.substring(7);
        String username = jwtTokenUtils.getUsername(token);
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            LocalDate parsedDate = LocalDate.parse(date);
            List<Task> tasks = taskRepository.findByUserAndDate(user, parsedDate);
            List<TaskDto> responseTasks = tasks.stream()
                    .map(TaskDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(responseTasks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> getTaskById(Long taskId){
        Task task = taskRepository.getTaskById(taskId);
        if(task == null){
            return new ResponseEntity<>(new ApiResponse(false, "Task not found"), HttpStatus.BAD_REQUEST);
        }
        TaskDto taskDto = new TaskDto(
          task.getId(),
          task.getDescription(),
          task.getTime()
        );
        return new ResponseEntity<>(taskDto, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteTaskById(Long taskId){
        taskRepository.deleteById(taskId);
        return new ResponseEntity<>(new ApiResponse(true, "Task deleted"), HttpStatus.OK);
    }

    public ResponseEntity<?> countUserTasksByDate(String authorization, String date) {
        String token = authorization.substring(7);
        String username = jwtTokenUtils.getUsername(token);
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {    
            User user = optionalUser.get();
            LocalDate parsedDate = LocalDate.parse(date);
            Long taskCount = taskRepository.countTasksByUserAndDate(user, parsedDate);

            return new ResponseEntity<>(Collections.singletonMap("count", taskCount), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }
}
