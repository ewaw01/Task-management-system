package com.example.Task_ManagementSystem.Task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Task(
        Long id, //уникальный id
        @NotNull
        Long creatorId, //идентификатор пользователя, создавшего задачу
        Long assignedUserId, //идентификатор исполнителя
        TaskStatus status, //статус нашей задачи CREATED, IN_PROGRESS, DONE
        @FutureOrPresent
        @NotNull
        LocalDateTime createDateTime, //дата и время создания задачи
        @NotNull
        @Future
        LocalDate deadlineDate, //дата, до которой задача дожна быть выполнена
        @NotNull
        TaskPriority priority, //приоритет задачи (Low, Medium, High)
        LocalDateTime doneDateTime //для даты со временем окончания выполнения задачи
) {
        public Task {
                doneDateTime = LocalDateTime.now();
        }
}
