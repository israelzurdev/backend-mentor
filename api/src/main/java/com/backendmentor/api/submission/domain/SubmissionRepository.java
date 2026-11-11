package com.backendmentor.api.submission.domain; // o com.backendmentor.api.submission.repository según tu carpeta

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {

    // Buscar entregas asociadas a un reto concreto
    List<Submission> findByChallengeIdOrderByCreatedAtDesc(UUID challengeId);

    // Buscar entregas de un usuario por su identificador (Clerk ID o GitHub username)
    List<Submission> findByUserIdentifierOrderByCreatedAtDesc(String userIdentifier);
}
