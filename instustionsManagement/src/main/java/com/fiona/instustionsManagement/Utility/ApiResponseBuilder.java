package com.fiona.instustionsManagement.Utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ApiResponseBuilder {

    public static <T>ResponseEntity<ApiResponse> buildResponseEntity(HttpStatus status,T body,String path){
        ApiResponse<T> response = ApiResponse.<T>builder()
                .body(body)
//                .timeStamp(LocalDateTime.now())
                .path(path)
                .build();
        return  new ResponseEntity<>(response,status);
    }
}
