package com.iodigital.ksp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstraintsViolationResponse {

    private String property;
    private String message;

    public ConstraintsViolationResponse(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
