package com.scholarshiptracker.service;

import com.scholarshiptracker.exception.ValidationException;
import com.scholarshiptracker.model.Scholarship;
import com.scholarshiptracker.model.ScholarshipType;
import com.scholarshiptracker.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationValidatorTest {

    private static final String VALID_NAME = "Jane Doe";
    private static final String VALID_ID = "S12345";
    private static final String VALID_EMAIL = "jane@example.com";

    private ApplicationValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ApplicationValidator();
    }

    @Test
    void validateStudentInput_gpaBelowMinimum_throwsValidationException() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validateStudentInput(VALID_NAME, VALID_ID, VALID_EMAIL, -1.0, 1000.0));

        assertEquals("GPA must be between 0.0 and 4.0.", exception.getMessage());
    }

    @Test
    void validateStudentInput_gpaAboveMaximum_throwsValidationException() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validateStudentInput(VALID_NAME, VALID_ID, VALID_EMAIL, 4.5, 1000.0));

        assertEquals("GPA must be between 0.0 and 4.0.", exception.getMessage());
    }

    @Test
    void validateStudentInput_negativeHouseholdIncome_throwsValidationException() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validateStudentInput(VALID_NAME, VALID_ID, VALID_EMAIL, 3.5, -500.0));

        assertEquals("Household income cannot be negative.", exception.getMessage());
    }

    @Test
    void validateStudentInput_blankStudentName_throwsValidationException() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validateStudentInput("   ", VALID_ID, VALID_EMAIL, 3.5, 1000.0));

        assertEquals("Full name is required.", exception.getMessage());
    }

    @Test
    void validateStudentInput_blankStudentId_throwsValidationException() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validateStudentInput(VALID_NAME, "", VALID_EMAIL, 3.5, 1000.0));

        assertEquals("Student ID is required.", exception.getMessage());
    }

    @Test
    void validateStudentInput_validInput_doesNotThrow() {
        assertDoesNotThrow(() -> validator.validateStudentInput(
                VALID_NAME, VALID_ID, VALID_EMAIL, 3.5, 1000.0));
    }

    @Test
    void validateApplication_nullStudent_throwsValidationException() {
        Scholarship scholarship = new Scholarship(
                "SCH-001", "Merit Award", ScholarshipType.MERIT, "Merit-based scholarship");

        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> validator.validateApplication(null, scholarship));

        assertEquals("Student details cannot be empty.", exception.getMessage());
    }
}
