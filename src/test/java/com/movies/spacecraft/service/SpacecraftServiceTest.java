package com.movies.spacecraft.service;

import com.movies.spacecraft.repository.entity.Spacecraft;
import com.movies.spacecraft.service.model.SpacecraftRequest;
import com.movies.spacecraft.service.model.SpacecraftResponse;
import com.movies.spacecraft.repository.SpacecraftRepository;
import com.movies.spacecraft.service.mapper.SpacecraftMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpacecraftServiceTest {

    @Spy private SpacecraftMapper spacecraftMapper;
    @Mock private SpacecraftRepository spacecraftRepository;
    @InjectMocks private SpacecraftService spacecraftService;

    @Test
    @DisplayName("when look for an existing spacecraft then return this")
    void findSpacecraftByIdThenReturnOk() {
        when(spacecraftRepository.findById(anyLong())).thenReturn(Optional.of(
                Spacecraft.builder().id(1L).name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build()));
        SpacecraftResponse spacecraft = spacecraftService.findSpacecraftById(1L);
        assertNotNull(spacecraft);
        assertEquals("x-wing", spacecraft.getName());
        assertEquals("Star Wars", spacecraft.getMovie());
        assertEquals("Luke Skywalker", spacecraft.getPilot());
        verify(spacecraftRepository).findById(anyLong());
        verify(spacecraftMapper).toSpacecraftResponse(any());
    }

    @Test
    @DisplayName("when look for a non existing spacecraft then return an empty response")
    void findSpacecraftByIdThenReturnEmpty() {
        when(spacecraftRepository.findById(anyLong())).thenReturn(Optional.empty());
        spacecraftService.findSpacecraftById(11L);
        verify(spacecraftRepository).findById(anyLong());
    }

    @Test
    @DisplayName("when create a spacecraft then return this")
    void createSpacecraftThenReturnCreated() {
        when(spacecraftRepository.save(any())).thenReturn(
                Spacecraft.builder().id(1L).name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build());
        SpacecraftResponse spacecraft = spacecraftService.createSpacecraft(
                SpacecraftRequest.builder().name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build());
        assertNotNull(spacecraft);
        assertEquals(1L, spacecraft.getId());
        assertEquals("x-wing", spacecraft.getName());
        assertEquals("Star Wars", spacecraft.getMovie());
        assertEquals("Luke Skywalker", spacecraft.getPilot());
        verify(spacecraftRepository).save(any());
        verify(spacecraftMapper).toSpacecraft(any());
        verify(spacecraftMapper).toSpacecraftResponse(any());
    }

}