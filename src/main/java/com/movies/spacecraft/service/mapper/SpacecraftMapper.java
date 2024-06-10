package com.movies.spacecraft.service.mapper;

import com.movies.spacecraft.repository.entity.Spacecraft;
import com.movies.spacecraft.service.model.SpacecraftRequest;
import com.movies.spacecraft.service.model.SpacecraftResponse;
import org.springframework.stereotype.Component;

@Component
public class SpacecraftMapper {

    public SpacecraftResponse toSpacecraftResponse(Spacecraft spacecraft) {
        return SpacecraftResponse.builder()
                .id(spacecraft.getId())
                .name(spacecraft.getName())
                .movie(spacecraft.getMovie())
                .pilot(spacecraft.getPilot())
                .build();
    }

    public Spacecraft toSpacecraft(SpacecraftRequest spacecraftRequest) {
        return Spacecraft.builder()
                .name(spacecraftRequest.getName())
                .movie(spacecraftRequest.getMovie())
                .pilot(spacecraftRequest.getPilot())
                .build();
    }
}
