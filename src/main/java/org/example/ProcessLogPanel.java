package org.example;

import javax.swing.*;
import java.awt.*;

public class ProcessLogPanel extends JPanel {
    private JTextArea logArea;

    public ProcessLogPanel() {
        setLayout(new BorderLayout());
        logArea = new JTextArea(10,30);
        logArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logArea);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void logMessage(String message) {
        logArea.append(message + "\n");
    }
}