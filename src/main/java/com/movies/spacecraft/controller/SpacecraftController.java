package com.movies.spacecraft.controller;

import com.movies.spacecraft.service.model.PageResponse;
import com.movies.spacecraft.service.model.SpacecraftRequest;
import com.movies.spacecraft.service.model.SpacecraftResponse;
import com.movies.spacecraft.service.SpacecraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spacecrafts")
@SecurityRequirement(name = "basicAuth")
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

    @Operation(summary = "Get gaginated spacecraft list")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<SpacecraftResponse>> getAllSpacecraft(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(spacecraftService.allSpacecraftPaginated(page, size));
    }

    @Operation(summary = "get filtered spacecraft list")
    @GetMapping(path = "/name", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpacecraftResponse>> getContainsNameSpacecraft(@RequestParam String filter) {
        return ResponseEntity.ok(spacecraftService.containsName(filter));
    }

    @Operation(summary = "get spacecraft by id")
    @GetMapping(path = "/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> getSpacecraftById(@PathVariable Long spacecraftId) {
        return ResponseEntity.ok(spacecraftService.findSpacecraftById(spacecraftId));
    }

    @Operation(summary = "insert spacecraft")
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> postSpacecraft(@RequestBody SpacecraftRequest spacecraftRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(spacecraftService.createSpacecraft(spacecraftRequest));
    }

    @Operation(summary = "update spacecraft")
    @PutMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> putSpacecraft(@PathVariable Long spacecraftId, @RequestBody SpacecraftRequest spacecraftRequest) {
        return ResponseEntity.ok(spacecraftService.updateSpacecraft(spacecraftId, spacecraftRequest));
    }

    @Operation(summary = "partial update spacecraft")
    @PatchMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpacecraftResponse> patchSpacecraft(@PathVariable Long spacecraftId, @RequestBody SpacecraftRequest spacecraftRequest) {
        return ResponseEntity.ok(spacecraftService.partialUpdateSpacecraft(spacecraftId, spacecraftRequest));
    }

    @Operation(summary = "remove spacecraft")
    @DeleteMapping(path="/{spacecraftId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> patchSpacecraft(@PathVariable Long spacecraftId) {
        spacecraftService.deleteSpacecraft(spacecraftId);
        return ResponseEntity.ok().build();
    }

}
