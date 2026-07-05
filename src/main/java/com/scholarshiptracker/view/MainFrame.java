package com.scholarshiptracker.view;

import javax.swing.*;
import java.awt.*;

/**
 * Main application window for the Smart Scholarship Tracker.
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Smart Scholarship Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 520);
        setMinimumSize(new Dimension(720, 480));
        setLocationRelativeTo(null);

        ApplicationFormView formView = new ApplicationFormView();
        formView.setApplicationFormListener(this::showTemporarySubmissionMessage);
        setContentPane(formView);
    }

    private void showTemporarySubmissionMessage(
            String fullName,
            String studentId,
            String email,
            String gpaText,
            String incomeText,
            Object scholarshipType
    ) {
        JOptionPane.showMessageDialog(
                this,
                "Application form captured for " + fullName + ".\n"
                        + "Full eligibility processing will be connected in the next integration step.",
                "Application Captured",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
