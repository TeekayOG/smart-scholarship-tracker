package com.scholarshiptracker.view;

import com.scholarshiptracker.controller.ApplicationController;
import com.scholarshiptracker.exception.ValidationException;
import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.ScholarshipApplication;
import com.scholarshiptracker.model.ScholarshipType;
import com.scholarshiptracker.observer.StatusNotificationService;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window for the Smart Scholarship Tracker.
 */
public class MainFrame extends JFrame {

    private static final String FORM_CARD = "FORM_CARD";
    private static final String RESULT_CARD = "RESULT_CARD";

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final ApplicationFormView formView;
    private final ResultView resultView;
    private final NotificationView notificationView;
    private final UniversityLookupView universityLookupView;
    private final ApplicationController controller;
    private final StatusNotificationService notificationService;

    public MainFrame() {
        controller = new ApplicationController();
        notificationService = new StatusNotificationService();

        formView = new ApplicationFormView();
        resultView = new ResultView();
        notificationView = new NotificationView();
        universityLookupView = new UniversityLookupView();

        notificationService.addObserver(notificationView);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(formView, FORM_CARD);
        cardPanel.add(resultView, RESULT_CARD);

        configureFrame();
        configureActions();
        setContentPane(createMainTabs());
    }

    private void configureFrame() {
        setTitle("Smart Scholarship Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 620);
        setMinimumSize(new Dimension(820, 560));
        setLocationRelativeTo(null);
    }

    private void configureActions() {
        formView.setApplicationFormListener(this::submitApplication);
        resultView.setBackAction(() -> cardLayout.show(cardPanel, FORM_CARD));
    }

    private JTabbedPane createMainTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Application", cardPanel);
        tabs.addTab("Notifications", notificationView);
        tabs.addTab("University API", universityLookupView);
        return tabs;
    }

    private void submitApplication(
            String fullName,
            String studentId,
            String email,
            String gpaText,
            String incomeText,
            ScholarshipType scholarshipType
    ) {
        try {
            ScholarshipApplication application = controller.submitApplication(
                    fullName,
                    studentId,
                    email,
                    gpaText,
                    incomeText,
                    scholarshipType
            );

            notifyStatusChange(application);
            resultView.displayResult(application);
            cardLayout.show(cardPanel, RESULT_CARD);
        } catch (ValidationException error) {
            showError(error.getMessage());
        }
    }

    private void notifyStatusChange(ScholarshipApplication application) {
        notificationService.notifyStatusChanged(
                application,
                ApplicationStatus.PENDING_REVIEW,
                application.getStatus()
        );
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Application Details",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
