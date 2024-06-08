package com.movies.spacecraft.repository;

import com.movies.spacecraft.entity.Spacecraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {
}
