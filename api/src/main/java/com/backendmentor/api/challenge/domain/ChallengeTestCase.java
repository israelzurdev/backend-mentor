package com.backendmentor.api.challenge.domain;

import com.backendmentor.api.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "challenge_test_cases")

public class ChallengeTestCase extends BaseEntity {

    // ATRIBUTOS MAPEADOS
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(name = "execution_order", nullable = false)
    private int executionOrder;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "http_method", nullable = false, length = 10)
    private String httpMethod;

    @Column(name = "endpoint_path", nullable = false, length = 255)
    private String endpointPath;

    @Column(name = "request_body_json", columnDefinition = "TEXT")
    private String requestBodyJson;

    @Column(name = "expected_status", nullable = false)
    private int expectedStatus;

    @Column(name = "expected_json_keys", columnDefinition = "TEXT")
    private String expectedJsonKeys;

    protected ChallengeTestCase() {

    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public int getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(int executionOrder) {
        this.executionOrder = executionOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getEndpointPath() {
        return endpointPath;
    }

    public void setEndpointPath(String endpointPath) {
        this.endpointPath = endpointPath;
    }

    public String getRequestBodyJson() {
        return requestBodyJson;
    }

    public void setRequestBodyJson(String requestBodyJson) {
        this.requestBodyJson = requestBodyJson;
    }

    public int getExpectedStatus() {
        return expectedStatus;
    }

    public void setExpectedStatus(int expectedStatus) {
        this.expectedStatus = expectedStatus;
    }

    public String getExpectedJsonKeys() {
        return expectedJsonKeys;
    }

    public void setExpectedJsonKeys(String expectedJsonKeys) {
        this.expectedJsonKeys = expectedJsonKeys;
    }
}
