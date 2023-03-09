package com.huffmanzipper;

import com.filezipper.FileOperations;
import com.huffmanzipper.commons.HuffmanCommonsImpl;
import com.huffmanzipper.commons.IHuffmanCommons;
import com.huffmanzipper.commons.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class AbstractDecompressor {
    public void decompress(byte[] fileData, Map<String, Integer> freqMap, int orgFileSize, OutputStream fout) throws IOException {
        IHuffmanCommons hc=new HuffmanCommonsImpl();
        PriorityQueue<Node> pq=hc.constructMinHeap(freqMap);
        Node root=hc.createHuffmanTree(pq);
        byte[] decodedData=decodeFile(fileData,root,orgFileSize);
        FileOperations.writeDecompFile(fout, decodedData);
    }

    protected abstract byte[] decodeFile(byte[] fileData,Node root,int orgFileSize);
}
