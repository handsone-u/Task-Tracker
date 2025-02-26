package io.handsone.tasktracker.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UnAuthorizedException.class)
  public ResponseEntity<Http500Response> handleUnauthorizedException(UnAuthorizedException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(new Http500Response(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
  }

  @Data
  @AllArgsConstructor
  public static class Http500Response {

    int statusCode;
    String message;
  }
}
