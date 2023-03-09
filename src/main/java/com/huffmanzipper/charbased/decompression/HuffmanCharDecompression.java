package com.huffmanzipper.charbased.decompression;

import com.huffmanzipper.AbstractDecompressor;
import com.huffmanzipper.commons.Node;

public class HuffmanCharDecompression extends AbstractDecompressor {
    @Override
    protected byte[] decodeFile(byte[] compFileData, Node tree, int orgFileSize) {
        byte[] decodedData=new byte[(int) orgFileSize];
        int curbyte= -1;
        Node root= tree;
        byte b;
        int chars=0;
        int bitcounter=0;
        boolean[] bits = new boolean[8];

        while(curbyte<compFileData.length){
            while(root.left !=null && root.right !=null){
                if(bitcounter==0 || bitcounter==8){
                    b=compFileData[++curbyte];
                    for (int i = 0; i < 8; i++)
                        bits[7 - i] = ((b & (1 << i)) != 0);
                    bitcounter=0;
                }
                if(!bits[bitcounter]) {
                    bitcounter++;
                    root = root.left;
                }
                else{
                    bitcounter++;
                    root = root.right;
                }
            }
            String sb=root.c;
            for(char bb:sb.toCharArray())
            {
                decodedData[chars++]=(byte)bb;
            }
            if(chars==orgFileSize)
                break;
            root= tree;
        }
        return decodedData;
    }
}
