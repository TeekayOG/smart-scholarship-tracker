package com.scholarshiptracker.controller;

import com.scholarshiptracker.exception.ValidationException;
import com.scholarshiptracker.model.Scholarship;
import com.scholarshiptracker.model.ScholarshipApplication;
import com.scholarshiptracker.model.ScholarshipType;
import com.scholarshiptracker.model.Student;
import com.scholarshiptracker.service.ApplicationService;
import com.scholarshiptracker.service.ApplicationValidator;

/**
 * Controller that connects GUI input to application service logic.
 */
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationValidator validator;

    public ApplicationController() {
        this(new ApplicationService(), new ApplicationValidator());
    }

    public ApplicationController(ApplicationService applicationService, ApplicationValidator validator) {
        this.applicationService = applicationService;
        this.validator = validator;
    }

    /**
     * Handles scholarship application submission from the GUI.
     *
     * @param fullName student full name
     * @param studentId student ID
     * @param email student email
     * @param gpaText GPA input as text
     * @param incomeText household income input as text
     * @param scholarshipType selected scholarship type
     * @return submitted scholarship application
     */
    public ScholarshipApplication submitApplication(
            String fullName,
            String studentId,
            String email,
            String gpaText,
            String incomeText,
            ScholarshipType scholarshipType
    ) {
        double gpa = parseDouble(gpaText, "GPA");
        double householdIncome = parseDouble(incomeText, "Household income");

        validator.validateStudentInput(fullName, studentId, email, gpa, householdIncome);

        Student student = new Student(fullName, studentId, email, gpa, householdIncome);
        Scholarship scholarship = applicationService.createScholarshipForType(scholarshipType);

        return applicationService.submitApplication(student, scholarship);
    }

    private double parseDouble(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }

        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException error) {
            throw new ValidationException(fieldName + " must be a valid number.");
        }
    }
}
