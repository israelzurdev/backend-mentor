package com.backendmentor.api.challenge.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChallengeRepository extends JpaRepository<Challenge, UUID> {

    // Para cuando alguien entra en la web, filtramos por la URL limpia
    Optional<Challenge> findBySlug(String slug);

    // Listar retos activos
    // List porque son varios. ActiveTrue para no usar parametros
    List<Challenge> findByActiveTrue();

    // Filtrar por dificultad. Ejemplo: Retos EASY activos.
    List<Challenge> findByDifficultyAndActiveTrue(Difficulty difficulty);

}
