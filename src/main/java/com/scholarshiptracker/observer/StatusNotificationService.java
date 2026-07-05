package com.scholarshiptracker.observer;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.ScholarshipApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject class that manages observers and notifies them when an application status changes.
 */
public class StatusNotificationService {

    private final List<StatusObserver> observers = new ArrayList<>();

    /**
     * Registers an observer for status-change notifications.
     *
     * @param observer observer to register
     */
    public void addObserver(StatusObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes an observer from the notification list.
     *
     * @param observer observer to remove
     */
    public void removeObserver(StatusObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers about a status change.
     *
     * @param application scholarship application
     * @param previousStatus previous status
     * @param newStatus new status
     */
    public void notifyStatusChanged(
            ScholarshipApplication application,
            ApplicationStatus previousStatus,
            ApplicationStatus newStatus
    ) {
        for (StatusObserver observer : observers) {
            observer.onStatusChanged(application, previousStatus, newStatus);
        }
    }

    public int getObserverCount() {
        return observers.size();
    }
}
