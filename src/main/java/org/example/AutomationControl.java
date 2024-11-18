package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutomationControl extends JFrame {
    private ArchiveConsole parentConsole;

    //private JTextArea automationLogArea;

    public AutomationControl(ArchiveConsole parentConsole) {
        setTitle("Automation Control");
        setSize(400,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set layout        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Automation Log Panel
//        automationLogArea = new JTextArea(10,30);
//        automationLogArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(automationLogArea);
//        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel with Buttons
      //  JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton retrieveButton = new JButton("Retrieve");
        retrieveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parentConsole.logAutomation("Retrieve operation");
            }
        });
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parentConsole.logAutomation("Remove operation");
            }
        });
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parentConsole.logAutomation("Return operation");
            }
        });
        JButton addButton = new JButton("Add to collection");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parentConsole.logAutomation("Add to collection operation triggered");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(retrieveButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(addButton);

        add(buttonPanel);



    }

//    private void simulateAction(ActionEvent actionEvent) {
//        automationLogArea.setText("");
//    }
//
//    private void clearLog(ActionEvent actionEvent) {
//        logAutomation("Simulating an automation task ...");
//    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new AutomationControl().setVisible(true));
//    }
}
