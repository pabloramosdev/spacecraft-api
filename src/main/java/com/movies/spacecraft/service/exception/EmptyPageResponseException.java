package com.movies.spacecraft.service.exception;

public class EmptyPageResponseException extends RuntimeException {

    private static final String MESSAGE = "No result found for page %d";

    public EmptyPageResponseException(Integer page) {
        super(String.format(MESSAGE, page));
    }
}
