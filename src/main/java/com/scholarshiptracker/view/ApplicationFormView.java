package com.scholarshiptracker.view;

import com.scholarshiptracker.model.ScholarshipType;

import javax.swing.*;
import java.awt.*;

/**
 * Swing form that collects scholarship application details from the user.
 */
public class ApplicationFormView extends JPanel {

    private final JTextField fullNameField;
    private final JTextField studentIdField;
    private final JTextField emailField;
    private final JTextField gpaField;
    private final JTextField incomeField;
    private final JComboBox<ScholarshipType> scholarshipTypeBox;
    private ApplicationFormListener listener;

    public ApplicationFormView() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        fullNameField = new JTextField(20);
        studentIdField = new JTextField(20);
        emailField = new JTextField(20);
        gpaField = new JTextField(20);
        incomeField = new JTextField(20);
        scholarshipTypeBox = new JComboBox<>(ScholarshipType.values());

        add(createHeader(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    public void setApplicationFormListener(ApplicationFormListener listener) {
        this.listener = listener;
    }

    private JLabel createHeader() {
        JLabel title = new JLabel("Smart Scholarship Tracker");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        return title;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createConstraints();

        addField(panel, gbc, 0, "Full Name:", fullNameField);
        addField(panel, gbc, 1, "Student ID:", studentIdField);
        addField(panel, gbc, 2, "Email:", emailField);
        addField(panel, gbc, 3, "GPA:", gpaField);
        addField(panel, gbc, 4, "Household Income:", incomeField);
        addField(panel, gbc, 5, "Scholarship Type:", scholarshipTypeBox);

        return panel;
    }

    private JPanel createButtonPanel() {
        JButton submitButton = new JButton("Submit Application");
        submitButton.addActionListener(event -> notifySubmission());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(submitButton);
        return panel;
    }

    private GridBagConstraints createConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void notifySubmission() {
        if (listener == null) {
            return;
        }

        listener.onSubmit(
                fullNameField.getText(),
                studentIdField.getText(),
                emailField.getText(),
                gpaField.getText(),
                incomeField.getText(),
                (ScholarshipType) scholarshipTypeBox.getSelectedItem()
        );
    }

    /**
     * Listener used by the main frame or controller to handle form submission.
     */
    public interface ApplicationFormListener {
        void onSubmit(
                String fullName,
                String studentId,
                String email,
                String gpaText,
                String incomeText,
                ScholarshipType scholarshipType
        );
    }
}
