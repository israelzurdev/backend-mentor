package com.backendmentor.api.submission.controller;

import com.backendmentor.api.submission.dto.CreateSubmissionRequest;
import com.backendmentor.api.submission.dto.SubmissionResponse;
import com.backendmentor.api.submission.service.SubmissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Submissions", description = "Endpoints para la gestión y entrega de soluciones a los retos")
@RestController
@RequestMapping("/api/v1/challenges/{slug}/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Operation(summary = "Enviar solución a un reto mediante URL de repositorio de GitHub")
    @PostMapping
    public ResponseEntity<SubmissionResponse> createSubmission(
            @PathVariable String slug,
            @Valid @RequestBody CreateSubmissionRequest request
    ) {
        SubmissionResponse response = submissionService.createSubmission(slug, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Obtener el listado de soluciones enviadas para un reto específico")
    @GetMapping
    public ResponseEntity<List<SubmissionResponse>> getSubmissionsByChallenge(
            @PathVariable String slug
    ) {
        List<SubmissionResponse> submissions = submissionService.getSubmissionsByChallengeSlug(slug);
        return ResponseEntity.ok(submissions);
    }
}