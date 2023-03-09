package com.filezipper;

import java.io.*;
import java.util.Map;

public class FileOperations {
    static ObjectInputStream obj;
    
    public static byte[] readOrgFileData(InputStream fin) throws IOException {
        byte[] fileData=new byte[fin.available()];
        fin.read(fileData);
        return fileData;
    }
    
    public static void writeCompFile(OutputStream fout, Map<String, Integer> freqMap, byte[] compressedData, int orgFileSize) throws IOException {

        ObjectOutputStream obj=new ObjectOutputStream(fout);
        obj.writeInt(orgFileSize);
        obj.writeObject(freqMap);
        obj.writeObject(compressedData);
    }
    
    public static void writeDecompFile(OutputStream fout, byte[] data)throws IOException{
        fout.write(data);
    }

    
    public static int readOrgFileSize(InputStream fin) throws IOException {
        obj=new ObjectInputStream(fin);
        int orgFileSize=obj.readInt();
        return orgFileSize;
    }
    
    public static Map<String, Integer> readFreqMap(InputStream fin) throws IOException, ClassNotFoundException {
        return (Map<String , Integer>)obj.readObject();
    }

    
    public Map<String, String> readHuffmanMap(InputStream fin) throws IOException, ClassNotFoundException {
        return (Map<String , String>)obj.readObject();
    }

    
    public static byte[] readCompFileData(InputStream fin) throws IOException, ClassNotFoundException {
        return (byte[] )obj.readObject();
    }
}
