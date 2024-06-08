package com.movies.spacecraft.controller;

import com.movies.spacecraft.service.SpacecraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spacecrafts")
public class SpacecraftController {

    private final SpacecraftService spacecraftService;

}
