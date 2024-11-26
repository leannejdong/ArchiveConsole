package org.example;

import java.util.Collections;
import java.util.List;

public class SortingAlgorithms {
    public static void sort(List<CD> cds, String algorithm) {
        if ("title".equalsIgnoreCase(algorithm)) {
            Collections.sort(cds, (cd1, cd2) -> cd1.getTitle().compareTo(cd2.getTitle()));
        } else if ("artist".equalsIgnoreCase(algorithm)) {
            Collections.sort(cds, (cd1, cd2) -> cd1.getAuthor().compareTo(cd2.getAuthor()));
        } else {
            System.out.println("Unknown sorting algorithm: " + algorithm);
        }
    }
}
