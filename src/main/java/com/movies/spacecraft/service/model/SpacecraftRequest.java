package com.movies.spacecraft.service.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpacecraftRequest {
    private final String name;
    private final String movie;
    private final String pilot;
}