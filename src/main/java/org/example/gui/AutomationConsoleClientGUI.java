package org.example.gui;

import org.example.client.NetworkService;
import org.example.client.CDTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The AutomationConsoleClientGUI class provides a graphical user interface (GUI) for the client-side application.
 * <p>
 * This GUI allows the client user to:
 * <ul>
 *   <li>Interact with the server to fetch CD data.</li>
 *   <li>Display the fetched data in a table format.</li>
 * </ul>
 * <p>
 * The communication with the server is handled by the {@link NetworkService} class.
 * The CD data is displayed using a {@link JTable} backed by the {@link CDTableModel}.
 */
public class AutomationConsoleClientGUI extends JFrame {
    private NetworkService networkService;
    private JTable cdTable;

    public AutomationConsoleClientGUI() {
        networkService = new NetworkService("http://localhost:8080");

        // Set up the GUI
        setTitle("Automation Console - Client GUI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table to display CD data
        cdTable = new JTable();
        add(new JScrollPane(cdTable), BorderLayout.CENTER);

        // Fetch Data Button
        JButton fetchButton = new JButton("Fetch CDs");
        fetchButton.addActionListener(e -> fetchAndDisplayCDs());
        add(fetchButton, BorderLayout.SOUTH);

        setVisible(true);
    }
    /**
     * Fetches CD data from the server using {@link NetworkService} and displays it in the table.
     */
    private void fetchAndDisplayCDs() {
        try {
            List<CD> cds = networkService.getCDs();
            CDTableModel tableModel = new CDTableModel(cds);
            cdTable.setModel(tableModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to fetch data from server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
/**
 * Main method to launch the AutomationConsoleClient GUI.
 *
 * @param args Command-line arguments (not used).
 */
    public static void main(String[] args) {
        new AutomationConsoleClientGUI();
    }
}
