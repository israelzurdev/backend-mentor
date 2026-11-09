package com.backendmentor.api.challenge.controller;

import com.backendmentor.api.challenge.domain.Category;
import com.backendmentor.api.challenge.domain.Difficulty;
import com.backendmentor.api.challenge.dto.ChallengeDetailResponse;
import com.backendmentor.api.challenge.dto.ChallengeSummaryResponse;
import com.backendmentor.api.challenge.service.ChallengeService;
import com.backendmentor.api.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChallengeController.class) // Levanta solo el controlador y el GlobalExceptionHandler
class ChallengeControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula las llamadas HTTP

    @MockBean
    private ChallengeService challengeService; // Mockito: finge el comportamiento del servicio

    @Test
    @DisplayName("GET /api/v1/challenges - Debe retornar 200 OK y la lista de retos")
    void shouldReturnAllChallenges() throws Exception {
        // GIVEN: Preparamos datos simulados con Mockito
        ChallengeSummaryResponse mockChallenge = new ChallengeSummaryResponse(
                UUID.randomUUID(),
                "Servidor JWT",
                "servidor-jwt",
                Difficulty.JUNIOR,
                Category.SECURITY,
                0
        );
        when(challengeService.getActiveChallenges()).thenReturn(List.of(mockChallenge));

        // WHEN & THEN: Simulamos la petición HTTP y validamos el JSON con MockMvc
        mockMvc.perform(get("/api/v1/challenges")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Servidor JWT"))
                .andExpect(jsonPath("$[0].slug").value("servidor-jwt"))
                .andExpect(jsonPath("$[0].difficulty").value("JUNIOR"));
    }

    @Test
    @DisplayName("GET /api/v1/challenges/{slug} - Debe retornar 404 NOT FOUND cuando no existe")
    void shouldReturn404WhenChallengeNotFound() throws Exception {
        // GIVEN: El servicio lanza tu excepción personalizada
        when(challengeService.getChallengeBySlug("slug-falso"))
                .thenThrow(new ResourceNotFoundException("No se encontró ningún reto con el slug: slug-falso"));

        // WHEN & THEN: Validamos que GlobalExceptionHandler devuelva tu ApiErrorResponse
        mockMvc.perform(get("/api/v1/challenges/slug-falso")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("No se encontró ningún reto con el slug: slug-falso"));
    }
}
