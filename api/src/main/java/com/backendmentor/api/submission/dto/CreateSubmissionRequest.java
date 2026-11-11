package com.backendmentor.api.submission.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Payload para registrar la entrega de un reto técnico")
public record CreateSubmissionRequest(

        @Schema(
                description = "URL pública del repositorio en GitHub con la solución",
                example = "https://github.com/usuario/reto-autenticacion-jwt"
        )
        @NotBlank(message = "La URL del repositorio de GitHub es obligatoria")
        @Pattern(
                regexp = "^https://github\\.com/[A-Za-z0-9_.-]+/[A-Za-z0-9_.-]+/?$",
                message = "Debe ser una URL válida de repositorio de GitHub (ej: https://github.com/usuario/mi-repo)"
        )
        String githubRepoUrl,

        @Schema(
                description = "Identificador del usuario (ID de Clerk, usuario de GitHub o 'demo-user')",
                example = "user_2test123"
        )
        @NotBlank(message = "El identificador de usuario es obligatorio")
        String userIdentifier

) {}
