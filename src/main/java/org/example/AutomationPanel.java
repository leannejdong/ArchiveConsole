package org.example;

import javax.swing.*;
import java.awt.*;

public class AutomationPanel extends JPanel {
    public AutomationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBorder(BorderFactory.createTitledBorder("Automation Action Request for the Item above:"));

        JButton retrieveButton = new JButton("Retrieve");
        JButton removeButton = new JButton("Remove");
        JButton returnButton = new JButton("Return");
        JButton addToCollectionButton = new JButton("Add to Collection");

        JLabel sortLabel = new JLabel("Sort Section:");
        JTextField sortSectionField = new JTextField(5);

        JButton randomSortButton = new JButton("Random Collection Sort");
        JButton mostlySortedSortButton = new JButton("Mostly Sorted Sort");
        JButton reverseOrderSortButton = new JButton("Reverse Order Sort");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(retrieveButton, gbc);

        gbc.gridx = 1;
        add(removeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(returnButton, gbc);

        gbc.gridx = 1;
        add(addToCollectionButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(sortLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(sortSectionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(randomSortButton, gbc);

        gbc.gridy = 4;
        add(mostlySortedSortButton, gbc);

        gbc.gridy = 5;
        add(reverseOrderSortButton, gbc);
    }
}
