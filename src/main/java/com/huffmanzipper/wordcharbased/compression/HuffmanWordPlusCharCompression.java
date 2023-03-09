package com.huffmanzipper.wordcharbased.compression;

import com.huffmanzipper.AbstractCompressor;
import com.huffmanzipper.commons.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HuffmanWordPlusCharCompression extends AbstractCompressor {
    private List<String> convertByteArrayToListOfWords(byte[] fileData) {

        if (fileData.length == 0)
        {
            return null;
        }
        List<String> ls=new ArrayList<>();
        String word="";
        for (byte x: fileData)
        {
            char ch=(char)(x);
            if(((ch>='a' && ch<='z') || (ch>='A' && ch<='Z') || (ch>='0' && ch<='9')))
            {
                word+=ch;
            }
            else if (ch!=' ')
            {
                if( !word.equals(""))
                {
                    ls.add(word);
                    word="";
                }

                ls.add(ch+"");
            }
            else{
                if(word!="")
                    ls.add(word);
                if(ch == ' ')
                    ls.add(" ");
                word="";
            }
        }

        return ls;
    }
    private Map<String,Integer> getFreqMapWords(List<String > fileData) {
        if(fileData.size() == 0)
        {
            return null;
        }
        Map<String, Integer> freqMapWords = new HashMap<>();
        for(String word:fileData)
        {
            Integer val=freqMapWords.get(word);
            if(val == null)
            {
                freqMapWords.put(word,1);
            }
            else{
                freqMapWords.put(word,val+1);
            }
        }
        freqMapWords= Utils.sortByValue(freqMapWords);
        return freqMapWords;
    }

    private List<String > getUniqueWordList(Map<String ,Integer> freqMapWords)
    {
        List<String > uniqueWords=new ArrayList<>();
        for(Map.Entry<String,Integer> entry:freqMapWords.entrySet())
        {
            uniqueWords.add(entry.getKey());
        }
        return uniqueWords;
    }
    @Override
    protected Map<String, Integer> getFreqMap(byte[] fileData) throws ExecutionException, InterruptedException {
        List<String> words=convertByteArrayToListOfWords(fileData);
        Map<String,Integer> freqMapWords=getFreqMapWords(words);
        List<String > uniqueWords=getUniqueWordList(freqMapWords);
        ExecutorService service =  Executors.newFixedThreadPool(4);
        OptimalPercentageTask[] task=new OptimalPercentageTask[4];
        List<Future<Integer>> list=new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            task[i]=new OptimalPercentageTask(uniqueWords,i,freqMapWords);
            list.add(service.submit(task[i]));
        }
        int bestSize=Integer.MAX_VALUE;
        Map<String ,Integer> bestMap=null;
        for(int i=0;i<4;i++)
        {
            int size=list.get(i).get();
            if (size < bestSize) {
                bestSize = size;
                bestMap=task[i].bestMap;
            }
        }
        service.shutdownNow();
        return bestMap;
    }


    @Override
    protected byte[] compressedDataGeneration(byte[] fileData, Map<String, String> huffCodes) {
        String s="";
        String curr="";
        List<Byte> list=new ArrayList<>();
        List<String> words=convertByteArrayToListOfWords(fileData);
        for(String word: words){
            if(huffCodes.containsKey(word))
            {
                s+=huffCodes.get(word);
            }
            else{
                for(int i=0;i<word.length();i++)
                {
                    s+=huffCodes.get(word.charAt(i)+"");
                }
            }
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
        byte[] byteHuffCodes=new byte[list.size()];
        int i=0;
        for(Byte x:list)
        {
            byteHuffCodes[i++]= x.byteValue();
        }
        return byteHuffCodes;
    }
}
