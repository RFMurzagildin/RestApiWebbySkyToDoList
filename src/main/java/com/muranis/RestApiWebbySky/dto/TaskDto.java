package com.muranis.RestApiWebbySky.dto;

import com.muranis.RestApiWebbySky.model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long id;
    private String description;
    private LocalDateTime time;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.time = task.getTime();
    }
}
