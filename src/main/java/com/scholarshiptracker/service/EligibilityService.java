package com.scholarshiptracker.service;

import com.scholarshiptracker.model.*;
import com.scholarshiptracker.strategy.*;

/**
 * Service for evaluating scholarship eligibility using the appropriate strategy.
 */
public class EligibilityService {

    /**
     * Evaluates eligibility for the given scholarship type using GPA and household income.
     *
     * @param type the scholarship type
     * @param gpa the applicant's grade point average
     * @param householdIncome the applicant's annual household income
     * @return the eligibility outcome
     * @throws IllegalArgumentException if {@code type} is null
     */
    public EligibilityResult evaluateEligibility(ScholarshipType type, double gpa, double householdIncome) {
        if (type == null) {
            throw new IllegalArgumentException("Scholarship type cannot be null.");
        }

        EligibilityStrategy strategy = switch (type) {
            case MERIT -> new MeritScholarshipStrategy();
            case NEED_BASED -> new NeedBasedScholarshipStrategy();
            case BALANCED -> new BalancedScholarshipStrategy();
        };

        return strategy.evaluate(gpa, householdIncome);
    }
}
