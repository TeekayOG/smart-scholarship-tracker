package com.scholarshiptracker.observer;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.ScholarshipApplication;

/**
 * Observer interface for receiving scholarship application status-change updates.
 */
public interface StatusObserver {

    /**
     * Called when an application's status changes.
     *
     * @param application scholarship application
     * @param previousStatus previous application status
     * @param newStatus new application status
     */
    void onStatusChanged(
            ScholarshipApplication application,
            ApplicationStatus previousStatus,
            ApplicationStatus newStatus
    );
}
