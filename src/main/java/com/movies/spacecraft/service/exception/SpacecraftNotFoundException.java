package com.movies.spacecraft.service.exception;

public class SpacecraftNotFoundException extends RuntimeException {

    private static final String NOT_FOUND_MESSAGE = "Spacecraft with id %d not found";

    public SpacecraftNotFoundException(Long id) {
        super(String.format(NOT_FOUND_MESSAGE, id));
    }

}
