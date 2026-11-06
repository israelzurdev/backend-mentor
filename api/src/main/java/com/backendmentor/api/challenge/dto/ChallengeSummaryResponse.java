package com.backendmentor.api.challenge.dto;

import com.backendmentor.api.challenge.domain.Category;
import com.backendmentor.api.challenge.domain.Challenge;
import com.backendmentor.api.challenge.domain.Difficulty;

import java.util.UUID;

public record ChallengeSummaryResponse(UUID id, String title, String slug, Difficulty difficulty, Category category,
                                       int totalSubmissions) {

    // HELPER: Transforma la entidad de la BD al DTO limpio que va al frontend
    public static ChallengeSummaryResponse fromEntity(Challenge challenge) {
        return new ChallengeSummaryResponse(
                challenge.getId(),
                challenge.getTitle(),
                challenge.getSlug(),
                challenge.getDifficulty(),
                challenge.getCategory(),
                0 // DE MOMENTO
        );
    }
}
