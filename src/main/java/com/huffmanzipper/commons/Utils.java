package com.huffmanzipper.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Utils {
    public static byte[] serializeMap(Map<String,Integer> freqMap)
    {
        byte[] arr=null;
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(freqMap);
            oos.close();
            arr= baos.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }
        return arr;
    }
    public static String getChecksum(byte[] fileData)  {
        byte[] mdigest;
        try {
            mdigest = MessageDigest.getInstance("MD5").digest(fileData);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
        String checksum=new BigInteger(1, mdigest).toString(16);
        return checksum;
    }

    public static long getFileSize(Map<String,Integer> freqMap,Map<String,String> huffCodes)
    {
        long compressedDataSize=0;
        for(Map.Entry<String,Integer> entry:freqMap.entrySet())
        {
            compressedDataSize+=entry.getValue()*huffCodes.get(entry.getKey()).length();
        }
        return (long)Math.ceil(compressedDataSize/8);
    }
    public static long getMapSize(Map<String,Integer> freqMap)
    {
        try{
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(freqMap);
            oos.close();
            return baos.toByteArray().length;
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }
    public static Map<String, Integer> sortByValue(Map<String, Integer> hm)
    {
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });


        Map<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    public static double calclateAvgSymbolLength(Map<String, String> huffcodes,Map<String, Integer> freqMap)
    {
        int avgSymbolLength=0;
        int totalFreq=0;
        for (Map.Entry<String,Integer> entry : freqMap.entrySet())
        {
//            System.out.println("key ="+huffcodes.get(entry.getKey())+" , value = "+entry.getValue());
            totalFreq+=entry.getValue()*entry.getKey().length();
            int codesLength=huffcodes.get(entry.getKey()).length();
            avgSymbolLength+=(codesLength * entry.getValue());
        }

        return (1.0*avgSymbolLength)/totalFreq;
    }

    public static void displayCompressionStats(String inputFilePath, String compressedFilePath) throws NullPointerException,SecurityException{


        File originalfile = new File(inputFilePath);
        File compressedFile = new File(compressedFilePath);

        if (originalfile.exists() && compressedFile.exists()) {
            System.out.println("Original File Size: " + originalfile.length() + " bytes");
            System.out.println("Compressed File Size: " + compressedFile.length() + " bytes");
            System.out.println("Compression Percentage is "+((double)(originalfile.length()-compressedFile.length())/(double) originalfile.length())*100+" %");
        }

    }
}

