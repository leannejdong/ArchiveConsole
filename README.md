# Archive Console

The Automation Console acts as a client that 
connects to the Archive Console server. 
Here’s how the complete system integrates the 
Automation Console with your existing architecture:

```angular2html
project-root/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── Main.java                 # Entry point for the Archive Console server
│   │   │           ├── SignalHandlerUtility.java # Handles OS signals
│   │   │           ├── ArchiveServer.java        # Server for IPC and multithreading
│   │   │           ├── AutomationConsoleClient.java # Client for Automation Console
│   │   │           ├── CD.java                  # Class representing a CD
│   │   │           ├── ProcessLogPanel.java      # Manages the action logs
│   │   │           ├── CDTableModel.java         # Manages CD table data
│   │   │           ├── Request.java              # Encapsulates requests sent between client and server
│   │   │           ├── Response.java             # Encapsulates responses sent between client and server
│   ├── resources/
│   │   ├── cds.csv
│   │   ├── application.properties
│   ├── test/
│       ├── java/
│       │   └── org/
│       │       └── example/
│       │           ├── ArchiveServerTest.java
│       │           ├── AutomationConsoleClientTest.java
│       │           ├── SignalHandlerTest.java
│       │           ├── CDTest.java
│       │           ├── ProcessLogPanelTest.java
│       ├── resources/
│           └── test-data.csv

```
