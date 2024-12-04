package org.example.model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CDTableModel extends AbstractTableModel {
    private List<CD> cds;
    private String[] columnNames = {"Title", "Author", "Genre", "Year", "Track Count", "Duration", "Label", "Favorite"};

    // Constructor to initialize with a file path
    public CDTableModel(String filePath) throws IOException {
        this.cds = loadData(filePath);  // Call DataLoader to load data from the file
    }

    // Constructor to accept an existing List<CD>
    public CDTableModel(List<CD> cds) {
        this.cds = cds;
    }


    @Override
    public int getRowCount() {
        return cds.size();  // Number of rows is the size of the CD list
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;  // Number of columns
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CD cd = cds.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cd.getTitle();
            case 1:
                return cd.getAuthor();
            case 2:
                return cd.getSection();
            case 3:
                return cd.getX();
            case 4:
                return cd.getY();
            case 5:
                return cd.getBarcode();
            case 6:
                return cd.getDescription();
            case 7:
                return cd.isOnLoan() ? "Yes" : "No";
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];  // Return the name of each column
    }


    // Static helper method to load data from a file
    public static List<CD> loadData(String filePath) throws IOException {
        List<CD> cds = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    String title = parts[0].trim();
                    String artist = parts[1].trim();
                    String genre = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    int trackCount = Integer.parseInt(parts[4].trim());
                    String duration = parts[5].trim();
                    String label = parts[6].trim();
                    boolean isFavorite = Boolean.parseBoolean(parts[7].trim());

                    cds.add(new CD(title, artist, genre, year, trackCount, Long.parseLong(duration), label, isFavorite));
                }
            }
        }

        return cds;
    }

    public List<CD> getCds() {
        return cds;
    }

}