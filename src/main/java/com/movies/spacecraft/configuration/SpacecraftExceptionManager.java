package com.movies.spacecraft.configuration;

import com.movies.spacecraft.service.exception.EmptyPageResponseException;
import com.movies.spacecraft.service.exception.SpacecraftNotFoundException;
import com.movies.spacecraft.service.exception.SpacecraftNotValidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SpacecraftExceptionManager extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SpacecraftNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(SpacecraftNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({EmptyPageResponseException.class})
    public ResponseEntity<Object> handlerNoContent(EmptyPageResponseException ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NO_CONTENT, request);
    }

    @ExceptionHandler({SpacecraftNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        ProblemDetail body = createProblemDetail(ex, HttpStatus.BAD_REQUEST, ex.getMessage(), null, null, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
