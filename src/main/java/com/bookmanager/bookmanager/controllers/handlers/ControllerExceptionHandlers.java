package com.bookmanager.bookmanager.controllers.handlers;

import com.bookmanager.bookmanager.dto.error.FieldError;
import com.bookmanager.bookmanager.dto.error.StandardError;
import com.bookmanager.bookmanager.dto.error.ValidationError;
import com.bookmanager.bookmanager.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.aspectj.weaver.bcel.asm.AsmDetector.rootCause;

@ControllerAdvice
public class ControllerExceptionHandlers {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleException(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(), status.value(), e.getMessage(),
                "Resource not found", request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ValidationError validationError = new ValidationError(Instant.now(), status.value(), e.getMessage(),
                "Resource not found", request.getRequestURI());
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            validationError.addFieldError(new FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
        });

        return ResponseEntity.status(status).body(validationError);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<StandardError> handleException(IOException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError standardError = new StandardError(Instant.now(), status.value(), e.getMessage(),
                "File error", request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        String message = (rootCause != null) ? rootCause.getMessage() : "An unexpected database error occurred.";
        response.put("error", "Database error");
        response.put("message", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
