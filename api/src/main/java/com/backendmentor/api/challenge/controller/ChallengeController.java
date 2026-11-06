package com.backendmentor.api.challenge.controller;


import com.backendmentor.api.challenge.domain.Difficulty;
import com.backendmentor.api.challenge.dto.ChallengeDetailResponse;
import com.backendmentor.api.challenge.dto.ChallengeSummaryResponse;
import com.backendmentor.api.challenge.service.ChallengeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    // Listar retos (con filtro opcional por dificultad en la URL: ?difficulty=EASY)
    @GetMapping
    public ResponseEntity<List<ChallengeSummaryResponse>> getAllChallenges(
            @RequestParam(required = false) Difficulty difficulty
    ) {
        List<ChallengeSummaryResponse> challenges = (difficulty != null)
                ? challengeService.getChallengesByDifficulty(difficulty)
                : challengeService.getActiveChallenges();

        return ResponseEntity.ok(challenges); // Devuelve HTTP 200 OK con la lista en formato JSON
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ChallengeDetailResponse> getChallengeBySlug(@PathVariable String slug) {
        ChallengeDetailResponse challenge = challengeService.getChallengeBySlug(slug);
        return ResponseEntity.ok(challenge); // Devuelve HTTP 200 OK con el detalle del reto
    }

}
