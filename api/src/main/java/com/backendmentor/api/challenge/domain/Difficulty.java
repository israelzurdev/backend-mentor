package com.backendmentor.api.challenge.domain;

/**
 * Niveles de dificultad para los retos de la plataforma.
 */
public enum Difficulty {
    // Retos de iniciación: conceptos básicos de Java/Spring, endpoints simples
    BEGINNER,

    // Retos básicos: CRUDs estándar, validación de datos, manejo de excepciones
    JUNIOR,

    // Retos intermedios: relaciones complejas en BD, seguridad JWT, cachés
    MID,

    // Retos avanzados: optimización profunda de SQL, Clean Architecture, concurrencia
    SENIOR,

    // Retos de nivel experto: diseño de sistemas distribuidos y alta disponibilidad
    EXPERT
}
