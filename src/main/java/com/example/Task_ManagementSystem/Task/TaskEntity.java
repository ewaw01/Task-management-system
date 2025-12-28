package com.example.Task_ManagementSystem.Task;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "tasks")
@Entity
public class TaskEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "creatorId")
    private Long creatorId;
    @Column(name = "assignedUserId")
    private Long assignedUserId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    @Column(name = "createDateTime")
    private LocalDateTime createDateTime;
    @Column(name = "deadlineDate")
    private LocalDate deadlineDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;
    @Column(name = "doneDateTime")
    private LocalDateTime doneDateTime;

    public TaskEntity(Long id, Long creatorId, Long assignedUserId, TaskStatus status, LocalDateTime createDateTime, LocalDate deadlineDate, TaskPriority priority) {
        this.id = id;
        this.creatorId = creatorId;
        this.assignedUserId = assignedUserId;
        this.status = status;
        this.createDateTime = createDateTime;
        this.deadlineDate = deadlineDate;
        this.priority = priority;
        this.doneDateTime = LocalDateTime.now();
    }

    public LocalDateTime getDoneDateTime() {
        return doneDateTime;
    }

    public void setDoneDateTime(LocalDateTime doneDateTime) {
        this.doneDateTime = doneDateTime;
    }

    public TaskEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDate getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDate deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id
                +
//                ", creatorId=" + creatorId +
//                ", assignedUserId=" + assignedUserId +
//                ", status=" + status +
//                ", createDateTime=" + createDateTime +
//                ", deadlineDate=" + deadlineDate +
//                ", priority=" + priority +
                '}'
                ;
    }
}
