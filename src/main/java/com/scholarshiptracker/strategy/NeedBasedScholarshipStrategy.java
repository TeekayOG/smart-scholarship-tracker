package com.scholarshiptracker.strategy;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.EligibilityResult;

/**
 * Need-based eligibility strategy that requires a minimum GPA and a maximum household income.
 */
public class NeedBasedScholarshipStrategy implements EligibilityStrategy {

    private static final double MIN_GPA = 2.8;
    private static final double MAX_INCOME = 3000.0;

    @Override
    public EligibilityResult evaluate(double gpa, double householdIncome) {
        if (gpa < MIN_GPA) {
            return new EligibilityResult(
                    false,
                    ApplicationStatus.NOT_ELIGIBLE,
                    "GPA of " + gpa + " is below the need-based scholarship minimum requirement of " + MIN_GPA + ".");
        }
        if (householdIncome <= MAX_INCOME) {
            return new EligibilityResult(
                    true,
                    ApplicationStatus.ELIGIBLE,
                    "GPA of " + gpa + " and household income of " + householdIncome
                            + " meet the need-based scholarship criteria.");
        }
        return new EligibilityResult(
                false,
                ApplicationStatus.NOT_ELIGIBLE,
                "Household income of " + householdIncome + " exceeds the need-based scholarship ceiling of "
                        + MAX_INCOME + ".");
    }
}
