package com.scholarshiptracker.model;

import java.time.LocalDateTime;

/**
 * Represents a submitted scholarship application.
 */
public class ScholarshipApplication {

    private final String applicationId;
    private final Student student;
    private final Scholarship scholarship;
    private final LocalDateTime submittedAt;
    private ApplicationStatus status;
    private EligibilityResult eligibilityResult;

    public ScholarshipApplication(String applicationId, Student student, Scholarship scholarship) {
        this.applicationId = applicationId;
        this.student = student;
        this.scholarship = scholarship;
        this.status = ApplicationStatus.PENDING_REVIEW;
        this.submittedAt = LocalDateTime.now();
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Student getStudent() {
        return student;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public EligibilityResult getEligibilityResult() {
        return eligibilityResult;
    }

    public void setEligibilityResult(EligibilityResult eligibilityResult) {
        this.eligibilityResult = eligibilityResult;
        this.status = eligibilityResult.getStatus();
    }

    public void updateStatus(ApplicationStatus status) {
        this.status = status;
    }
}
