package com.scholarshiptracker.strategy;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.EligibilityResult;

/**
 * Merit-based eligibility strategy that requires a minimum GPA.
 */
public class MeritScholarshipStrategy implements EligibilityStrategy {

    private static final double MIN_GPA = 3.5;

    @Override
    public EligibilityResult evaluate(double gpa, double householdIncome) {
        if (gpa >= MIN_GPA) {
            return new EligibilityResult(
                    true,
                    ApplicationStatus.ELIGIBLE,
                    "GPA of " + gpa + " meets the merit scholarship minimum requirement of " + MIN_GPA + ".");
        }
        return new EligibilityResult(
                false,
                ApplicationStatus.NOT_ELIGIBLE,
                "GPA of " + gpa + " is below the merit scholarship minimum requirement of " + MIN_GPA + ".");
    }
}
