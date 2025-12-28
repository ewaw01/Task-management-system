package com.example.Task_ManagementSystem.Task;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

//класс - бизнес логика
@Service
public class TaskService {

    private final TaskRepository repository;

    private TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Task getTaskById(
            long id
    ) {
        TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Reservation not found by id: " + id
        ));
        return mapper.toDomainTask(taskEntity);
    }

    public List<Task> SearchAllByFilter(
            TaskSearchFilter filter
    ) {
        int pageSize = filter.pageSize() != null ?
                filter.pageSize() : 10;
        int pageNumber = filter.pageNum() != null ?
                filter.pageNum() : 0;

        var pageable = PageRequest.of(pageNumber, pageSize);

        Page<TaskEntity> pageResult = repository.searchAllByFilter(
                filter.creatorId(),
                filter.assignedUserId(),
                filter.status(),
                filter.priority(),
                pageable
        );

        return pageResult.getContent()
                .stream()
                .map(mapper::toDomainTask)
                .collect(Collectors.toList());
    }

    public Task createTask(Task task) {
        if (task.id() != null) { //выбросим исключение, что клиент попытался сохранить бронирование с каким-то заданным id, так не делается, т к id задаются самим бекендом а не пользователем
            throw new IllegalArgumentException("Id should be empty"); //нельзя задавать id клиенту
        }
        if(task.status() != null) {
            throw new IllegalArgumentException("Status should be empty"); //статус тоже задается системой, не клиентом
        }
        var newTask = new TaskEntity(
                null,
                task.creatorId(),
                task.assignedUserId(),
                TaskStatus.CREATED,
                task.createDateTime(),
                task.deadlineDate(),
                task.priority()
        );

        repository.save(newTask);
        return mapper.toDomainTask(newTask);
    }

    public Task updateTask(
            Task taskToUpdate, Long id
    ) {
        var oldTask = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Task with id " + taskToUpdate.id() + " not found"
        ));

        if(oldTask.getStatus() == TaskStatus.DONE) {
            throw new IllegalArgumentException("Task with id " + taskToUpdate.id() + " is not in progress");
        }

        var newTask = new TaskEntity(
                oldTask.getId(),
                taskToUpdate.creatorId(),
                taskToUpdate.assignedUserId(),
                oldTask.getStatus(),
                taskToUpdate.createDateTime(),
                taskToUpdate.deadlineDate(),
                taskToUpdate.priority()
        );

        var updatedTask = repository.save(newTask);
        return mapper.toDomainTask(updatedTask);
    }

    public Void deleteTask(Long id) {
        repository.deleteById(id);
        return null;
    }

    public Void taskStatusToInProgress(
            Long id
    ) {
        var oldTask = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Task with id " + id + " not found"
        ));

        if(oldTask.getAssignedUserId() == null) {
            throw new IllegalArgumentException("Task with id " + id + " has not been assigned to anyone");
        }

        List<TaskEntity> listOfAssignedTasks;
        try {
            listOfAssignedTasks = repository.findAllByAssignedUserId(oldTask.getAssignedUserId());
        } catch (NullPointerException e) {
            listOfAssignedTasks = new ArrayList<>() {};
        }

//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + listOfAssignedTasks);
        if(listOfAssignedTasks.size() < 4) {
//            throw new IllegalArgumentException("Number of tasks exceeds maximum");
            repository.save(new TaskEntity(
                    id,
                    oldTask.getCreatorId(),
                    oldTask.getAssignedUserId(),
                    TaskStatus.IN_PROGRESS,
                    oldTask.getCreateDateTime(),
                    oldTask.getDeadlineDate(),
                    oldTask.getPriority()
            ));
        }
        else {
            throw new IllegalArgumentException("Task with id " + id + " has already been assigned to anyone");
        }

        return null;
    }

    public Void doneTask(
            Long id
    ) {
        var taskToDone = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Task with id " + id + " not found"
        ));

        if(taskToDone.getAssignedUserId() == null || taskToDone.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("Task with id " + id + " has not been assigned to anyone or not in progress");
        }
        repository.save(new TaskEntity(
                taskToDone.getId(),
                taskToDone.getCreatorId(),
                taskToDone.getAssignedUserId(),
                TaskStatus.DONE,
                taskToDone.getCreateDateTime(),
                taskToDone.getDeadlineDate(),
                taskToDone.getPriority()
        ));
        return null;
    }

}
