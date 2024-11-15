package org.example.tree;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private Node root;

    // Insert method to add a CD to the tree
    public void insert(String barcode, String title) {
        root = insertRecursive(root, barcode, title);
    }

    private Node insertRecursive(Node node, String barcode, String title) {
        if (node == null) {
            return new Node(barcode, title);
        }
        if (barcode.compareTo(node.barcode) < 0) {
            node.left = insertRecursive(node.left, barcode, title);
        } else {
            node.right = insertRecursive(node.right, barcode, title);
        }
        return node;
    }

    // Traversal methods

    public String preOrderTraversal(){
        return preOrderHelper(root).trim();
    }

    public String inOrderTraversal(){
        return inOrderHelper(root).trim();
    }
    // Pre-order traversal


    private String preOrderHelper(Node node) {
        if (node == null) return "";
        return node.barcode + " " + node.title + ","
                + preOrderHelper(node.left) + preOrderHelper(node.right);
    }

    private String inOrderHelper(Node node) {
        if (node == null) return "";
        return inOrderHelper(node.left)+ node.barcode + ""
                + node.title + "," + inOrderHelper(node.right);
    }

    private String postOrderHelper(Node node) {
        if (node == null) return "";
        return postOrderHelper(node.left) +
                postOrderHelper(node.right) + node.barcode + "" +node.title + ",";
    }

    // In-order traversal
    public List<String> inOrder() {
        List<String> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }

    private void inOrderHelper(Node node, List<String> result) {
        if (node != null) {
            inOrderHelper(node.left, result);
            result.add("Barcode: " + node.barcode + ", Title: " + node.title);
            inOrderHelper(node.right, result);
        }
    }

    // Post-order traversal
    public List<String> postOrder() {
        List<String> result = new ArrayList<>();
        postOrderHelper(root, result);
        return result;
    }

    private void postOrderHelper(Node node, List<String> result) {
        if (node != null) {
            postOrderHelper(node.left, result);
            postOrderHelper(node.right, result);
            result.add("Barcode: " + node.barcode + ", Title: " + node.title);
        }
    }

    // Draw tree method to show a graphical representation of the binary tree
    public void drawTree() {
        if (root == null) {
            JOptionPane.showMessageDialog(null, "Tree is empty.");
            return;
        }

        JFrame frame = new JFrame("Binary Tree Graphical Representation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(new TreePanel(root));
        frame.setVisible(true);
    }

    public String postOrderTraversal() {
        return postOrderHelper(root).trim();
    }

    // Inner class for drawing the tree on a JPanel
    private static class TreePanel extends JPanel {
        private final Node root;

        public TreePanel(Node root) {
            this.root = root;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawNode(g, root, getWidth() / 2, 30, getWidth() / 4);
        }

        private void drawNode(Graphics g, Node node, int x, int y, int xOffset) {
            if (node == null) {
                return;
            }

            // Draw the node with a limited size and position range
            int nodeRadius = 20;
            g.setColor(Color.BLUE);
            g.fillOval(x - nodeRadius / 2, y - nodeRadius / 2, nodeRadius, nodeRadius);
            g.setColor(Color.WHITE);
            g.drawString(node.barcode, x - 10, y + 5);

            int yOffset = 60;  // Adjust vertical distance between levels

            // Draw left child if exists
            if (node.left != null) {
                g.drawLine(x, y, x - xOffset, y + yOffset);
                drawNode(g, node.left, x - xOffset, y + yOffset, xOffset / 2);
            }

            // Draw right child if exists
            if (node.right != null) {
                g.drawLine(x, y, x + xOffset, y + yOffset);
                drawNode(g, node.right, x + xOffset, y + yOffset, xOffset / 2);
            }
        }

    }
}
