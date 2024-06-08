package com.movies.spacecraft.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpacecraftResponse {
    private final String name;
    private final String movie;
    private final String pilot;
}
