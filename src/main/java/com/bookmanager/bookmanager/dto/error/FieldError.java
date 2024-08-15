package com.bookmanager.bookmanager.dto.error;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class FieldError {

    private String field;
    private String message;

}
