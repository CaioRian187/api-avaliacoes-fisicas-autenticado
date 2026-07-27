package com.CaioRian.AvaliacoesFisicas.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> DataIntegrityViolationExceptionHandler(DataIntegrityViolationException exception){
        Map<String, String> response = new HashMap<String, String>();
        response.put(HttpStatus.BAD_REQUEST.toString(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        Map<String, String> response = new HashMap<String, String>();
        exception.getBindingResult().getFieldErrors().forEach( error -> {
            response.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> responseStatusExceptionHandle(ResponseStatusException exception){
        Map<String, String> response = new HashMap<String, String>();
        response.put(exception.getStatusCode().toString(), exception.getReason());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
