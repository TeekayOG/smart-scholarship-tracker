package com.scholarshiptracker.service;

import com.scholarshiptracker.exception.ValidationException;
import com.scholarshiptracker.model.Scholarship;
import com.scholarshiptracker.model.Student;

/**
 * Validates student and scholarship application input before processing.
 */
public class ApplicationValidator {

    private static final double MIN_GPA = 0.0;
    private static final double MAX_GPA = 4.0;
    private static final double MIN_INCOME = 0.0;

    /**
     * Validates student input before creating a student object.
     *
     * @param fullName student full name
     * @param studentId student ID
     * @param email student email
     * @param gpa student GPA
     * @param householdIncome household income
     */
    public void validateStudentInput(
            String fullName,
            String studentId,
            String email,
            double gpa,
            double householdIncome
    ) {
        validateRequiredText(fullName, "Full name");
        validateRequiredText(studentId, "Student ID");
        validateRequiredText(email, "Email");
        validateGpa(gpa);
        validateIncome(householdIncome);
    }

    /**
     * Validates the application objects before eligibility evaluation.
     *
     * @param student student applying for the scholarship
     * @param scholarship selected scholarship
     */
    public void validateApplication(Student student, Scholarship scholarship) {
        if (student == null) {
            throw new ValidationException("Student details cannot be empty.");
        }
        if (scholarship == null) {
            throw new ValidationException("Scholarship selection cannot be empty.");
        }
    }

    private void validateRequiredText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " is required.");
        }
    }

    private void validateGpa(double gpa) {
        if (gpa < MIN_GPA || gpa > MAX_GPA) {
            throw new ValidationException("GPA must be between 0.0 and 4.0.");
        }
    }

    private void validateIncome(double householdIncome) {
        if (householdIncome < MIN_INCOME) {
            throw new ValidationException("Household income cannot be negative.");
        }
    }
}
