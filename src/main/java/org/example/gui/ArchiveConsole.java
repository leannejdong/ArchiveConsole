package org.example.gui;

import org.example.model.CD;
import org.example.model.CDTableModel;
import org.example.server.ArchiveConsoleServer;
import org.example.server.ProcessLogPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

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

    private JTable table;
    private CDTableModel cdTableModel;
    private DefaultTableModel model;
    private ArrayList<CD> cdList = new ArrayList<>();
    private JTextField titleField, authorField, sectionField, xField, yField, barcodeField, descriptionField;
    private JCheckBox onLoanCheckBox;
    private JTextField searchField;
    private JTextArea logArea;

    private ArchiveConsole parentConsole;
    private ProcessLogPanel processLogPanel;
    /**
     * Constructor for the ArchiveConsole GUI and initializes the server instance.
     */
    public ArchiveConsole() {
        server = new ArchiveConsoleServer();
        //processLogPanel = new ProcessLogPanel(server);

        setTitle("Archive Console - Server GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize table with columns and empty data
        String[] columnNames = {"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan"};
        Object[][] data = {};
        table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);

        // Add table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Enable checkbox rendering and editing for Boolean values
        table.getColumnModel().getColumn(7).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(7).setCellEditor(table.getDefaultEditor(Boolean.class));

        // Table initialization (assumed)

        // Bottom panel for sorting buttons
        JPanel sortPanel = new JPanel(new FlowLayout());

        JButton sortByTitleButton = new JButton("By Title");
        sortByTitleButton.addActionListener(e -> sortByTitle());

        JButton sortByAuthorButton = new JButton("By Author");
        sortByAuthorButton.addActionListener(e -> sortByAuthor());

        JButton sortByBarcodeButton = new JButton("By Barcode");
        sortByBarcodeButton.addActionListener(e -> sortByBarcode());

        sortPanel.add(sortByTitleButton);
        sortPanel.add(sortByAuthorButton);
        sortPanel.add(sortByBarcodeButton);


        add(sortPanel, BorderLayout.SOUTH);

        // Enable checkbox rendering and editing for Boolean values
        table.getColumnModel().getColumn(7).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(7).setCellEditor(table.getDefaultEditor(Boolean.class));
        // initialize components
        initSearchPanel();
        initTable();
        initDetailsPanel();
        initBottomPanel();

        loadDataFromFile("data.txt");
        populateTableWithData();

        processLogPanel = new ProcessLogPanel();
        add(processLogPanel, BorderLayout.SOUTH);

        JButton automationConsoleButton = new JButton("Open Automation Console");
 //       automationConsoleButton.addActionListener(e -> launchAutomationConsole());

        JPanel mainPanel = new JPanel(/*new BorderLayout()*/);
        mainPanel.add(automationConsoleButton);
        add(mainPanel, BorderLayout.CENTER);


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

//    private void launchAutomationConsole(){
//        SwingUtilities.invokeLater(()->{
//            new AutomationControlClientGUI(this).setVisible(true);
//        });
//    }

    private void populateTableWithData() {
      //  model.setRowCount(0);
        for(CD cd : cdList) {
            model.addRow(new Object[]{
                    cd.getTitle(),
                    cd.getAuthor(),
                    cd.getSection(),
                    cd.getX(),
                    cd.getY(),
                    cd.getBarcode(),
                    cd.getDescription(),
                    cd.isOnLoan()
            });
        }
    }

    private void initBottomPanel() {
        JSplitPane bottomPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        ProcessLogPanel processLogPanel = new ProcessLogPanel();
        bottomPanel.setLeftComponent(processLogPanel);

//
//       logArea = new JTextArea(10,30);
//       logArea.setEditable(false);
//       JScrollPane logScrollPane = new JScrollPane(logArea);
//       bottomPanel.setLeftComponent(logScrollPane);

        JPanel automationPanel = createAutomationPanel();
        bottomPanel.setRightComponent(automationPanel);

        bottomPanel.setResizeWeight(0.7);
        add(bottomPanel, BorderLayout.SOUTH);

        // Inside the ArchiveConsole constructor or initialization method

// Create the bottom panel for sorting buttons
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout to arrange buttons neatly
// Add the "Sort" label
        JLabel sortLabel = new JLabel("Sort:");
        sortPanel.add(sortLabel);
        JButton sortByTitleButton = new JButton("By Title");
        sortByTitleButton.addActionListener(e -> sortByTitle());

        JButton sortByAuthorButton = new JButton("By Author");
        sortByAuthorButton.addActionListener(e -> sortByAuthor());

        JButton sortByBarcodeButton = new JButton("By Barcode");
        sortByBarcodeButton.addActionListener(e -> sortByBarcode());

// Add buttons to the sort panel
        sortPanel.add(sortByTitleButton);
        sortPanel.add(sortByAuthorButton);
        sortPanel.add(sortByBarcodeButton);

// Add the sort panel to the bottom of the main frame
        add(sortPanel, BorderLayout.SOUTH);

// Enable checkbox rendering and editing for Boolean values in the table
        table.getColumnModel().getColumn(7).setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumnModel().getColumn(7).setCellEditor(table.getDefaultEditor(Boolean.class));

    }

    private void initDetailsPanel() {
        // Right panel for CD details and buttons
        JPanel detailsPanel = new JPanel(new GridLayout(9, 2));
        titleField = new JTextField();
        authorField = new JTextField();
        sectionField = new JTextField();
        xField = new JTextField();
        yField = new JTextField();
        barcodeField = new JTextField();
        descriptionField = new JTextField();
        onLoanCheckBox = new JCheckBox("On Loan");

        detailsPanel.add(new JLabel("Title:")); detailsPanel.add(titleField);
        detailsPanel.add(new JLabel("Author:")); detailsPanel.add(authorField);
        detailsPanel.add(new JLabel("Section:")); detailsPanel.add(sectionField);
        detailsPanel.add(new JLabel("X:")); detailsPanel.add(xField);
        detailsPanel.add(new JLabel("Y:")); detailsPanel.add(yField);
        detailsPanel.add(new JLabel("Barcode:")); detailsPanel.add(barcodeField);
        detailsPanel.add(new JLabel("Description:")); detailsPanel.add(descriptionField);
        detailsPanel.add(new JLabel("On Loan:")); detailsPanel.add(onLoanCheckBox);

        JButton newItemButton = new JButton("New Item");
        newItemButton.addActionListener(e -> clearFields());

        JButton saveButton = new JButton("Save/Update");
        saveButton.addActionListener(e -> saveCD());

        detailsPanel.add(newItemButton); detailsPanel.add(saveButton);

        add(detailsPanel, BorderLayout.EAST);


    }

    private void initTable() {
        model = new DefaultTableModel(new String[]{"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };

        // Initialize JTable with column headers
        // model = new DefaultTableModel(new String[]{"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan"}, 0);
        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                displayCDDetails(table.getSelectedRow());
            }
        });
        JScrollPane tablePane = new JScrollPane(table);
        add(tablePane, BorderLayout.CENTER);

    }

    private void initSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search String:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchCDs());
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);
    }





    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.LIGHT_GRAY);
        leftPanel.add(new JLabel("Left Panel Content"));
        return leftPanel;
    }

    private JPanel createAutomationPanel() {
        // Creating the panel for automation actions
        JPanel automationPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add labels and buttons as needed in your automation panel
        JLabel label = new JLabel("Automation Action Request for the Item above:");
        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 0;
        automationPanel.add(label, gbc);

        // Row 1 buttons
        JButton retrieveButton = new JButton("Retrieve");
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 1;
        automationPanel.add(retrieveButton, gbc);

        JButton removeButton = new JButton("Remove");
        gbc.gridx = 1; gbc.gridy = 1;
        automationPanel.add(removeButton, gbc);

        // Row 2 buttons
        JButton returnButton = new JButton("Return");
        gbc.gridx = 0; gbc.gridy = 2;
        automationPanel.add(returnButton, gbc);

        JButton addButton = new JButton("Add to Collection");
        gbc.gridx = 1; gbc.gridy = 2;
        automationPanel.add(addButton, gbc);

        // Sort Section label and text field
        JLabel sortLabel = new JLabel("Sort Section:");
        gbc.gridx = 0; gbc.gridy = 3;
        automationPanel.add(sortLabel, gbc);

        JTextField sortField = new JTextField(5);
        gbc.gridx = 1; gbc.gridy = 3;
        automationPanel.add(sortField, gbc);

        // Sort buttons
        JButton randomSortButton = new JButton("Random Collection Sort");
        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 4;
        automationPanel.add(randomSortButton, gbc);

        JButton mostlySortedSortButton = new JButton("Mostly Sorted Sort");
        gbc.gridy = 5;
        automationPanel.add(mostlySortedSortButton, gbc);

        JButton reverseOrderSortButton = new JButton("Reverse Order Sort");
        gbc.gridy = 6;
        automationPanel.add(reverseOrderSortButton, gbc);

        return automationPanel;
    }


    private GridBagConstraints createGbc(int x, int y, int width){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x; gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }


    private void searchCDs() {
        String searchText = searchField.getText().toLowerCase();  // Get the text entered in the search box and convert it to lowercase
        model.setRowCount(0);  // Clear the table before displaying the search results

        // Loop through the cdList and add rows that match the search criteria to the table
        for (CD cd : cdList) {
            // Check if the search text is contained in the title, author, section, or description (case-insensitive)
            if (cd.getTitle().toLowerCase().contains(searchText) ||
                    cd.getAuthor().toLowerCase().contains(searchText) ||
                    cd.getSection().toLowerCase().contains(searchText) ||
                    cd.getDescription().toLowerCase().contains(searchText)) {

                // Add the matching CD details to the table
                model.addRow(new Object[]{
                        cd.getTitle(),
                        cd.getAuthor(),
                        cd.getSection(),
                        cd.getX(),
                        cd.getY(),
                        cd.getBarcode(),
                        cd.getDescription(),
                        cd.isOnLoan()
                });
            }
        }
    }


