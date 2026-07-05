package com.scholarshiptracker.view;

import com.scholarshiptracker.model.EligibilityResult;
import com.scholarshiptracker.model.ScholarshipApplication;

import javax.swing.*;
import java.awt.*;

/**
 * Swing screen that displays the scholarship eligibility result.
 */
public class ResultView extends JPanel {

    private final JLabel applicationIdValue;
    private final JLabel studentNameValue;
    private final JLabel scholarshipNameValue;
    private final JLabel scholarshipTypeValue;
    private final JLabel statusValue;
    private final JLabel eligibleValue;
    private final JTextArea reasoningArea;
    private Runnable backAction;

    public ResultView() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        applicationIdValue = new JLabel("-");
        studentNameValue = new JLabel("-");
        scholarshipNameValue = new JLabel("-");
        scholarshipTypeValue = new JLabel("-");
        statusValue = new JLabel("-");
        eligibleValue = new JLabel("-");
        reasoningArea = new JTextArea(5, 40);

        configureReasoningArea();

        add(createHeader(), BorderLayout.NORTH);
        add(createResultPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    public void setBackAction(Runnable backAction) {
        this.backAction = backAction;
    }

    public void displayResult(ScholarshipApplication application) {
        EligibilityResult result = application.getEligibilityResult();

        applicationIdValue.setText(application.getApplicationId());
        studentNameValue.setText(application.getStudent().getFullName());
        scholarshipNameValue.setText(application.getScholarship().getName());
        scholarshipTypeValue.setText(application.getScholarship().getType().name());
        statusValue.setText(application.getStatus().name());
        eligibleValue.setText(result.isEligible() ? "YES" : "NO");
        reasoningArea.setText(result.getReasoning());

        updateStatusColour(result.isEligible());
    }

    private JLabel createHeader() {
        JLabel title = new JLabel("Eligibility Result");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        return title;
    }

    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createConstraints();

        addRow(panel, gbc, 0, "Application ID:", applicationIdValue);
        addRow(panel, gbc, 1, "Student Name:", studentNameValue);
        addRow(panel, gbc, 2, "Scholarship:", scholarshipNameValue);
        addRow(panel, gbc, 3, "Scholarship Type:", scholarshipTypeValue);
        addRow(panel, gbc, 4, "Status:", statusValue);
        addRow(panel, gbc, 5, "Eligible:", eligibleValue);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Reason:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(reasoningArea), gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JButton backButton = new JButton("Back to Application Form");
        backButton.addActionListener(event -> goBack());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(backButton);
        return panel;
    }

    private GridBagConstraints createConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, JLabel value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(value, gbc);
    }

    private void configureReasoningArea() {
        reasoningArea.setEditable(false);
        reasoningArea.setLineWrap(true);
        reasoningArea.setWrapStyleWord(true);
    }

    private void updateStatusColour(boolean eligible) {
        if (eligible) {
            statusValue.setForeground(new Color(0, 128, 0));
            eligibleValue.setForeground(new Color(0, 128, 0));
            return;
        }

        statusValue.setForeground(Color.RED);
        eligibleValue.setForeground(Color.RED);
    }

    private void goBack() {
        if (backAction != null) {
            backAction.run();
        }
    }
}
