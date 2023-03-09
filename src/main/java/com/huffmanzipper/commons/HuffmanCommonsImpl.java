package com.huffmanzipper.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCommonsImpl implements IHuffmanCommons{
    @Override
    public PriorityQueue<Node> constructMinHeap(Map<String, Integer> freqMapChars) {
        if(freqMapChars.size()==0)
        {
            return null;
        }
        int n= freqMapChars.size();
        PriorityQueue<Node> pq
                = new PriorityQueue<>(n, new MyComparator());
        for (Map.Entry<String,Integer> entry : freqMapChars.entrySet())
        {
            Node node = new Node();
            node.c=entry.getKey().toString();
            node.freq=entry.getValue();
            node.left=null;
            node.right=null;
            pq.add(node);
        }
        return pq;
    }

    @Override
    public Node createHuffmanTree(PriorityQueue<Node> pq) {
        Node root=null;
        if(pq.size()==1)
        {
            return pq.poll();
        }
        while(pq.size()>1)
        {
            Node x=pq.peek();
            pq.poll();
            Node y=pq.peek();
            pq.poll();
            Node newNode = new Node();
            newNode.c="-";
            newNode.freq=x.freq+y.freq;
            newNode.left=x;
            newNode.right=y;
            root =newNode;
            pq.add(newNode);
        }
        return root;
    }

    private Map<String,String> huffCodesRecursive(Node root, String s,Map<String, String> huffCodes)
    {
        if(root==null)return huffCodes;
        if(root.left==null && root.right==null)
        {
            huffCodes.put(root.c,s);
            return huffCodes;
        }
        huffCodesRecursive(root.left, s+"0",huffCodes);
        huffCodesRecursive(root.right, s+"1",huffCodes);
        return huffCodes;
    }
    @Override
    public Map<String, String> getHuffmanCodes(Node root) {
        Map<String, String> huffCodes = new HashMap<>();
        if(root.left==null && root.right==null)
        {

            huffCodes.put(root.c, "0");
            return  huffCodes;
        }
        return huffCodesRecursive(root, "",huffCodes);
    }
}
