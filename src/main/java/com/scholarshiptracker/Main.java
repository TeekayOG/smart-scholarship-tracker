package com.scholarshiptracker;

import com.scholarshiptracker.view.MainFrame;

import javax.swing.*;

/**
 * Entry point for the Smart Scholarship Tracker application.
 */
public class Main {

    /**
     * Starts the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
