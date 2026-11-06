package com.backendmentor.api.challenge.dto;

import com.backendmentor.api.challenge.domain.Category;
import com.backendmentor.api.challenge.domain.Challenge;
import com.backendmentor.api.challenge.domain.Difficulty;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) para la pantalla de detalle de un reto.
 * Usamos un 'record' porque solo sirve para transportar datos inmutables hacia el frontend.
 * No exponemos la entidad JPA directamente para evitar exponer datos internos o romper la API.
 */
public record ChallengeDetailResponse(
        // 1. El identificador único del reto en la base de datos
        UUID id,

        // 2. El título visible para el usuario (ej: "Servicio de Autenticación JWT")
        String title,

        // 3. La URL amigable para navegar en Angular (ej: "servicio-autenticacion-jwt")
        String slug,

        // 4. El nivel de dificultad (EASY, MEDIUM, HARD) para pintar el badge de color
        Difficulty difficulty,

        // 5. La categoría del reto (ej: BACKEND, DATABASE, SECURITY)
        Category category,

        // 6. El contenido completo del enunciado redactado en Markdown
        String descriptionMd,

        // 7. La fecha y hora en que se publicó el reto (heredada de BaseEntity)
        Instant createdAt
) {

    /**
     * Metodo estático de fábrica (Factory Method).
     * Recibe la entidad 'Challenge' cargada desde PostgreSQL y extrae únicamente
     * los datos necesarios para construir este DTO limpio.
     */
    public static ChallengeDetailResponse fromEntity(Challenge challenge) {
        return new ChallengeDetailResponse(
                challenge.getId(),             // Extrae el UUID
                challenge.getTitle(),          // Extrae el título
                challenge.getSlug(),           // Extrae el slug
                challenge.getDifficulty(),     // Extrae el Enum de dificultad
                challenge.getCategory(),       // Extrae el Enum de categoría
                challenge.getDescriptionMd(),  // Extrae el texto en Markdown
                challenge.getCreatedAt()       // Extrae la fecha de creación
        );
    }
}
