package com.movies.spacecraft.service;

import com.movies.spacecraft.model.SpacecraftRequest;
import com.movies.spacecraft.model.SpacecraftResponse;
import com.movies.spacecraft.repository.SpacecraftRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpacecraftService {

    private final SpacecraftMapper spacecraftMapper;
    private final SpacecraftRepository spacecraftRepository;

    public SpacecraftResponse findSpacecraftById(Long spacecraftId) {
        return spacecraftRepository.findById(spacecraftId)
                .map(spacecraftMapper::toSpacecraftResponse)
                .orElseGet(() -> SpacecraftResponse.builder().build());
    }

    @Transactional
    public SpacecraftResponse createSpacecraft(SpacecraftRequest spacecraftRequest) {
        return spacecraftMapper.toSpacecraftResponse(
                spacecraftRepository.save(spacecraftMapper.toSpacecraft(spacecraftRequest)));
    }
}
