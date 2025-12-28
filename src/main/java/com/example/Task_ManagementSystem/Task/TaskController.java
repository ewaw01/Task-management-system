package com.example.Task_ManagementSystem.Task;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.NoSuchElementException;

//класс-контроллер, обработчик http-запросов (REST-запросов)
@RestController
@RequestMapping("/task")
public class TaskController {
    public TaskService taskService;

    private final Logger log = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(
            @PathVariable Long id
    ) {
        log.info("Called function get task id " + id);
        try {
            return ResponseEntity.ok().body(taskService.getTaskById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<List<Task>> getTasksByFilter(
            @RequestParam(name = "creatorId", required = false) Long creatorId,
            @RequestParam(name = "assignedUserId", required = false) Long assignedUserId,
            @RequestParam(name = "status", required = false) TaskStatus status,
            @RequestParam(name = "priority", required = false) TaskPriority priority,
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @RequestParam(name = "pageNum", required = false) Integer pageNum
    ) {
        log.info("Called function get tasks");

        var filter = new TaskSearchFilter(
                creatorId,
                assignedUserId,
                status,
                priority,
                pageSize,
                pageNum
        );

        return ResponseEntity.ok().body(taskService.SearchAllByFilter(
                filter
        ));
    }

    @PostMapping()
    public ResponseEntity<Task> createTask(
            @RequestBody @Valid Task task
    ) {
        log.info("Called function create task " + task);
        try {
            return ResponseEntity.status(201).body(taskService.createTask(task));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable("id") Long id,
            @RequestBody @Valid Task task
    ){
        log.info("Called function update task with id : " + id);
        try {
            return ResponseEntity.ok().body(taskService.updateTask(task, id));
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("id") Long id
    ) {
        log.info("Called function delete task with id : " + id);
        try {
            return ResponseEntity.ok().body(taskService.deleteTask(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startTask(
            @PathVariable Long id
    ) {
        log.info("Called function start task with id : " + id);
        try {
            return ResponseEntity.ok().body(taskService.taskStatusToInProgress(id));
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/done")
    public ResponseEntity<Void> doneTask(
            @PathVariable Long id
    ) {
        log.info("Called function done task with id : " + id);
        return ResponseEntity.ok().body(taskService.doneTask(id));
    }

}











