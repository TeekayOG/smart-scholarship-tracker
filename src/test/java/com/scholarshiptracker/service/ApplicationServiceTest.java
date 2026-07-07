package com.scholarshiptracker.service;

import com.scholarshiptracker.exception.ValidationException;
import com.scholarshiptracker.model.Scholarship;
import com.scholarshiptracker.model.ScholarshipApplication;
import com.scholarshiptracker.model.ScholarshipType;
import com.scholarshiptracker.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationServiceTest {

    @Test
    void submitApplication_withValidStudentAndScholarship_setsEligibilityResult() {
        ApplicationService service = new ApplicationService();
        Student student = new Student("Test Student", "0381460", "test@example.com", 3.8, 3000);
        Scholarship scholarship = service.createScholarshipForType(ScholarshipType.MERIT);

        ScholarshipApplication application = service.submitApplication(student, scholarship);

        assertAll(
                () -> assertTrue(application.getApplicationId().startsWith("APP-")),
                () -> assertEquals(student, application.getStudent()),
                () -> assertEquals(scholarship, application.getScholarship()),
                () -> assertNotNull(application.getEligibilityResult()),
                () -> assertTrue(application.getEligibilityResult().isEligible())
        );
    }

    @Test
    void createScholarshipForType_withMerit_returnsMeritScholarship() {
        ApplicationService service = new ApplicationService();

        Scholarship scholarship = service.createScholarshipForType(ScholarshipType.MERIT);

        assertAll(
                () -> assertEquals("SCH-MERIT", scholarship.getScholarshipId()),
                () -> assertEquals("Merit Excellence Scholarship", scholarship.getName()),
                () -> assertEquals(ScholarshipType.MERIT, scholarship.getType())
        );
    }

    @Test
    void createScholarshipForType_withNeedBased_returnsNeedBasedScholarship() {
        ApplicationService service = new ApplicationService();

        Scholarship scholarship = service.createScholarshipForType(ScholarshipType.NEED_BASED);

        assertAll(
                () -> assertEquals("SCH-NEED", scholarship.getScholarshipId()),
                () -> assertEquals("Need-Based Education Support Scholarship", scholarship.getName()),
                () -> assertEquals(ScholarshipType.NEED_BASED, scholarship.getType())
        );
    }

    @Test
    void createScholarshipForType_withBalanced_returnsBalancedScholarship() {
        ApplicationService service = new ApplicationService();

        Scholarship scholarship = service.createScholarshipForType(ScholarshipType.BALANCED);

        assertAll(
                () -> assertEquals("SCH-BALANCED", scholarship.getScholarshipId()),
                () -> assertEquals("Balanced Achievement Scholarship", scholarship.getName()),
                () -> assertEquals(ScholarshipType.BALANCED, scholarship.getType())
        );
    }

    @Test
    void createScholarshipForType_withNullType_throwsValidationException() {
        ApplicationService service = new ApplicationService();

        ValidationException error = assertThrows(
                ValidationException.class,
                () -> service.createScholarshipForType(null)
        );

        assertEquals("Scholarship type is required.", error.getMessage());
    }
}
