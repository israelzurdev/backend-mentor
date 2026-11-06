package com.backendmentor.api.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CodeReviewRepository extends JpaRepository<CodeReview, UUID> {

    // Filtramos por las entregas ordenadas por votos
    List<CodeReview> findBySubmissionIdOrderByUpvotesCountDesc(UUID submissionId);

    // Historial de revies hechas por un usuario
    List<CodeReview> findByAuthorIdOrderByCreatedAtDesc(UUID authorId);

    // Evitamos reviews duplicadas del mismo usuario en la misma entrega
    boolean existsBySubmissionIdAndAuthorId(UUID submissionId, UUID authorId);
}
