package com.movies.spacecraft.service;

import com.movies.spacecraft.repository.entity.Spacecraft;
import com.movies.spacecraft.service.exception.EmptyPageResponseException;
import com.movies.spacecraft.service.exception.SpacecraftNotFoundException;
import com.movies.spacecraft.service.exception.SpacecraftNotValidException;
import com.movies.spacecraft.service.model.PageResponse;
import com.movies.spacecraft.service.model.SpacecraftRequest;
import com.movies.spacecraft.service.model.SpacecraftResponse;
import com.movies.spacecraft.repository.SpacecraftRepository;
import com.movies.spacecraft.service.mapper.SpacecraftMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpacecraftService {

    private final SpacecraftMapper spacecraftMapper;
    private final SpacecraftRepository spacecraftRepository;

    @Cacheable( cacheNames = "spacecrafts")
    public SpacecraftResponse findSpacecraftById(Long spacecraftId) {
        return spacecraftMapper.toSpacecraftResponse(findSpacecraft(spacecraftId));
    }

    @Cacheable( cacheNames = "spacecrafts")
    public PageResponse<SpacecraftResponse> allSpacecraftPaginated(Integer page, Integer size) {
        Page<Spacecraft> pageSpacecraft = spacecraftRepository.findAll(PageRequest.of(page, size));
        Page<SpacecraftResponse> spacecraftResponsePage = pageSpacecraft.map(spacecraftMapper::toSpacecraftResponse);
        if (spacecraftResponsePage.isEmpty()) {
            throw new EmptyPageResponseException(page);
        }
        PageResponse<SpacecraftResponse> pageResponse = new PageResponse<>(spacecraftResponsePage.getNumber(), spacecraftResponsePage.getSize(),
                spacecraftResponsePage.getTotalPages(), spacecraftResponsePage.getTotalElements());
        pageResponse.addContent(spacecraftResponsePage.getContent());
        return pageResponse;
    }

    @Cacheable( cacheNames = "spacecrafts")
    public List<SpacecraftResponse> containsName(String name) {
        return spacecraftRepository.findByNameContaining(name).stream()
                .map(spacecraftMapper::toSpacecraftResponse)
                .collect(Collectors.toList());
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

    @Transactional
    public void deleteSpacecraft(Long spacecraftId) {
        Spacecraft spacecraft = findSpacecraft(spacecraftId);
        spacecraftRepository.delete(spacecraft);
    }

    public Spacecraft findSpacecraft(Long spacecraftId) {
        return spacecraftRepository.findById(spacecraftId)
                .orElseThrow(() -> new SpacecraftNotFoundException(spacecraftId));
    }

}
