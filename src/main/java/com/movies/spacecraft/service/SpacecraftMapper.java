package com.movies.spacecraft.service;

import com.movies.spacecraft.entity.Spacecraft;
import com.movies.spacecraft.model.SpacecraftResponse;
import org.springframework.stereotype.Component;

@Component
public class SpacecraftMapper {

    public SpacecraftResponse toSpacecraftResponse(Spacecraft spacecraft) {
        return SpacecraftResponse.builder()
                .name(spacecraft.getName())
                .movie(spacecraft.getMovie())
                .pilot(spacecraft.getPilot())
                .build();
    }

}
