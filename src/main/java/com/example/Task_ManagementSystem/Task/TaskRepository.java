package com.example.Task_ManagementSystem.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("""
    select r from TaskEntity r where r.assignedUserId = :assigned_user_id and r.status = "IN_PROGRESS"
    """)
    List<TaskEntity> findAllByAssignedUserId(
            @Param("assigned_user_id") Long assignedUserId
    );

    @Query("""
        SELECT r from TaskEntity r
                WHERE (:creatorId IS NULL OR r.creatorId = :creatorId)
                AND (:assignedUserId IS NULL OR r.assignedUserId = :assignedUserId)
                AND (:status IS NULL OR r.status = :status)
                AND (:priority IS NULL OR r.priority = :priority)
        """)
    Page<TaskEntity> searchAllByFilter(
            @Param("creatorId") Long creatorId,
            @Param("assignedUserId") Long assignedUserId,
            @Param("status") TaskStatus status,
            @Param("priority") TaskPriority priority,
            Pageable pageable
    );
}
