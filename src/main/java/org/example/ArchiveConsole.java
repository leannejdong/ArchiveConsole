package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
public class ArchiveConsole extends JFrame {
    private JTable table;

    private CDTableModel cdTableModel;
    private DefaultTableModel model;
    private ArrayList<CD> cdList = new ArrayList<>();
    private JTextField titleField, authorField, sectionField, xField, yField, barcodeField, descriptionField;
    private JCheckBox onLoanCheckBox;

    private JTextField searchField;



    public ArchiveConsole() {
        setTitle("Archive Console");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel for search functionality
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search String:"));
        searchField = new JTextField(20);  // Search box
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchCDs());
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Initialize JTable with column headers and set Boolean type for "On Loan" to display as checkbox
        model = new DefaultTableModel(new String[]{"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Boolean.class;
                return super.getColumnClass(columnIndex);
            }
        };

        // Initialize JTable with column headers
        model = new DefaultTableModel(new String[]{"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan"}, 0);
        table = new JTable(model);
        JScrollPane tablePane = new JScrollPane(table);
        add(tablePane, BorderLayout.CENTER);

        // Load data from file (create a sample file manually if not exists)
        loadDataFromFile("data.txt");

        // Table listener to display CD details when clicked
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                displayCDDetails(selectedRow);
            }
        });


//        // Initialize the table model with the CD list
//        cdTableModel = new CDTableModel(cdList);
//        table = new JTable(cdTableModel);

        // Table listener to display CD details when clicked
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                displayCDDetails(selectedRow);
            }
        });

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

        // Bottom panel for sorting buttons with "Sort" text
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel sortLabel = new JLabel("Sort:");
        sortPanel.add(sortLabel);

        // Bottom panel for sorting buttons
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


    private void loadDataFromFile(String fileName) {
        // Read from the file and populate the cdList
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine();  // Skip the header line
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Use a more robust CSV parsing method (for comma-separated values)
                String[] data = line.split(";", -1);  // Handles commas inside quotes

                if (data.length == 9) {  // Ensure we have exactly 9 fields
                    String id = data[0];
                    String title = data[1];
                    String author = data[2];
                    String section = data[3];
                    int x = Integer.parseInt(data[4]);
                    int y = Integer.parseInt(data[5]);
                    long barcode = Long.parseLong(data[6]);
                    String description = data[7];
                    boolean onLoan = data[8].equalsIgnoreCase("true") || data[7].equalsIgnoreCase("yes");

                    // Create a CD object and add it to the list
                    CD cd = new CD(title, author, section, x, y, barcode, description, onLoan);
                    cdList.add(cd);
                    model.addRow(new Object[]{title, author, section, x, y, barcode, description, onLoan});

                    System.out.println("CD added: " + cd);
                } else {
                    System.out.println("Invalid data: " + line);  // Log invalid lines for debugging
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void displayCDDetails(int index) {
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

        saveDataToFile("CD_ArchivePrototype_SampleData.txt");
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
        SwingUtilities.invokeLater(() -> new ArchiveConsole().setVisible(true));
    }
}



// CDTableModel class to handle table data


