package org.example.utils;

import org.example.CD;
import java.util.List;

public class SortingHelper {
    public static void sortByTitle(List<CD> cds){
        cds.sort((cd1, cd2) -> cd1.getTitle().compareTo(cd2.getTitle()));
    }

    public static void sortByAuthor(List<CD> cds){
        cds.sort((cd1, cd2) -> cd1.getAuthor().compareTo(cd2.getAuthor()));
    }
}
