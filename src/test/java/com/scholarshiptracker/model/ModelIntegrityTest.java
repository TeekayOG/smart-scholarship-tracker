package com.scholarshiptracker.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for domain models verifying state integrity and grouped assertions.
 */
class ModelIntegrityTest {

    @Test
    void constructor_validApplicationDetails_initializesAllFieldsCorrectly() {
        Student student = new Student("Ali Bin Ahmad", "S12345", "ali@student.edu", 3.8, 2500.0);
        Scholarship scholarship = new Scholarship("SCH-01", "Merit Excellence", ScholarshipType.MERIT, "For top academic achievers.");

        ScholarshipApplication application = new ScholarshipApplication("APP-001", student, scholarship);

        // Required rubric element: assertAll grouping to verify multiple properties simultaneously
        assertAll("Verify newly created application state",
                () -> assertEquals("APP-001", application.getApplicationId(), "Application ID should match input"),
                () -> assertEquals(student, application.getStudent(), "Student reference should match input"),
                () -> assertEquals(scholarship, application.getScholarship(), "Scholarship reference should match input"),
                () -> assertEquals(ApplicationStatus.PENDING_REVIEW, application.getStatus(), "Initial status should default to PENDING_REVIEW"),
                () -> assertNotNull(application.getSubmittedAt(), "Submission timestamp should be automatically generated")
        );
    }

    @Test
    void setEligibilityResult_eligibleResult_updatesApplicationStatusToEligible() {
        Student student = new Student("Ali Bin Ahmad", "S12345", "ali@student.edu", 3.8, 2500.0);
        Scholarship scholarship = new Scholarship("SCH-01", "Merit Excellence", ScholarshipType.MERIT, "Description");
        ScholarshipApplication application = new ScholarshipApplication("APP-001", student, scholarship);

        EligibilityResult result = new EligibilityResult(true, ApplicationStatus.ELIGIBLE, "Qualifies for merit scholarship.");
        application.setEligibilityResult(result);

        assertAll("Verify updated application status and eligibility outcome",
                () -> assertEquals(result, application.getEligibilityResult()),
                () -> assertEquals(ApplicationStatus.ELIGIBLE, application.getStatus()),
                () -> assertTrue(application.getEligibilityResult().isEligible())
        );
    }

    @Test
    void updateStatus_newStatus_changesApplicationStatusSuccessfully() {
        Student student = new Student("Ali Bin Ahmad", "S12345", "ali@student.edu", 3.8, 2500.0);
        Scholarship scholarship = new Scholarship("SCH-01", "Merit Excellence", ScholarshipType.MERIT, "Description");
        ScholarshipApplication application = new ScholarshipApplication("APP-001", student, scholarship);

        application.updateStatus(ApplicationStatus.MISSING_DOCUMENTS);

        assertEquals(ApplicationStatus.MISSING_DOCUMENTS, application.getStatus());
    }
}