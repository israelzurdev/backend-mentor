package com.backendmentor.api.submission.domain;


import com.backendmentor.api.challenge.domain.ChallengeTestCase;
import com.backendmentor.api.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "submission_test_results")

public class SubmissionTestResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_case_id", nullable = false)
    private ChallengeTestCase testCase;

    @Column(nullable = false)
    private boolean passed;

    @Column(name = "actual_status")
    private Integer actualStatus;

    @Column(name = "response_time_ms")
    private Integer responseTimeMs;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    protected SubmissionTestResult() {

    }

    // Getters y Setters
    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public ChallengeTestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(ChallengeTestCase testCase) {
        this.testCase = testCase;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public Integer getActualStatus() {
        return actualStatus;
    }

    public void setActualStatus(Integer actualStatus) {
        this.actualStatus = actualStatus;
    }

    public Integer getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Integer responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
