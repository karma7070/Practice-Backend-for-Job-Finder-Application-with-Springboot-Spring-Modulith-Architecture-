package com.FindAJob.demo;

import com.FindAJob.demo.SecurityPackage.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice // watches every controller in this folder
public class GlobalExceptionHandler {



    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFound(UsernameNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex){
      return  ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleResponseStatus(ResponseStatusException ex){
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ex.getReason());
    }




}



