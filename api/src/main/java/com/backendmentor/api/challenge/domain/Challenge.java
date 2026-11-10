package com.backendmentor.api.challenge.domain;


import com.backendmentor.api.shared.domain.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "challenges")
public class Challenge extends BaseEntity {

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeTestCase> testCases = new ArrayList<>();

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "description_md", columnDefinition = "TEXT", nullable = false)
    private String descriptionMd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;

    @Column(nullable = false)
    private boolean active = true;

    protected Challenge() {

    }

    // Constructor para que nazca con todos sus datos
    public Challenge(String title, String slug, Difficulty difficulty, Category category, String descriptionMd) {
        this.title = title;
        this.slug = slug;
        this.difficulty = difficulty;
        this.category = category;
        this.descriptionMd = descriptionMd;
        this.active = true; // Por defecto nace activo
    }

    public void addTestCase(ChallengeTestCase testCase) {
        testCases.add(testCase);
        testCase.setChallenge(this);
    }

    public void removeTestCase(ChallengeTestCase testCase) {
        testCases.remove(testCase);
        testCase.setChallenge(null);
    }

    public List<ChallengeTestCase> getTestCases() {
        return Collections.unmodifiableList(testCases);
    }


    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptionMd() {
        return descriptionMd;
    }

    public void setDescriptionMd(String descriptionMd) {
        this.descriptionMd = descriptionMd;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
