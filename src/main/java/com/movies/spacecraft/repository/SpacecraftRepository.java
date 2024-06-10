package com.movies.spacecraft.repository;

import com.movies.spacecraft.repository.entity.Spacecraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {
    List<Spacecraft> findByNameContaining(String name);
}
