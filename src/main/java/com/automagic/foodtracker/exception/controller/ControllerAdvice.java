package com.automagic.foodtracker.exception.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("path", request.getDescription(false).replace("uri=", ""));

        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> {
                    Map<String, String> error = new HashMap<>();
                    error.put("field", e.getField());
                    error.put("message", e.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toList());

        body.put("errors", errors);

        return body;
    }
}
