package com.movies.spacecraft.service;

import com.movies.spacecraft.repository.entity.Spacecraft;
import com.movies.spacecraft.service.exception.SpacecraftNotFoundException;
import com.movies.spacecraft.service.exception.SpacecraftNotValidException;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
        SpacecraftNotFoundException spacecraftNotFoundException = assertThrows(SpacecraftNotFoundException.class,
                () -> spacecraftService.findSpacecraftById(11L));
        assertEquals("Spacecraft with id 11 not found", spacecraftNotFoundException.getMessage());
        verify(spacecraftRepository).findById(anyLong());
    }

    @Test
    @DisplayName("when create a spacecraft then return it")
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

    @Test
    @DisplayName("when update a spacecraft then return updated one")
    void updateSpacecraftThenReturnUpdated() {
        when(spacecraftRepository.findById(anyLong())).thenReturn(Optional.of(
                Spacecraft.builder().id(1L).name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build()));
        when(spacecraftRepository.save(any())).thenReturn(
                Spacecraft.builder().id(1L).name("x-wing-updated").movie("Star Wars updated").pilot("Luke Skywalker Updated").build());
        SpacecraftResponse spacecraft = spacecraftService.updateSpacecraft(1L,
                SpacecraftRequest.builder().name("x-wing-updated").movie("Star Wars updated").pilot("Luke Skywalker Updated").build());
        assertNotNull(spacecraft);
        assertEquals(1L, spacecraft.getId());
        assertEquals("x-wing-updated", spacecraft.getName());
        assertEquals("Star Wars updated", spacecraft.getMovie());
        assertEquals("Luke Skywalker Updated", spacecraft.getPilot());
        verify(spacecraftRepository).findById(anyLong());
        verify(spacecraftRepository).save(any());
        verify(spacecraftMapper).toSpacecraftResponse(any());
    }

    @Test
    @DisplayName("when update a spacecraft partially then throw spacecraft not found exception")
    void updateSpacecraftPartiallyThenThrowSpacecraftNotFoundException() {
        when(spacecraftRepository.findById(anyLong())).thenReturn(Optional.of(
                Spacecraft.builder().id(1L).name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build()));
        SpacecraftNotValidException notValidException = assertThrows(SpacecraftNotValidException.class, () -> spacecraftService.updateSpacecraft(1L,
                SpacecraftRequest.builder().name("x-wing-updated").pilot("Luke Skywalker Updated").build()));
        assertEquals("Spacecraft is not valid", notValidException.getMessage());
        verify(spacecraftRepository).findById(anyLong());
    }

    @Test
    @DisplayName("when update partial spacecraft then return partially updated one")
    void partialUpdateSpacecraftThenReturnPartiallyUpdated() {
        when(spacecraftRepository.findById(anyLong())).thenReturn(Optional.of(
                Spacecraft.builder().id(1L).name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build()));
        when(spacecraftRepository.save(any())).thenReturn(
                Spacecraft.builder().id(1L).name("x-wing-updated").movie("Star Wars updated").pilot("Luke Skywalker").build());
        SpacecraftResponse spacecraft = spacecraftService.partialUpdateSpacecraft(1L,
                SpacecraftRequest.builder().name("x-wing-updated").movie("Star Wars updated").pilot("Luke Skywalker").build());
        assertNotNull(spacecraft);
        assertEquals(1L, spacecraft.getId());
        assertEquals("x-wing-updated", spacecraft.getName());
        assertEquals("Star Wars updated", spacecraft.getMovie());
        assertEquals("Luke Skywalker", spacecraft.getPilot());
        verify(spacecraftRepository).findById(anyLong());
        verify(spacecraftRepository).save(any());
        verify(spacecraftMapper).toSpacecraftResponse(any());
    }

    @Test
    @DisplayName("when update partial spacecraft then return partially updated one")
    void deleteSpacecraftThenDoNothing() {
        when(spacecraftRepository.findById(anyLong())).thenReturn(Optional.of(
                Spacecraft.builder().id(1L).name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build()));
        doNothing().when(spacecraftRepository).delete(any());
        spacecraftService.deleteSpacecraft(1L);
        verify(spacecraftRepository).findById(anyLong());
        verify(spacecraftRepository).delete(any());
    }

    @Test
    @DisplayName("when filter by name then return all spacecrafts that match the filter")
    void containsNameTestOk() {
        when(spacecraftRepository.findByNameContaining(anyString())).thenReturn(Collections.singletonList(
                Spacecraft.builder().id(1L).name("x-wing").movie("Star Wars").pilot("Luke Skywalker").build()));
        List<SpacecraftResponse> spacecrafts = spacecraftService.containsName("wing");
        assertNotNull(spacecrafts);
        assertFalse(spacecrafts.isEmpty());
        SpacecraftResponse spacecraft = spacecrafts.getFirst();
        assertEquals("x-wing", spacecraft.getName());
        assertEquals("Star Wars", spacecraft.getMovie());
        assertEquals("Luke Skywalker", spacecraft.getPilot());
        verify(spacecraftRepository).findByNameContaining(anyString());
        verify(spacecraftMapper).toSpacecraftResponse(any());
    }

}