package org.example.tree;

class Node {
    String barcode;
    String title;
    Node left, right;

    Node(String barcode, String title) {
        this.barcode = barcode;
        this.title = title;
        this.left = null;
        this.right = null;
    }
}