//    private void loadDataFromFile(String fileName) {
//        cdList.clear();  // Clear the existing list before loading new data
//        // Read from the file and populate the cdList
//        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            reader.readLine();  // Skip the header line
//            while ((line = reader.readLine()) != null) {
//                if (line.trim().isEmpty()) continue;
//
//                // Use a more robust CSV parsing method (for comma-separated values)
//                String[] data = line.split(";", -1);  // Handles commas inside quotes
//
//                if (data.length == 9) {  // Ensure we have exactly 9 fields
//                    String id = data[0];
//                    String title = data[1];
//                    String author = data[2];
//                    String section = data[3];
//                    int x = Integer.parseInt(data[4]);
//                    int y = Integer.parseInt(data[5]);
//                    long barcode = Long.parseLong(data[6]);
//                    String description = data[7];
//                    boolean onLoan = data[8].equalsIgnoreCase("true") || data[7].equalsIgnoreCase("yes");
//
//                    // Create a CD object and add it to the list
//                    CD cd = new CD(title, author, section, x, y, barcode, description, onLoan);
//                    cdList.add(cd);
//                    //model.addRow(new Object[]{title, author, section, x, y, barcode, description, onLoan});
//                    addCDToTable(cd);
//                    System.out.println("CD added: " + cd);
//                } else {
//                    System.out.println("Invalid data: " + line);  // Log invalid lines for debugging
//                }
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error loading data from file: " + e.getMessage(),
//                    "File Load Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private void loadDataFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            cdList.clear(); // Clear existing data
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(","); // Assuming comma-separated values
                if (fields.length == 8) { // Ensure all fields are present
                    CD cd = new CD(
                            fields[0].trim(), // Title
                            fields[1].trim(), // Author
                            fields[2].trim(), // Section
                            Integer.parseInt(fields[3].trim()), // X
                            Integer.parseInt(fields[4].trim()), // Y
                            Long.parseLong(fields[5].trim()), // Barcode
                            fields[6].trim(), // Description
                            Boolean.parseBoolean(fields[7].trim()) // On Loan
                    );
                    cdList.add(cd);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading data file: " + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addCDToTable(CD cd){
        model.addRow(new Object[]{
                cd.getTitle(), cd.getAuthor(), cd.getSection(), cd.getX(), cd.getY(), cd.getBarcode(), cd.getDescription(),cd.isOnLoan()
        });
    }



    private void displayCDDetails(int index) {
        if(index < 0 || index >= cdList.size()) return;
        // Display selected CD's details in text fields
        CD cd = cdList.get(index);
        titleField.setText(cd.getTitle());
        authorField.setText(cd.getAuthor());
        sectionField.setText(cd.getSection());
        xField.setText(String.valueOf(cd.getX()));
        yField.setText(String.valueOf(cd.getY()));
        barcodeField.setText(cd.getBarcode());
        descriptionField.setText(cd.getDescription());
        onLoanCheckBox.setSelected(cd.isOnLoan());
    }

    private void clearFields() {
        // Clear all input fields for new item
        titleField.setText("");
        authorField.setText("");
        sectionField.setText("");
        xField.setText("");
        yField.setText("");
        barcodeField.setText("");
        descriptionField.setText("");
        onLoanCheckBox.setSelected(false);
    }

    private void saveCD() {
        // Add new CD or update existing one
        String title = titleField.getText();
        String author = authorField.getText();
        String section = sectionField.getText();
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());
        long barcode = Long.parseLong(barcodeField.getText());
        String description = descriptionField.getText();
        boolean onLoan = onLoanCheckBox.isSelected();

        CD cd = new CD(title, author, section, x, y, barcode, description, onLoan);
        cdList.add(cd);

        model.addRow(new Object[]{title, author, section, x, y, barcode, description, onLoan});

        saveDataToFile("data_output.txt");
    }

    private void saveDataToFile(String fileName) {
        // Save all CDs to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (CD cd : cdList) {
                writer.write(cd.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortByTitle() {
        cdList.sort(Comparator.comparing(CD::getTitle));
        refreshTable();
    }

    private void sortByAuthor() {
        cdList.sort(Comparator.comparing(CD::getAuthor));
        refreshTable();
    }

    private void sortByBarcode() {
        cdList.sort(Comparator.comparing(CD::getBarcode));
        refreshTable();
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (CD cd : cdList) {
            model.addRow(new Object[]{cd.getTitle(), cd.getAuthor(), cd.getSection(), cd.getX(), cd.getY(), cd.getBarcode(), cd.getDescription(), cd.isOnLoan()});
        }
    }


    public static void main(String[] args) {

        ArchiveConsole archiveConsole = new ArchiveConsole();
        archiveConsole.setVisible(true);

    }
}