package com.scholarshiptracker.view;

import com.scholarshiptracker.api.University;
import com.scholarshiptracker.api.UniversityLookupResult;
import com.scholarshiptracker.api.UniversityLookupService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Swing screen that allows the user to fetch university data from an external REST API.
 */
public class UniversityLookupView extends JPanel {

    private static final int MAX_DISPLAYED_UNIVERSITIES = 8;

    private final JTextField countryField;
    private final JTextArea resultArea;
    private final JLabel statusLabel;
    private final UniversityLookupService lookupService;

    public UniversityLookupView() {
        this(new UniversityLookupService());
    }

    public UniversityLookupView(UniversityLookupService lookupService) {
        this.lookupService = lookupService;
        this.countryField = new JTextField("Malaysia", 20);
        this.resultArea = new JTextArea(14, 50);
        this.statusLabel = new JLabel("Enter a country and search for university data.");

        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        configureResultArea();

        add(createHeader(), BorderLayout.NORTH);
        add(createSearchPanel(), BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    private JLabel createHeader() {
        JLabel title = new JLabel("External API: University Lookup");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        return title;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.add(createInputPanel(), BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createInputPanel() {
        JButton searchButton = new JButton("Search Universities");
        searchButton.addActionListener(event -> searchUniversities());

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Country:"));
        inputPanel.add(countryField);
        inputPanel.add(searchButton);
        return inputPanel;
    }

    private void configureResultArea() {
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
    }

    private void searchUniversities() {
        statusLabel.setText("Searching...");
        resultArea.setText("");

        SwingWorker<UniversityLookupResult, Void> worker = new SwingWorker<>() {
            @Override
            protected UniversityLookupResult doInBackground() {
                return lookupService.searchUniversities(countryField.getText());
            }

            @Override
            protected void done() {
                displayLookupResult();
            }

            private void displayLookupResult() {
                try {
                    UniversityLookupResult result = get();
                    statusLabel.setText(result.getMessage());

                    if (result.isSuccess()) {
                        resultArea.setText(formatUniversities(result.getUniversities()));
                        return;
                    }

                    resultArea.setText(result.getMessage());
                } catch (Exception error) {
                    statusLabel.setText("University lookup failed.");
                    resultArea.setText("University data is temporarily unavailable. Please try again later.");
                }
            }
        };

        worker.execute();
    }

    private String formatUniversities(List<University> universities) {
        StringBuilder builder = new StringBuilder();
        int count = Math.min(universities.size(), MAX_DISPLAYED_UNIVERSITIES);

        builder.append("Showing ").append(count).append(" of ")
                .append(universities.size()).append(" records:\n\n");

        for (int index = 0; index < count; index++) {
            University university = universities.get(index);
            builder.append(index + 1)
                    .append(". ")
                    .append(university.getName())
                    .append("\n   Country: ")
                    .append(university.getCountry())
                    .append("\n   Website: ")
                    .append(university.getPrimaryWebsite())
                    .append("\n\n");
        }

        return builder.toString();
    }
}
