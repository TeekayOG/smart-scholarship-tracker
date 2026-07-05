package com.scholarshiptracker.model;

/**
 * Represents a student applying for a scholarship.
 */
public class Student {

    private final String fullName;
    private final String studentId;
    private final String email;
    private final double gpa;
    private final double householdIncome;

    public Student(String fullName, String studentId, String email, double gpa, double householdIncome) {
        this.fullName = fullName;
        this.studentId = studentId;
        this.email = email;
        this.gpa = gpa;
        this.householdIncome = householdIncome;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getEmail() {
        return email;
    }

    public double getGpa() {
        return gpa;
    }

    public double getHouseholdIncome() {
        return householdIncome;
    }
}
