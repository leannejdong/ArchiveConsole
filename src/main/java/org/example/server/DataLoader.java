package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    /**
     * Load data from the given file path.
     *
     * @param filePath the path to the data file.
     * @return a list of CD objects.
     * @throws IOException if an error occurs while reading the file.
     */
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

                    // Convert strings to appropriate data types
                    int year = Integer.parseInt(yearStr); // Convert year to int
                    int trackCount = Integer.parseInt(trackCountStr); // Convert track count to int
                    long duration = Long.parseLong(durationStr); // Convert duration to long
                    boolean isFavorite = Boolean.parseBoolean(isFavoriteStr); // Convert isFavorite to boolean
          //          int year = Integer.parseInt(parts[2].trim());
                    cds.add(new CD(title, artist, genre, year, trackCount, duration, label, isFavorite));                }
            }
        }

        return cds;
    }
}
