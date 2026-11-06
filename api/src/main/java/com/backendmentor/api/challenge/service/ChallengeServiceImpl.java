package com.backendmentor.api.challenge.service;

import com.backendmentor.api.challenge.domain.Challenge;
import com.backendmentor.api.challenge.domain.ChallengeRepository;
import com.backendmentor.api.challenge.domain.Difficulty;
import com.backendmentor.api.challenge.dto.ChallengeDetailResponse;
import com.backendmentor.api.challenge.dto.ChallengeSummaryResponse;
import com.backendmentor.api.shared.domain.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public List<ChallengeSummaryResponse> getActiveChallenges() {
        return challengeRepository.findByActiveTrue()
                .stream()                                     // 1. Abrimos una cinta transportadora
                .map(ChallengeSummaryResponse::fromEntity)    // 2. Transformamos cada Challenge en su DTO
                .toList();                                    // 3. Lo empaquetamos de vuelta en una List<>
    }

    @Override
    public List<ChallengeSummaryResponse> getChallengesByDifficulty(Difficulty difficulty) {
        return challengeRepository.findByDifficultyAndActiveTrue(difficulty)
                .stream()                                     // 1. Abrimos una cinta transportadora
                .map(ChallengeSummaryResponse::fromEntity)    // 2. Transformamos cada Challenge en su DTO
                .toList();                                    // 3. Lo empaquetamos de vuelta en una List<>
    }

    @Override
    public ChallengeDetailResponse getChallengeBySlug(String slug) {
        Challenge challenge = challengeRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún reto con el slug: " + slug));

        return ChallengeDetailResponse.fromEntity(challenge);
    }
}
