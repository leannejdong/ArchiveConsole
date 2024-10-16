package org.example;


import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CDTableModel extends AbstractTableModel {
    private List<CD> cdList;
    private String[] columnNames = { "Title", "Author", "Section", "X", "Y", "Barcode", "Description", "On Loan" };

    // Constructor to initialize the CD list
    public CDTableModel(List<CD> cdList) {
        this.cdList = cdList;
    }

    // Load new data into the model and notify JTable
    public void loadData(List<CD> newData) {
        this.cdList = newData;
        fireTableDataChanged();  // Notify the JTable that the data has changed
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
