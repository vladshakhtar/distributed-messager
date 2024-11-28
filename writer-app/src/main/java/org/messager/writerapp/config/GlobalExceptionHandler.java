package org.messager.writerapp.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserIdException.class)
    public ResponseEntity<String> handleIllegalStatusException(UserIdException ex) {
        return ResponseEntity.status(HttpStatusCode.valueOf(HttpStatus.FORBIDDEN.value())).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value())).body(ex.getMessage());
    }
}
