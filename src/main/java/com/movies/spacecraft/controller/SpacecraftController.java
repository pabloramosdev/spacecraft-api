package com.movies.spacecraft.controller;

import com.movies.spacecraft.service.model.SpacecraftRequest;
import com.movies.spacecraft.service.model.SpacecraftResponse;
import com.movies.spacecraft.service.SpacecraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spacecrafts")
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    @GetMapping(path = "/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> getSpacecraftById(@PathVariable Long spacecraftId) {
        return ResponseEntity.ok(spacecraftService.findSpacecraftById(spacecraftId));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> postSpacecraft(@RequestBody SpacecraftRequest spacecraftRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spacecraftService.createSpacecraft(spacecraftRequest));
    }

}
