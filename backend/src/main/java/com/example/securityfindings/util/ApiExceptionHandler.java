package com.example.securityfindings.util;

import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        ApiError error = new ApiError(Instant.now(), HttpStatus.NOT_FOUND.value(), "Not Found", ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ApiError error = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Validation Error", "Validation failed", details);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex) {
        ApiError error = new ApiError(Instant.now(), HttpStatus.BAD_REQUEST.value(), "Validation Error", ex.getMessage(), List.of());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex) {
        ApiError error = new ApiError(Instant.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server Error", ex.getMessage(), List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
