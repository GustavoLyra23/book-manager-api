package com.bookmanager.bookmanager.dto.error;

import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
public class ValidationError extends StandardError {

    private Set<FieldError> fieldErrors = new HashSet<>();


    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addFieldError(FieldError fieldError) {
        fieldErrors.add(fieldError);
    }


}
