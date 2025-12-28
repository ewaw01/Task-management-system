package com.example.Task_ManagementSystem.Web;

import java.time.LocalDateTime;

public record ErrorResponseDto (
        String message,
        String detailMessage,
        LocalDateTime errorTime
) {

}
