package com.scholarshiptracker.strategy;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.EligibilityResult;

/**
 * Balanced eligibility strategy that requires both a minimum GPA and a maximum household income.
 */
public class BalancedScholarshipStrategy implements EligibilityStrategy {

    private static final double MIN_GPA = 3.2;
    private static final double MAX_INCOME = 5000.0;

    @Override
    public EligibilityResult evaluate(double gpa, double householdIncome) {
        if (gpa >= MIN_GPA && householdIncome <= MAX_INCOME) {
            return new EligibilityResult(
                    true,
                    ApplicationStatus.ELIGIBLE,
                    "GPA of " + gpa + " and household income of " + householdIncome
                            + " meet the balanced scholarship criteria.");
        }
        return new EligibilityResult(
                false,
                ApplicationStatus.NOT_ELIGIBLE,
                "Applicant does not qualify for the balanced scholarship. Requires a minimum GPA of " + MIN_GPA
                        + " and a maximum household income of " + MAX_INCOME + ".");
    }
}
