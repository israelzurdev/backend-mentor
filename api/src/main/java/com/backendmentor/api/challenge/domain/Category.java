package com.backendmentor.api.challenge.domain;

/**
 * Categorías temáticas de Backend Mentor.
 *
 */
public enum Category {
    // Creación y diseño de APIs (HTTP, verbos, códigos de estado, DTOs, OpenAPI)
    REST,

    // Autenticación, autorización, tokens JWT, OAuth2, RBAC y protección de endpoints
    SECURITY,

    // Modelado relacional, SQL puro, índices, optimización de queries y transacciones
    DATABASE,

    // Rendimiento, cachés (Redis), consultas lentas, pooling de conexiones y concurrencia
    PERFORMANCE,

    // Arquitectura limpia, Hexagonal, DDD y principios SOLID
    ARCHITECTURE,

    // Contenedores (Docker), integración continua (CI/CD) y despliegue
    DEVOPS,

    // Pruebas unitarias y de integración con JUnit 5, Mockito y Testcontainers
    TESTING,

    // Retos complejos: colas de mensajería (RabbitMQ/Kafka), escalabilidad y resiliencia
    SYSTEM_DESIGN
}
