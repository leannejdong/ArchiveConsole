package org.example.server;

import org.example.model.CDTableModel;
import org.example.model.CD;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.logging.Logger;

public class ArchiveConsoleServer {
    private List<CD> cds;
    private ProcessLogPanel logPanel;

    public ArchiveConsoleServer() {
        try {
            // Initialize CDTableModel and load data into it
            CDTableModel tableModel = new CDTableModel("data.txt");
            cds = tableModel.getCds();


            // Initialize ProcessLogPanel
            logPanel = new ProcessLogPanel();

            System.out.println("ArchiveConsoleServer initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing ArchiveConsoleServer: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<CD> getAllCDs() {
        return cds;
    }

    public List<CD> sortCDs(String algorithm) {
        SortingAlgorithms.sort(cds, algorithm);
        logPanel.addLog("Sorted CDs using " + algorithm);
        return cds;
    }

    public void startServer() {
        try {
            // Load data from file using DataLoader
          //  List<CD> cds = DataLoader.loadData("data.txt");

            // Initialize table model and load data into it
            CDTableModel tableModel = new CDTableModel("data.txt");
            List<CD> cds = tableModel.getCds();
        //    tableModel.loadData(cds);

            System.out.println("Server started successfully. Data loaded into the table model.");

            // Add further server initialization logic here if required

        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ProcessLogPanel getLogPanel() {
        return logPanel;
    }

    public static void main(String[] args) {
        ArchiveConsoleServer server = new ArchiveConsoleServer();
        server.startServer();
    }
}
