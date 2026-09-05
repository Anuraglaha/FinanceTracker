package com.anurag.financetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.anurag.financetracker.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceAccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<?> handleResourceAccessDenied(
            ResourceAccessDeniedException ex) {

        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );
    }
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntimeException(RuntimeException ex) {

        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );

    }

}