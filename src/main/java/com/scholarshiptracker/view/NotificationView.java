package com.scholarshiptracker.view;

import com.scholarshiptracker.model.ApplicationStatus;
import com.scholarshiptracker.model.ScholarshipApplication;
import com.scholarshiptracker.observer.StatusObserver;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Swing screen that displays scholarship application status notifications.
 */
public class NotificationView extends JPanel implements StatusObserver {

    private final DefaultListModel<String> notificationListModel;
    private final JList<String> notificationList;
    private final DateTimeFormatter formatter;

    public NotificationView() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        notificationListModel = new DefaultListModel<>();
        notificationList = new JList<>(notificationListModel);

        add(createHeader(), BorderLayout.NORTH);
        add(new JScrollPane(notificationList), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        addMessage("Notification tracker ready.");
    }

    /**
     * Adds a manual message to the notification list.
     *
     * @param message message to display
     */
    public void addMessage(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        notificationListModel.addElement("[" + timestamp + "] " + message);
    }

    /**
     * Receives application status-change updates through the Observer pattern.
     *
     * @param application scholarship application
     * @param previousStatus previous application status
     * @param newStatus new application status
     */
    @Override
    public void onStatusChanged(
            ScholarshipApplication application,
            ApplicationStatus previousStatus,
            ApplicationStatus newStatus
    ) {
        addMessage(
                "Application " + application.getApplicationId()
                        + " for " + application.getStudent().getFullName()
                        + " changed from " + previousStatus
                        + " to " + newStatus + "."
        );
    }

    private JLabel createHeader() {
        JLabel title = new JLabel("Application Notifications");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        return title;
    }

    private JPanel createFooter() {
        JButton clearButton = new JButton("Clear Notifications");
        clearButton.addActionListener(event -> notificationListModel.clear());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(clearButton);
        return panel;
    }
}
