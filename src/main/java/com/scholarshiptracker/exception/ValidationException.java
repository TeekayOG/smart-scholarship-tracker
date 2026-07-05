package com.scholarshiptracker.exception;

/**
 * Exception thrown when user input does not meet application validation rules.
 */
public class ValidationException extends IllegalArgumentException {

    public ValidationException(String message) {
        super(message);
    }
}
