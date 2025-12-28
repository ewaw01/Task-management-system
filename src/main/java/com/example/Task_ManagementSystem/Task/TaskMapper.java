package com.example.Task_ManagementSystem.Task;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toDomainTask(TaskEntity task) {
        return new Task(
                task.getId(),
                task.getCreatorId(),
                task.getAssignedUserId(),
                task.getStatus(),
                task.getCreateDateTime(),
                task.getDeadlineDate(),
                task.getPriority(),
                task.getDoneDateTime()
        );
    }

//    public TaskEntity toEntity(Task task) {
//        return new TaskEntity(
//                task.id(),
//                task.creatorId(),
//                task.assignedUserId(),
//                task.status(),
//                task.createDateTime(),
//                task.deadlineDate(),
//                task.priority(),
//                task.doneDateTime()
//        );
//    }

}
