package com.backendmentor.api.challenge.service;

import com.backendmentor.api.challenge.domain.Category;
import com.backendmentor.api.challenge.domain.Challenge;
import com.backendmentor.api.challenge.domain.ChallengeRepository;
import com.backendmentor.api.challenge.domain.Difficulty;
import com.backendmentor.api.challenge.dto.ChallengeDetailResponse;
import com.backendmentor.api.challenge.dto.ChallengeSummaryResponse;
import com.backendmentor.api.shared.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlunit.diff.Diff;

import java.util.List;
import java.util.Optional;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // 1. Activa Mockito para este test
class ChallengeServiceImplTest {

    @Mock
    private ChallengeRepository challengeRepository; // 2. La marioneta del repositorio

    @InjectMocks
    private ChallengeServiceImpl challengeService; // 3. El servicio real, al que Mockito le inyecta la marioneta

    @Test
    @DisplayName("getActiveChallenges debe devolver la lista de retos activos convertidos a DTO")
    void shouldReturnActiveChallenges() {
        // --- 1. GIVEN (Preparar el escenario falso) ---
        Challenge mockChallenge = new Challenge(
                "Reto Autenticación",
                "reto-auth",
                Difficulty.JUNIOR,
                Category.SECURITY,
                "# Enunciado del reto"
        );

        // Le enseñamos el guion a la marioneta: "Cuando te llamen a findByActiveTrue, devuelve esta lista"
        when(challengeRepository.findByActiveTrue()).thenReturn(List.of(mockChallenge));

        // --- 2. WHEN (Ejecutamos el método del servicio que queremos probar) ---
        List<ChallengeSummaryResponse> resultado = challengeService.getActiveChallenges();

        // --- 3. THEN (Comprobamos que el servicio hizo bien las cuentas) ---
        assertNotNull(resultado, "La lista no debe ser nula");
        assertEquals(1, resultado.size(), "Debe devolver exactamente 1 reto");
        assertEquals("reto-auth", resultado.get(0).slug());
        assertEquals("Reto Autenticación", resultado.get(0).title());
        assertEquals(Difficulty.JUNIOR, resultado.get(0).difficulty());

        // Verificamos que el servicio realmente consultó a la marioneta una sola vez
        verify(challengeRepository, times(1)).findByActiveTrue();
    }

    @Test
    @DisplayName("getChallengesByDifficulty debe filtrar por el enum solicitado")
    void shouldReturnChallengesByDifficulty() {
        // GIVEN
        Challenge mockChallenge = new Challenge(
                "Reto Autenticación",
                "reto-auth",
                Difficulty.JUNIOR,
                Category.SECURITY,
                "# Enunciado"
        );
        // WHEN
        when(challengeRepository.findByDifficultyAndActiveTrue(Difficulty.JUNIOR))
                .thenReturn(List.of(mockChallenge));

        List<ChallengeSummaryResponse> resultado = challengeService.getChallengesByDifficulty(Difficulty.JUNIOR);

        // THEN
        assertNotNull(resultado, "La lista no debe de ser nula");
        assertEquals(1, resultado.size(), "Debe de devolver exactamente 1");
        assertEquals(Difficulty.JUNIOR, resultado.get(0).difficulty());

        // VERIFY
        verify(challengeRepository).findByDifficultyAndActiveTrue(Difficulty.JUNIOR);
    }

    @Test
    @DisplayName("getChallengeBySlug debe retornar el detalle del reto cuando slug existe")
    void shouldReturnChallengeBySlugWhenExists() {

        // GIVEN
        Challenge mockChallenge = new Challenge(
                "Servidor de Autenticación JWT",
                "crear-servidor-auth",
                Difficulty.JUNIOR,
                Category.SECURITY,
                "# Enunciado del reto"
        );

        when(challengeRepository.findBySlug("crear-servidor-auth"))
                .thenReturn(Optional.of(mockChallenge));

        // WHEN (EJECUTA EL METODO DEL SERVICIO)
        ChallengeDetailResponse resultado = challengeService.getChallengeBySlug("crear-servidor-auth");

        // THEN (ASERCIONES SOBRE EL DTO DEVUELTO)
        assertNotNull(resultado, "El DTO devuelto no debe de ser nulo");
        assertEquals("crear-servidor-auth", resultado.slug());
        assertEquals("Servidor de Autenticación JWT", resultado.title());
        assertEquals(Difficulty.JUNIOR, resultado.difficulty());
        assertEquals(Category.SECURITY, resultado.category());

        // VERIFY (VERIFICAMOS QUE SE RECIBIO LA LLAMADA ESPERADA
        verify(challengeRepository, times(1)).findBySlug("crear-servidor-auth");
    }

    @Test
    @DisplayName("getChallengeBySlug debe lanzar ResourceNotFoundException cuando el slug no existe")
    void shouldThrowExceptionWhenSlugDoesNotExists() {

        // 1. GIVEN: La marioneta devuelve una caja vacía
        when(challengeRepository.findBySlug("slug-inexistente"))
                .thenReturn(Optional.empty());

        // 2. WHEN: Ejecutamos únicamente la acción que esperamos que de el error
        ResourceNotFoundException excepcion = assertThrows(ResourceNotFoundException.class, () -> {
            challengeService.getChallengeBySlug("slug-inexistente");
        });

        // 3. THEN: Una vez capturada la excepción devuelta por assertThrows, comprobamos su mensaje
        assertTrue(excepcion.getMessage().contains("slug-inexistente"));

        // 4. VERIFY: Confirmamos que se consultó al repositorio exactamente con ese slug
        verify(challengeRepository).findBySlug("slug-inexistente");
    }

}
