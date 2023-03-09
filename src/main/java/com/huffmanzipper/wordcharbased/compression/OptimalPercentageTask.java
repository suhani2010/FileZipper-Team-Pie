package com.huffmanzipper.wordcharbased.compression;

import com.huffmanzipper.commons.HuffmanCommonsImpl;
import com.huffmanzipper.commons.IHuffmanCommons;
import com.huffmanzipper.commons.Node;
import com.huffmanzipper.commons.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;

public class OptimalPercentageTask implements Callable<Integer> {
    List<String> uniqueWords;
    int x;
    public int bestPercent;
    public Map<String ,Integer> bestMap;
    Map<String ,Integer> freqMapWords;
    long bestSize = Integer.MAX_VALUE;
    public OptimalPercentageTask(List<String> uniqueWords, int x, Map<String ,Integer> freqMapWords) {
        this.uniqueWords=uniqueWords;
        this.x=x;
        this.freqMapWords=freqMapWords;
    }
    IHuffmanCommons hco = new HuffmanCommonsImpl();
    Map<String, Integer> ithfreqMap;
    PriorityQueue<Node> pq;
    Map<String, String> huffCodes;
    long fileSize;
    long mapSize;
    Node root;
    @Override
    public Integer call() throws Exception {
        int start=25*(x-1);
        int end=start+25;

        for(int i=start;i<=end;i+=10) {
            ithfreqMap = getFreqMap(uniqueWords, i, freqMapWords);
            pq = hco.constructMinHeap(ithfreqMap);
            root = hco.createHuffmanTree(pq);
            huffCodes = hco.getHuffmanCodes(root);
             fileSize = Utils.getFileSize(ithfreqMap, huffCodes);
             mapSize = Utils.getMapSize(ithfreqMap);
            if (fileSize + mapSize < bestSize) {
                bestSize = fileSize + mapSize;
                bestPercent = i;
                bestMap= ithfreqMap;
            }
        }
            return (int)bestSize;
    }

    public Map<String, Integer> getFreqMap(List<String> uniqueWords, int percent, Map<String, Integer> freqMapWords) {
        int x= freqMapWords.size();
        x=(x*percent)/100;
        Map<String, Integer> freqMap = new HashMap<>();
        for (Map.Entry<String,Integer> entry : freqMapWords.entrySet()) {
            if (x == 0) break;

            if(!freqMap.containsKey(entry.getKey()))
            {
                x--;
                freqMap.put(entry.getKey(),entry.getValue());
            }
        }
        for(String word:uniqueWords)
        {
            if(!freqMap.containsKey(word))
            {
                int count=freqMapWords.get(word);
                for(int i=0;i<word.length();i++) {
                    String ch=word.charAt(i)+"";
                    Integer val = freqMap.get(ch);
                    if (val == null) {
                        freqMap.put(ch, count);
                    } else {
                        freqMap.put(ch, val + count);
                    }
                }
            }
        }
        return freqMap;
    }
}
