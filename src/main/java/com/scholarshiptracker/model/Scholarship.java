package com.scholarshiptracker.model;

/**
 * Represents a scholarship offered by the institution.
 */
public class Scholarship {

    private final String scholarshipId;
    private final String name;
    private final ScholarshipType type;
    private final String description;

    public Scholarship(String scholarshipId, String name, ScholarshipType type, String description) {
        this.scholarshipId = scholarshipId;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getScholarshipId() {
        return scholarshipId;
    }

    public String getName() {
        return name;
    }

    public ScholarshipType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
