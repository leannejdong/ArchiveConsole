package org.example;


// Class to represent a CD entry
public class CD {
    private String title, author, section, description;
    private long barcode;
    private int x, y;
    private boolean onLoan;

    public CD(String title, String author, String section, int x, int y, long barcode, String description, boolean onLoan) {
        this.title = title;
        this.author = author;
        this.section = section;
        this.x = x;
        this.y = y;
        this.barcode = barcode;
        this.description = description;
        this.onLoan = onLoan;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getSection() { return section; }
    public int getX() { return x; }
    public int getY() { return y; }
    public String getBarcode() { return String.valueOf(barcode); }
    public String getDescription() { return description; }
    public boolean isOnLoan() { return onLoan; }

    @Override
    public String toString() {
        return title + "," + author + "," + section + "," + x + "," + y + "," + barcode + "," + description + "," + onLoan;
    }
}