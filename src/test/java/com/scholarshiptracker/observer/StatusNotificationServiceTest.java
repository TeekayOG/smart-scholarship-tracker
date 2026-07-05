package com.scholarshiptracker.observer;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.Scholarship;
import com.scholarshiptracker.model.ScholarshipApplication;
import com.scholarshiptracker.model.ScholarshipType;
import com.scholarshiptracker.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class StatusNotificationServiceTest {

    private StatusNotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new StatusNotificationService();
    }

    @Test
    void addObserver_subscribedObserver_increasesObserverCount() {
        StubStatusObserver observer = new StubStatusObserver();

        notificationService.addObserver(observer);

        assertEquals(1, notificationService.getObserverCount());
    }

    @Test
    void notifyStatusChanged_subscribedObserver_triggersCallback() {
        StubStatusObserver observer = new StubStatusObserver();
        ScholarshipApplication application = createApplication();
        notificationService.addObserver(observer);

        notificationService.notifyStatusChanged(
                application,
                ApplicationStatus.PENDING_REVIEW,
                ApplicationStatus.ELIGIBLE);

        assertEquals(1, observer.getNotificationCount());
        assertSame(application, observer.getLastApplication());
        assertEquals(ApplicationStatus.PENDING_REVIEW, observer.getLastPreviousStatus());
        assertEquals(ApplicationStatus.ELIGIBLE, observer.getLastNewStatus());
    }

    @Test
    void notifyStatusChanged_unsubscribedObserver_suppressesCallback() {
        StubStatusObserver observer = new StubStatusObserver();
        ScholarshipApplication application = createApplication();
        notificationService.addObserver(observer);
        notificationService.removeObserver(observer);

        notificationService.notifyStatusChanged(
                application,
                ApplicationStatus.PENDING_REVIEW,
                ApplicationStatus.NOT_ELIGIBLE);

        assertEquals(0, observer.getNotificationCount());
    }

    @Test
    void notifyStatusChanged_noObservers_suppressesNotifications() {
        ScholarshipApplication application = createApplication();

        assertDoesNotThrow(() -> notificationService.notifyStatusChanged(
                application,
                ApplicationStatus.PENDING_REVIEW,
                ApplicationStatus.APPROVED));
        assertEquals(0, notificationService.getObserverCount());
    }

    @Test
    void notifyStatusChanged_multipleSubscribedObservers_triggersAllCallbacks() {
        StubStatusObserver firstObserver = new StubStatusObserver();
        StubStatusObserver secondObserver = new StubStatusObserver();
        ScholarshipApplication application = createApplication();
        notificationService.addObserver(firstObserver);
        notificationService.addObserver(secondObserver);

        notificationService.notifyStatusChanged(
                application,
                ApplicationStatus.ELIGIBLE,
                ApplicationStatus.APPROVED);

        assertEquals(1, firstObserver.getNotificationCount());
        assertEquals(1, secondObserver.getNotificationCount());
        assertEquals(ApplicationStatus.ELIGIBLE, firstObserver.getLastPreviousStatus());
        assertEquals(ApplicationStatus.APPROVED, secondObserver.getLastNewStatus());
    }

    private ScholarshipApplication createApplication() {
        Student student = new Student("Jane Doe", "S12345", "jane@example.com", 3.5, 1000.0);
        Scholarship scholarship = new Scholarship(
                "SCH-001", "Merit Award", ScholarshipType.MERIT, "Merit-based scholarship");
        return new ScholarshipApplication("APP-001", student, scholarship);
    }

    private static class StubStatusObserver implements StatusObserver {

        private int notificationCount;
        private ScholarshipApplication lastApplication;
        private ApplicationStatus lastPreviousStatus;
        private ApplicationStatus lastNewStatus;

        @Override
        public void onStatusChanged(
                ScholarshipApplication application,
                ApplicationStatus previousStatus,
                ApplicationStatus newStatus) {
            notificationCount++;
            lastApplication = application;
            lastPreviousStatus = previousStatus;
            lastNewStatus = newStatus;
        }

        int getNotificationCount() {
            return notificationCount;
        }

        ScholarshipApplication getLastApplication() {
            return lastApplication;
        }

        ApplicationStatus getLastPreviousStatus() {
            return lastPreviousStatus;
        }

        ApplicationStatus getLastNewStatus() {
            return lastNewStatus;
        }
    }
}
