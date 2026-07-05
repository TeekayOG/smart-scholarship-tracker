package com.scholarshiptracker.service;

import com.scholarshiptracker.exception.ValidationException;
import com.scholarshiptracker.model.EligibilityResult;
import com.scholarshiptracker.model.Scholarship;
import com.scholarshiptracker.model.ScholarshipApplication;
import com.scholarshiptracker.model.ScholarshipType;
import com.scholarshiptracker.model.Student;

import java.util.UUID;

/**
 * Handles scholarship application creation and eligibility evaluation.
 */
public class ApplicationService {

    private final EligibilityService eligibilityService;
    private final ApplicationValidator validator;

    public ApplicationService() {
        this(new EligibilityService(), new ApplicationValidator());
    }

    public ApplicationService(EligibilityService eligibilityService, ApplicationValidator validator) {
        this.eligibilityService = eligibilityService;
        this.validator = validator;
    }

    /**
     * Submits a scholarship application and evaluates eligibility.
     *
     * @param student student applying for the scholarship
     * @param scholarship selected scholarship
     * @return completed scholarship application with eligibility result
     */
    public ScholarshipApplication submitApplication(Student student, Scholarship scholarship) {
        validator.validateApplication(student, scholarship);

        ScholarshipApplication application = new ScholarshipApplication(
                generateApplicationId(),
                student,
                scholarship
        );

        EligibilityResult result = eligibilityService.evaluateEligibility(
                scholarship.getType(),
                student.getGpa(),
                student.getHouseholdIncome()
        );

        application.setEligibilityResult(result);
        return application;
    }

    /**
     * Creates a scholarship object based on the selected scholarship type.
     *
     * @param type selected scholarship type
     * @return scholarship object
     */
    public Scholarship createScholarshipForType(ScholarshipType type) {
        if (type == null) {
            throw new ValidationException("Scholarship type is required.");
        }

        return switch (type) {
            case MERIT -> new Scholarship(
                    "SCH-MERIT",
                    "Merit Excellence Scholarship",
                    ScholarshipType.MERIT,
                    "Awarded to students with strong academic performance."
            );
            case NEED_BASED -> new Scholarship(
                    "SCH-NEED",
                    "Need-Based Education Support Scholarship",
                    ScholarshipType.NEED_BASED,
                    "Awarded to students who meet financial need requirements."
            );
            case BALANCED -> new Scholarship(
                    "SCH-BALANCED",
                    "Balanced Achievement Scholarship",
                    ScholarshipType.BALANCED,
                    "Awarded based on both academic performance and household income."
            );
        };
    }

    private String generateApplicationId() {
        return "APP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
