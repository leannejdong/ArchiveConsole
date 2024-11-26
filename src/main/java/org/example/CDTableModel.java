package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class CDTableModel extends AbstractTableModel {
    private List<CD> cdList;

    // Constructor to initialize with a file path
    public CDTableModel(String filePath) throws IOException {
        this.cdList = DataLoader.loadData(filePath);  // Call DataLoader to load data from the file
    }

    // No need to call loadData here as we have already loaded data in the constructor
    public List<CD> getCds() {
        return cdList ;
    }
    private String[] columnNames = { "Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan" };


    // Load new data into the model and notify JTable
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
                    String yearStr = parts[3].trim();
                    String trackCountStr = parts[4].trim();
                    String durationStr = parts[5].trim();
                    String label = parts[6].trim();
                    String isFavoriteStr = parts[7].trim();

                    int year = Integer.parseInt(yearStr);
                    int trackCount = Integer.parseInt(trackCountStr);
                    long duration = Long.parseLong(durationStr);
                    boolean isFavorite = Boolean.parseBoolean(isFavoriteStr);

                    cds.add(new CD(title, artist, genre, year, trackCount, duration, label, isFavorite));
                }
            }
        }

        return cds;
    }

    @Override
    public int getRowCount() {
        return cdList.size();  // Number of rows is the size of the CD list
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;  // Number of columns
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CD cd = cdList.get(rowIndex);
        switch (columnIndex) {
            case 0: return cd.getTitle();
            case 1: return cd.getAuthor();
            case 2: return cd.getSection();
            case 3: return cd.getX();
            case 4: return cd.getY();
            case 5: return cd.getBarcode();
            case 6: return cd.getDescription();
            case 7: return cd.isOnLoan() ? "Yes" : "No";
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];  // Return the name of each column
    }
}
