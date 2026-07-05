package com.scholarshiptracker.service;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.EligibilityResult;
import com.scholarshiptracker.model.ScholarshipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EligibilityServiceTest {

    private EligibilityService service;

    @BeforeEach
    void setUp() {
        service = new EligibilityService();
    }

    @ParameterizedTest
    @CsvSource({
            "MERIT, 2.0, 1000, false, NOT_ELIGIBLE",
            "MERIT, 3.5, 3000, true, ELIGIBLE",
            "MERIT, 4.0, 6000, true, ELIGIBLE"
    })
    void evaluateEligibility_meritGpaBoundaries_returnsExpectedEligibility(
            ScholarshipType type,
            double gpa,
            double householdIncome,
            boolean expectedEligible,
            ApplicationStatus expectedStatus) {
        EligibilityResult result = service.evaluateEligibility(type, gpa, householdIncome);

        assertEquals(expectedEligible, result.isEligible());
        assertEquals(expectedStatus, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @ParameterizedTest
    @CsvSource({
            "NEED_BASED, 2.0, 1000, false, NOT_ELIGIBLE",
            "NEED_BASED, 3.5, 1000, true, ELIGIBLE",
            "NEED_BASED, 3.5, 3000, true, ELIGIBLE",
            "NEED_BASED, 3.5, 6000, false, NOT_ELIGIBLE"
    })
    void evaluateEligibility_needBasedGpaAndIncomeBoundaries_returnsExpectedEligibility(
            ScholarshipType type,
            double gpa,
            double householdIncome,
            boolean expectedEligible,
            ApplicationStatus expectedStatus) {
        EligibilityResult result = service.evaluateEligibility(type, gpa, householdIncome);

        assertEquals(expectedEligible, result.isEligible());
        assertEquals(expectedStatus, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @ParameterizedTest
    @CsvSource({
            "BALANCED, 2.0, 1000, false, NOT_ELIGIBLE",
            "BALANCED, 3.5, 1000, true, ELIGIBLE",
            "BALANCED, 3.5, 3000, true, ELIGIBLE",
            "BALANCED, 3.5, 6000, false, NOT_ELIGIBLE",
            "BALANCED, 4.0, 1000, true, ELIGIBLE"
    })
    void evaluateEligibility_balancedGpaAndIncomeBoundaries_returnsExpectedEligibility(
            ScholarshipType type,
            double gpa,
            double householdIncome,
            boolean expectedEligible,
            ApplicationStatus expectedStatus) {
        EligibilityResult result = service.evaluateEligibility(type, gpa, householdIncome);

        assertEquals(expectedEligible, result.isEligible());
        assertEquals(expectedStatus, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @ParameterizedTest
    @ValueSource(strings = {"MERIT", "NEED_BASED", "BALANCED"})
    void evaluateEligibility_highGpaLowIncomeAcrossTypes_returnsEligible(String scholarshipType) {
        ScholarshipType type = ScholarshipType.valueOf(scholarshipType);
        EligibilityResult result = service.evaluateEligibility(type, 4.0, 1000.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluateEligibility_nullScholarshipType_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.evaluateEligibility(null, 3.5, 3000.0));

        assertEquals("Scholarship type cannot be null.", exception.getMessage());
    }

    @Test
    void evaluateEligibility_meritLowGpa_returnsNotEligibleRegardlessOfIncome() {
        EligibilityResult result = service.evaluateEligibility(ScholarshipType.MERIT, 2.0, 6000.0);

        assertFalse(result.isEligible());
        assertEquals(ApplicationStatus.NOT_ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluateEligibility_balancedAtIncomeCeiling_returnsEligible() {
        EligibilityResult result = service.evaluateEligibility(ScholarshipType.BALANCED, 3.5, 5000.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }
}
