package com.valeo.task.exception;

import com.valeo.task.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "No Content", e.getMessage()));
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleTaskNotFoundException(TaskNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponseDTO<>(HttpStatus.NO_CONTENT.value(), e.getMessage(), null));
    }
    @ExceptionHandler({RequestParamMissingException.class, RequestBodyMissingException.class})
    public ResponseEntity<ApiResponseDTO<Void>> handleMissingParameterException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
    }

}

