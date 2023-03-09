package com.huffmanzipper.charbased;

import com.filezipper.FileOperations;
import com.filezipper.IZipperAlgorithm;
import com.huffmanzipper.AbstractCompressor;
import com.huffmanzipper.AbstractDecompressor;
import com.huffmanzipper.charbased.compression.HuffmanCharCompression;
import com.huffmanzipper.charbased.decompression.HuffmanCharDecompression;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HuffmanCharApproachImpl implements IZipperAlgorithm {
    private final AbstractCompressor huffmanCompressor;
    private final AbstractDecompressor huffmanDecompressor;

    public HuffmanCharApproachImpl() {
        this.huffmanCompressor = new HuffmanCharCompression();
        this.huffmanDecompressor = new HuffmanCharDecompression();
    }

    @Override
    public void zip(String orgFile, String compFile) throws IOException, ExecutionException, InterruptedException {
        InputStream fin = new FileInputStream(orgFile);
        OutputStream fout = new FileOutputStream(compFile);
        byte[] fileData = FileOperations.readOrgFileData(fin);
        huffmanCompressor.compress(fileData,fout);
    }

    @Override
    public void unzip(String compFile, String decompFile) throws IOException, ClassNotFoundException {
        InputStream fin=new FileInputStream(compFile);
        OutputStream fout=new FileOutputStream(decompFile);
        int orgFileSize= FileOperations.readOrgFileSize(fin);
        Map<String, Integer> freqMap=FileOperations.readFreqMap(fin);
        byte[] fileData=FileOperations.readCompFileData(fin);
        huffmanDecompressor.decompress(fileData,freqMap,orgFileSize,fout);
    }
}
