package com.backendmentor.api.submission.dto;

import com.backendmentor.api.submission.domain.Submission;
import com.backendmentor.api.submission.domain.SubmissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Respuesta con el estado y los datos de la entrega realizada")
public record SubmissionResponse(

        @Schema(description = "Identificador único de la entrega", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        UUID id,

        @Schema(description = "Identificador del reto asociado", example = "dfb3c92b-3874-40ae-a2df-9eb9f53b053f")
        UUID challengeId,

        @Schema(description = "Identificador del usuario", example = "user_2test123")
        String userIdentifier,

        @Schema(description = "URL del repositorio con la solución", example = "https://github.com/usuario/mi-repo")
        String githubRepoUrl,

        @Schema(description = "Estado de evaluación de la entrega", example = "PENDING")
        SubmissionStatus status,

        @Schema(description = "Feedback o notas de la revisión técnica")
        String reviewNotes,

        @Schema(description = "Fecha de registro de la entrega")
        Instant createdAt
) {
    public static SubmissionResponse fromEntity(Submission submission) {
        return new SubmissionResponse(
                submission.getId(),
                submission.getChallenge().getId(),
                submission.getUserIdentifier(),
                submission.getGithubRepoUrl(),
                submission.getStatus(),
                submission.getReviewNotes(),
                submission.getCreatedAt()
        );
    }
}