package com.scholarshiptracker.strategy;

import com.scholarshiptracker.model.EligibilityResult;

/**
 * Defines a strategy for evaluating scholarship eligibility based on academic
 * and financial criteria.
 *
 * <p>Implementations apply different rules (for example, merit-based,
 * need-based, or balanced) to determine whether an applicant qualifies and
 * what status applies.</p>
 */
public interface EligibilityStrategy {

    /**
     * Evaluates eligibility using the applicant's GPA and household income.
     *
     * @param gpa the applicant's grade point average
     * @param householdIncome the applicant's annual household income
     * @return the eligibility outcome, including status and reasoning
     */
    EligibilityResult evaluate(double gpa, double householdIncome);
}
