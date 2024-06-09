package com.movies.spacecraft.controller;

import com.movies.spacecraft.service.model.PageResponse;
import com.movies.spacecraft.service.model.SpacecraftRequest;
import com.movies.spacecraft.service.model.SpacecraftResponse;
import com.movies.spacecraft.service.SpacecraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spacecrafts")
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<SpacecraftResponse>> getAllSpacecraft(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(spacecraftService.allSpacecraftPaginated(page, size));
    }

    @GetMapping(path = "/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> getSpacecraftById(@PathVariable Long spacecraftId) {
        return ResponseEntity.ok(spacecraftService.findSpacecraftById(spacecraftId));
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> postSpacecraft(@RequestBody SpacecraftRequest spacecraftRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spacecraftService.createSpacecraft(spacecraftRequest));
    }

    @PutMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> putSpacecraft(@PathVariable Long spacecraftId, @RequestBody SpacecraftRequest spacecraftRequest) {
        return ResponseEntity.ok(spacecraftService.updateSpacecraft(spacecraftId, spacecraftRequest));
    }

    @PatchMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> patchSpacecraft(@PathVariable Long spacecraftId, @RequestBody SpacecraftRequest spacecraftRequest) {
        return ResponseEntity.ok(spacecraftService.partialUpdateSpacecraft(spacecraftId, spacecraftRequest));
    }

    @DeleteMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchSpacecraft(@PathVariable Long spacecraftId) {
        spacecraftService.deleteSpacecraft(spacecraftId);
        return ResponseEntity.ok().build();
    }

}
