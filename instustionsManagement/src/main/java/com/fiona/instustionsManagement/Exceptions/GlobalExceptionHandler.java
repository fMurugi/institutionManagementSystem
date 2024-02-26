package com.fiona.instustionsManagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateInstitutionException.class)
    public ResponseEntity<Object> handleDuplicate(DuplicateInstitutionException ex, WebRequest request){
        return new ResponseEntity<>(new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now()),HttpStatus.BAD_REQUEST );
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleInstitutionNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateCourseException.class)
    public ResponseEntity<String> handleDuplicateCourseException(DuplicateCourseException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCourseChangeException.class)
    public ResponseEntity<String > handleInvalidCourseChangeException(InvalidCourseChangeException ex){
        return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body(ex.getMessage());
    }
}
