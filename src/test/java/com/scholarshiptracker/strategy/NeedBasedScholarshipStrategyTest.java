package com.scholarshiptracker.strategy;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.EligibilityResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NeedBasedScholarshipStrategyTest {

    private NeedBasedScholarshipStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new NeedBasedScholarshipStrategy();
    }

    @Test
    void evaluate_gpaAndIncomeWithinLimits_returnsEligible() {
        EligibilityResult result = strategy.evaluate(3.0, 2500.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_gpaAtThresholdAndIncomeAtCeiling_returnsEligible() {
        EligibilityResult result = strategy.evaluate(2.8, 3000.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_gpaBelowThreshold_returnsNotEligible() {
        EligibilityResult result = strategy.evaluate(2.79, 2000.0);

        assertFalse(result.isEligible());
        assertEquals(ApplicationStatus.NOT_ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_incomeAboveCeiling_returnsNotEligible() {
        EligibilityResult result = strategy.evaluate(3.0, 3000.01);

        assertFalse(result.isEligible());
        assertEquals(ApplicationStatus.NOT_ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }
}
