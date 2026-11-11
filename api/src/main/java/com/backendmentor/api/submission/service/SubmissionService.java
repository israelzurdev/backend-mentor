package com.backendmentor.api.submission.service;

import com.backendmentor.api.challenge.domain.Challenge;
import com.backendmentor.api.challenge.domain.ChallengeRepository;
import com.backendmentor.api.shared.exception.ResourceNotFoundException;
import com.backendmentor.api.submission.domain.Submission;
import com.backendmentor.api.submission.domain.SubmissionRepository;
import com.backendmentor.api.submission.dto.CreateSubmissionRequest;
import com.backendmentor.api.submission.dto.SubmissionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ChallengeRepository challengeRepository;

    public SubmissionService(SubmissionRepository submissionRepository, ChallengeRepository challengeRepository) {
        this.submissionRepository = submissionRepository;
        this.challengeRepository = challengeRepository;
    }

    @Transactional
    public SubmissionResponse createSubmission(String slug, CreateSubmissionRequest request) {
        Challenge challenge = challengeRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún reto con el slug: " + slug));

        Submission submission = new Submission(
                null, // El UUID se genera en @PrePersist si va a null
                challenge,
                request.userIdentifier(),
                request.githubRepoUrl()
        );

        Submission saved = submissionRepository.save(submission);
        return SubmissionResponse.fromEntity(saved);
    }

    public List<SubmissionResponse> getSubmissionsByChallengeSlug(String slug) {
        Challenge challenge = challengeRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró ningún reto con el slug: " + slug));

        return submissionRepository.findByChallengeIdOrderByCreatedAtDesc(challenge.getId())
                .stream()
                .map(SubmissionResponse::fromEntity)
                .toList();
    }
}
