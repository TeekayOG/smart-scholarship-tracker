package com.scholarshiptracker.strategy;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.EligibilityResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MeritScholarshipStrategyTest {

    private MeritScholarshipStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new MeritScholarshipStrategy();
    }

    @Test
    void evaluate_gpaAboveThreshold_returnsEligible() {
        EligibilityResult result = strategy.evaluate(3.6, 0.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_gpaAtThreshold_returnsEligible() {
        EligibilityResult result = strategy.evaluate(3.5, 0.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_gpaBelowThreshold_returnsNotEligible() {
        EligibilityResult result = strategy.evaluate(3.49, 0.0);

        assertFalse(result.isEligible());
        assertEquals(ApplicationStatus.NOT_ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }
}
