package com.movies.spacecraft.service;

import com.movies.spacecraft.repository.SpacecraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpacecraftService {

    private final SpacecraftRepository spacecraftRepository;

}
