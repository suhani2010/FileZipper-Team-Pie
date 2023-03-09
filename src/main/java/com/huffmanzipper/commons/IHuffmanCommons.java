package com.huffmanzipper.commons;

import java.util.Map;
import java.util.PriorityQueue;

public interface IHuffmanCommons {
    public Node createHuffmanTree(PriorityQueue<Node> pq);
    public PriorityQueue<Node> constructMinHeap(Map<String, Integer> freqMap);
    public Map<String, String> getHuffmanCodes(Node root);
}
