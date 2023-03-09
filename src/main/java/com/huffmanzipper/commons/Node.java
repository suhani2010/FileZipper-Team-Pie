
package com.huffmanzipper.commons;

public class Node {
    public int freq;
    public String c;

    public Node left;
    public Node right;

//    public Node(int freq, byte c, Node left, Node right) {
//        this.freq = freq;
//        this.c = c;
//        this.left = left;
//        this.right = right;
//    }

    public Node() {
    }

    public Node(String c, int freq) {
        this.freq = freq;
        this.c = c;
    }

    public Node(String c) {
        this.c = c;
    }
}
