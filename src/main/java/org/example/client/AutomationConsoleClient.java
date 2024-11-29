package org.example;

import java.util.List;

public class AutomationConsoleClient {
    private NetworkService networkService;

    public AutomationConsoleClient(String serverUrl) {
        this.networkService = new NetworkService(serverUrl);
    }

    public void loadAndDisplayCDs() {
        try {
            List<CD> cds = networkService.getCDs();
            displayCDs(cds);
        } catch (Exception e) {
            System.out.println("Error loading CDs: " + e.getMessage());
        }
    }

    private void displayCDs(List<CD> cds) {
        for (CD cd : cds) {
            System.out.println(cd);
        }
    }

    public static void main(String[] args) {
        AutomationConsoleClient client = new AutomationConsoleClient("http://localhost:8080");
        client.loadAndDisplayCDs();
    }
}
