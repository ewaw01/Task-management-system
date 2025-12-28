package com.example.Task_ManagementSystem.Web;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(
            Exception e
    ) {

        log.error("Handle Exception", e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDto(
                "Something went wrong",
                e.getMessage(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFound(
            EntityNotFoundException e
    ) {

        log.error("Handle EntityNotFoundException", e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(
                "Entity not found",
                e.getMessage(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(exception = {
            IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class
            }
    )
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(
            Exception e
    ) {

        log.error("Handle BadRequest", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(
                "Bad request",
                e.getMessage(),
                LocalDateTime.now()
        ));
    }

}
