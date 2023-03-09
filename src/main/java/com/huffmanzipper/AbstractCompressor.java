package com.huffmanzipper;

import com.filezipper.FileOperations;
import com.huffmanzipper.commons.HuffmanCommonsImpl;
import com.huffmanzipper.commons.IHuffmanCommons;
import com.huffmanzipper.commons.Node;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

public abstract class AbstractCompressor {
    public final void compress(byte[] fileData, OutputStream fout) throws IOException, ExecutionException, InterruptedException, SQLException {

        Map<String ,Integer> freqMap=getFreqMap(fileData);
        IHuffmanCommons hc=new HuffmanCommonsImpl();
        PriorityQueue<Node> pq=hc.constructMinHeap(freqMap);
        Node root=hc.createHuffmanTree(pq);
        Map<String, String> huffCodes = hc.getHuffmanCodes(root);
//        double avgSymbolLength=Utils.calclateAvgSymbolLength(huffCodes,freqMap);
//        System.out.println("Avg Symbol Length = "+ avgSymbolLength);
        byte[] compressedData=compressedDataGeneration(fileData,huffCodes);
        FileOperations.writeCompFile(fout,freqMap,compressedData,fileData.length);
    }

    protected abstract Map<String ,Integer> getFreqMap(byte[] fileData) throws ExecutionException, InterruptedException, SQLException;
    protected abstract byte[] compressedDataGeneration(byte[] fileData,Map<String, String> huffCodes);

}
