package com.backendmentor.api.challenge.service;

import com.backendmentor.api.challenge.domain.Difficulty;
import com.backendmentor.api.challenge.dto.ChallengeDetailResponse;
import com.backendmentor.api.challenge.dto.ChallengeSummaryResponse;

import java.util.List;

public interface ChallengeService {

    // Catalogo principal: Devuelve lista de retos activos en formato tarjeta
    List<ChallengeSummaryResponse> getActiveChallenges();

    // Filtro de catalogo:Devuelve los retos activos que coincidan con la dificultad elegida
    List<ChallengeSummaryResponse> getChallengesByDifficulty(Difficulty difficulty);

    // Vista detalle: Buscamos reto por URL para enunciado completo
    // Si no existe, lanzara ResourceNotFoundException
    ChallengeDetailResponse getChallengeBySlug(String slug);

}
