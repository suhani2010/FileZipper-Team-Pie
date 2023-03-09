package com.huffmanzipper.wordcharbased;

import com.filezipper.FileOperations;
import com.filezipper.IZipperAlgorithm;
import com.huffmanzipper.AbstractCompressor;
import com.huffmanzipper.AbstractDecompressor;
import com.huffmanzipper.wordcharbased.compression.HuffmanWordPlusCharCompression;
import com.huffmanzipper.wordcharbased.decompression.HuffmanWordPlusCharDecompression;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HuffmanWordPlusCharApproachImpl implements IZipperAlgorithm {
    private final AbstractCompressor huffmanCompressor;
    private final AbstractDecompressor huffmanDecompressor;

    public HuffmanWordPlusCharApproachImpl() {
        this.huffmanCompressor = new HuffmanWordPlusCharCompression();
        this.huffmanDecompressor = new HuffmanWordPlusCharDecompression();
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
