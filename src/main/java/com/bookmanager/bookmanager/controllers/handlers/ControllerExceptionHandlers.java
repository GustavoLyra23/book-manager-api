package com.bookmanager.bookmanager.controllers.handlers;

import com.bookmanager.bookmanager.dto.error.FieldError;
import com.bookmanager.bookmanager.dto.error.StandardError;
import com.bookmanager.bookmanager.dto.error.ValidationError;
import com.bookmanager.bookmanager.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

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


}
