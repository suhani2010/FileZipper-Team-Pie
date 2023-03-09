package com.huffmanzipper.charbased.compression;

import com.huffmanzipper.AbstractCompressor;
import com.huffmanzipper.commons.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanCharCompression extends AbstractCompressor {
    @Override
    protected Map<String, Integer> getFreqMap(byte[] fileData) {
        if(fileData.length == 0)
        {
            return null;
        }
        Map<String, Integer> freqMap = new HashMap<>();
        for(byte x:fileData)
        {
            String key=String.valueOf((char)x);
            Integer val=freqMap.get(key);
            if(val == null)
            {
                freqMap.put(key,1);
            }
            else{
                freqMap.put(key,val+1);
            }
        }
        return freqMap;
    }


    @Override
    protected byte[] compressedDataGeneration(byte[] fileData, Map<String, String> huffCodes) {
        String s="";
        String curr="";
        List<Byte> list=new ArrayList<>();

        for (byte x : fileData) {

            s += huffCodes.get(String.valueOf((char)x));

            while(s.length()>=8){
                curr=s.substring(0,8);
                s=s.substring(8);
                byte b = (byte) Integer.parseInt(curr, 2);
                list.add(b);
            }
        }

        if(s.length()>0){
            curr=String.format("%1$-" + 8 + "s", s).replace(' ', '0');
            byte b = (byte) Integer.parseInt(curr, 2);
            list.add(b);
        }
        byte[] byteHuffcodes=new byte[list.size()];
        int i=0;
        for(Byte x:list){
            byteHuffcodes[i++]=x.byteValue();
        }
        return byteHuffcodes;
    }
}
