package com.scholarshiptracker.strategy;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.EligibilityResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BalancedScholarshipStrategyTest {

    private BalancedScholarshipStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new BalancedScholarshipStrategy();
    }

    @Test
    void evaluate_gpaAndIncomeWithinLimits_returnsEligible() {
        EligibilityResult result = strategy.evaluate(3.5, 4000.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_gpaAtThresholdAndIncomeAtCeiling_returnsEligible() {
        EligibilityResult result = strategy.evaluate(3.2, 5000.0);

        assertTrue(result.isEligible());
        assertEquals(ApplicationStatus.ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_gpaBelowThreshold_returnsNotEligible() {
        EligibilityResult result = strategy.evaluate(3.19, 4000.0);

        assertFalse(result.isEligible());
        assertEquals(ApplicationStatus.NOT_ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }

    @Test
    void evaluate_incomeAboveCeiling_returnsNotEligible() {
        EligibilityResult result = strategy.evaluate(3.5, 5000.01);

        assertFalse(result.isEligible());
        assertEquals(ApplicationStatus.NOT_ELIGIBLE, result.getStatus());
        assertNotNull(result.getReasoning());
    }
}
