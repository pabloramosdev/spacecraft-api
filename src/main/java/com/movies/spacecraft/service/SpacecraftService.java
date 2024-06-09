package com.movies.spacecraft.service;

import com.movies.spacecraft.repository.entity.Spacecraft;
import com.movies.spacecraft.service.exception.SpacecraftNotFoundException;
import com.movies.spacecraft.service.exception.SpacecraftNotValidException;
import com.movies.spacecraft.service.model.SpacecraftRequest;
import com.movies.spacecraft.service.model.SpacecraftResponse;
import com.movies.spacecraft.repository.SpacecraftRepository;
import com.movies.spacecraft.service.mapper.SpacecraftMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpacecraftService {

    private final SpacecraftMapper spacecraftMapper;
    private final SpacecraftRepository spacecraftRepository;

    public SpacecraftResponse findSpacecraftById(Long spacecraftId) {
        return spacecraftMapper.toSpacecraftResponse(findSpacecraft(spacecraftId));
    }

    @Transactional
    public SpacecraftResponse createSpacecraft(SpacecraftRequest spacecraftRequest) {
        return spacecraftMapper.toSpacecraftResponse(
                spacecraftRepository.save(spacecraftMapper.toSpacecraft(spacecraftRequest)));
    }

    @Transactional
    public SpacecraftResponse updateSpacecraft(Long spacecraftId, SpacecraftRequest spacecraftRequest) {
        Spacecraft spacecraft = findSpacecraft(spacecraftId);
        if (spacecraftRequest != null && spacecraftRequest.getName() != null && spacecraftRequest.getMovie() != null &&
            spacecraftRequest.getPilot() != null) {
            spacecraft.setName(spacecraftRequest.getName());
            spacecraft.setMovie(spacecraftRequest.getMovie());
            spacecraft.setPilot(spacecraftRequest.getPilot());
        } else {
            throw new SpacecraftNotValidException();
        }
        return spacecraftMapper.toSpacecraftResponse(spacecraftRepository.save(spacecraft));
    }

    @Transactional
    public SpacecraftResponse partialUpdateSpacecraft(Long spacecraftId, SpacecraftRequest spacecraftRequest) {
        Spacecraft spacecraft = findSpacecraft(spacecraftId);
        if (spacecraftRequest != null && spacecraftRequest.getName() != null) {
            spacecraft.setName(spacecraftRequest.getName());
        }
        if (spacecraftRequest != null && spacecraftRequest.getMovie() != null) {
            spacecraft.setMovie(spacecraftRequest.getMovie());
        }
        if (spacecraftRequest != null && spacecraftRequest.getPilot() != null) {
            spacecraft.setPilot(spacecraftRequest.getPilot());
        }
        return spacecraftMapper.toSpacecraftResponse(spacecraftRepository.save(spacecraft));
    }

    private Spacecraft findSpacecraft(Long spacecraftId) {
        return spacecraftRepository.findById(spacecraftId)
                .orElseThrow(() -> new SpacecraftNotFoundException(spacecraftId));
    }

}
