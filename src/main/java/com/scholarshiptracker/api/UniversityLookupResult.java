package com.scholarshiptracker.api;

import java.util.List;

/**
 * Represents the result of a university API lookup.
 */
public class UniversityLookupResult {

    private final boolean success;
    private final String message;
    private final List<University> universities;

    public UniversityLookupResult(boolean success, String message, List<University> universities) {
        this.success = success;
        this.message = message;
        this.universities = universities;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<University> getUniversities() {
        return universities;
    }
}
