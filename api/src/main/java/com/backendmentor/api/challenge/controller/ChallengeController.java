package com.backendmentor.api.challenge.controller;

import com.backendmentor.api.challenge.domain.Difficulty;
import com.backendmentor.api.challenge.dto.ChallengeDetailResponse;
import com.backendmentor.api.challenge.dto.ChallengeSummaryResponse;
import com.backendmentor.api.challenge.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Challenges", description = "Endpoints para la gestión y consulta de retos de programación")
@RestController
@RequestMapping("/api/v1/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @Operation(summary = "Listar retos activos con filtro opcional por nivel de dificultad")
    @GetMapping
    public ResponseEntity<List<ChallengeSummaryResponse>> getAllChallenges(
            @RequestParam(required = false) Difficulty difficulty
    ) {
        List<ChallengeSummaryResponse> challenges = (difficulty != null)
                ? challengeService.getChallengesByDifficulty(difficulty)
                : challengeService.getActiveChallenges();

        return ResponseEntity.ok(challenges);
    }

    @Operation(summary = "Obtener el detalle completo de un reto mediante su slug único")
    @GetMapping("/{slug}")
    public ResponseEntity<ChallengeDetailResponse> getChallengeBySlug(@PathVariable String slug) {
        ChallengeDetailResponse challenge = challengeService.getChallengeBySlug(slug);
        return ResponseEntity.ok(challenge);
    }
}
