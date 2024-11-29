package org.example.gui;

import org.example.server.ArchiveConsoleServer;
import org.example.server.ProcessLogPanel;

import javax.swing.*;
import java.awt.*;

/**
 * The ArchiveConsole class is a GUI for the ArchiveConsoleServer.
 * <p>
 * This GUI allows the server operator to:
 * <ul>
 *   <li>Monitor server activity and view logs via the {@link ProcessLogPanel}.</li>
 *   <li>Manage the server lifecycle, such as starting the server.</li>
 * </ul>
 * <p>
 * The server logic is managed by the ArchiveConsoleServer class.
 */
public class ArchiveConsole extends JFrame {
    private final ArchiveConsoleServer server;
    //private final ProcessLogPanel processLogPanel;

    /**
     * Constructor for the ArchiveConsole GUI and initializes the server instance.
     */
    public ArchiveConsole() {
        server = new ArchiveConsoleServer();
        //processLogPanel = new ProcessLogPanel(server);

        setTitle("Archive Console - Server GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Log Panel
        ProcessLogPanel logPanel = server.getLogPanel();
        add(logPanel, BorderLayout.CENTER);

        // Start Server Button
        JButton startServerButton = new JButton("Start Server");
        startServerButton.addActionListener(e -> {
            server.startServer();
            startServerButton.setEnabled(false);
        });

        add(startServerButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ArchiveConsole();
    }
}