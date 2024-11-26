package org.example;

import java.util.List;
import java.io.IOException;

public class NetworkService {
    private String serverUrl;

    public NetworkService(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public List<CD> getCDs() throws IOException {
        // Simulate server communication
        System.out.println("Fetching CDs from server at: " + serverUrl);
        return CDTableModel.loadData("data.txt"); // Replace with actual server call
    }

    public List<CD> sortCDs(String algorithm) throws IOException{
        // Simulate server communication
        System.out.println("Requesting sort on server: " + algorithm);
        return CDTableModel.loadData("data.txt"); // Replace with actual server call
    }
}
