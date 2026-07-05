package com.scholarshiptracker.model;

public final class EligibilityResult {

    private final boolean eligible;
    private final ApplicationStatus status;
    private final String reasoning;

    public EligibilityResult(boolean eligible, ApplicationStatus status, String reasoning) {
        this.eligible = eligible;
        this.status = status;
        this.reasoning = reasoning;
    }

    public boolean isEligible() {
        return eligible;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getReasoning() {
        return reasoning;
    }
}
