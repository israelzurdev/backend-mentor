package com.backendmentor.api.submission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {

    // Mostrar entregas ordenadas de nueva a antigua
    List<Submission> findByUserIdOrderByCreatedAtDesc(UUID userId);

    // Se comprueba si el alumno tiene entrega con estado (Ej: SUCCESS)
    boolean existsByChallengeIdAndUserIdAndStatus(UUID challengeId, UUID userId, SubmissionStatus status);

    // Devuelve las entregas filtradas por estado(solo aprobadas)
    List<Submission> findByChallengeIdAndStatus(UUID challengeId, SubmissionStatus status);

}
