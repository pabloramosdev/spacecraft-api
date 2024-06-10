package com.movies.spacecraft.service.exception;

public class SpacecraftNotValidException extends RuntimeException {

    private static final String MESSAGE = "Spacecraft is not valid";

    public SpacecraftNotValidException() {
        super(MESSAGE);
    }
}
