package com.scholarshiptracker.controller;

import com.scholarshiptracker.exception.ValidationException;
import com.scholarshiptracker.model.ScholarshipApplication;
import com.scholarshiptracker.model.ScholarshipType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationControllerTest {

    @Test
    void submitApplication_withValidInputs_returnsCompletedApplication() {
        ApplicationController controller = new ApplicationController();

        ScholarshipApplication application = controller.submitApplication(
                "Test Student",
                "0381460",
                "test@example.com",
                "3.80",
                "3000",
                ScholarshipType.MERIT
        );

        assertAll(
                () -> assertTrue(application.getApplicationId().startsWith("APP-")),
                () -> assertEquals("Test Student", application.getStudent().getFullName()),
                () -> assertEquals(ScholarshipType.MERIT, application.getScholarship().getType()),
                () -> assertNotNull(application.getEligibilityResult())
        );
    }

    @Test
    void submitApplication_withBlankGpa_throwsValidationException() {
        ApplicationController controller = new ApplicationController();

        ValidationException error = assertThrows(
                ValidationException.class,
                () -> controller.submitApplication(
                        "Test Student",
                        "0381460",
                        "test@example.com",
                        " ",
                        "3000",
                        ScholarshipType.MERIT
                )
        );

        assertEquals("GPA is required.", error.getMessage());
    }

    @Test
    void submitApplication_withInvalidIncome_throwsValidationException() {
        ApplicationController controller = new ApplicationController();

        ValidationException error = assertThrows(
                ValidationException.class,
                () -> controller.submitApplication(
                        "Test Student",
                        "0381460",
                        "test@example.com",
                        "3.80",
                        "abc",
                        ScholarshipType.MERIT
                )
        );

        assertEquals("Household income must be a valid number.", error.getMessage());
    }
}
