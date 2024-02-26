package com.fiona.instustionsManagement.Exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ApiError {
    private String message;
    private HttpStatus status;
    private LocalDateTime timeStamp;
}
