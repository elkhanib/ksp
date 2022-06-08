package com.iodigital.ksp.exception.handler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.iodigital.ksp.domain.ConstraintsViolationResponse;
import com.iodigital.ksp.exception.RecordNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static com.iodigital.ksp.common.ErrorAttributes.ERROR;
import static com.iodigital.ksp.common.ErrorAttributes.ERRORS;
import static com.iodigital.ksp.common.ErrorAttributes.MESSAGE;
import static com.iodigital.ksp.common.ErrorAttributes.PATH;
import static com.iodigital.ksp.common.ErrorAttributes.STATUS;


@Slf4j
@RestControllerAdvice
public class ExceptionHandler extends DefaultErrorAttributes {

    public static final String ARGUMENT_VALIDATION_FAILED = "Argument validation failed";

    @org.springframework.web.bind.annotation.ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<Map<String, Object>> handle(RecordNotFoundException ex,
                                                            WebRequest request) {
        log.error("Resource not found {}", ex.getMessage());
        return ofType(request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Map<String, Object>> handle(ConstraintViolationException ex,
                                                            WebRequest request) {
        log.error("Constraints violated {}", ex.getMessage());
        List<ConstraintsViolationResponse> validationErrors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintsViolationResponse::new)
                .collect(Collectors.toList());
        return ofType(request, HttpStatus.BAD_REQUEST, ARGUMENT_VALIDATION_FAILED, validationErrors);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Map<String, Object>> handle(DateTimeParseException ex, WebRequest request) {
        log.error("Invalid date-format provided: {}", ex.getMessage());
        List<ConstraintsViolationResponse> validationErrors = List.of(
                ConstraintsViolationResponse.of("talkDate", ex.getMessage())
        );
        return ofType(request, HttpStatus.BAD_REQUEST, ARGUMENT_VALIDATION_FAILED, validationErrors);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Map<String, Object>> handle(MethodArgumentTypeMismatchException ex,
                                                            WebRequest request) {
        log.error("Method arguments are not valid {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MismatchedInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Map<String, Object>> handle(MismatchedInputException ex,
                                                            WebRequest request) {
        log.error("Mismatched inout {}", ex.getMessage());
        return ofType(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Map<String, Object>> handle(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        List<ConstraintsViolationResponse> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ConstraintsViolationResponse::new)
                .collect(Collectors.toList());
        log.error("Constraints are violated", ex);
        return ofType(request, HttpStatus.BAD_REQUEST, "Argument validation failed", validationErrors);
    }

    protected ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status, String message) {
        return ofType(request, status, message, Collections.emptyList());
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request,
                                                       HttpStatus status,
                                                       String message,
                                                       List<ConstraintsViolationResponse> validationErrors) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(STATUS, status.value());
        attributes.put(ERROR, status.getReasonPhrase());
        attributes.put(MESSAGE, message);
        attributes.put(ERRORS, validationErrors);
        attributes.put(PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(attributes, status);
    }
}
