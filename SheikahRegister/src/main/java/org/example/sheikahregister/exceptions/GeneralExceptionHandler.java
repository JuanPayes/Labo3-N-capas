package org.example.sheikahregister.exceptions;

import org.example.sheikahregister.domain.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException e){
        return builderErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    public ResponseEntity<ApiErrorResponse> builderErrorResponse(HttpStatus status, Object message) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();

        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.builder()
                        .status(status.value())
                        .message(message)
                        .time(LocalDate.now())
                        .url(uri)
                        .build()
                );
    }
}
