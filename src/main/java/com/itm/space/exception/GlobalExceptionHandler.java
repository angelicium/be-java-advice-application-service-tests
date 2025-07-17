package com.itm.space.exception;

import com.itm.space.model.response.HttpErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<HttpErrorResponse> handleTypeMismatchException(Exception ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);

        return ResponseEntity
                .badRequest()
                .body(new HttpErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<HttpErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        log.error("Exception occurred: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(CONFLICT)
                .body(new HttpErrorResponse(
                        CONFLICT.value(),
                        CONFLICT.getReasonPhrase(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("Invalid request parameter: {}", ex.getMessage(), ex);
        return ResponseEntity
                .badRequest()
                .body(new HttpErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        ex.getMessage()
                ));
    }

    @ExceptionHandler({AuthorizationDeniedException.class,
            AccessDeniedException.class})
    public ResponseEntity<HttpErrorResponse> handleAccessDeniedException(Exception ex) {
        log.error("Access denied: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new HttpErrorResponse(
                        HttpStatus.FORBIDDEN.value(),
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleUnauthorizedException(AuthenticationCredentialsNotFoundException ex) {
        log.error("Authentication failed: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new HttpErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                        ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleNotFoundException(Exception ex) {
        log.error("File not found: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new HttpErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        ex.getMessage()));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleFileNotFoundException(FileNotFoundException ex) {
        Map<String, String> body = Map.of("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
