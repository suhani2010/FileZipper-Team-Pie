package com.capillary;

import com.filezipper.AlgorithmCreator;
import com.filezipper.FileValidator;
import com.filezipper.IZipperAlgorithm;
import com.huffmanzipper.commons.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException, ClassNotFoundException, SQLException {
        String orgFile="/home/suhaniporwal/Desktop/bigfile.txt";
        String compFile="/home/suhaniporwal/Desktop/compFile.txt";
        String decompFile="/home/suhaniporwal/Desktop/decompFile.txt";

        IZipperAlgorithm zipper = AlgorithmCreator.create();
        long st=System.currentTimeMillis();
        zipper.zip(orgFile,compFile );
        long et=System.currentTimeMillis();
        Logger.getLogger(Main.class.getName()).log(Level.INFO,"Compressed File Generated in "+(et-st)+" ms");
        st=System.currentTimeMillis();
        zipper.unzip(compFile,decompFile );
        et=System.currentTimeMillis();
        Logger.getLogger(Main.class.getName()).log(Level.INFO,"Decompressed File Generated in "+(et-st)+" ms");

        boolean equal = FileValidator.areFilesEqual(orgFile, decompFile);
        if (equal) {
            Utils.displayCompressionStats(orgFile,compFile);
        }
        else {
            Logger.getLogger(Main.class.getName()).log(Level.INFO,"Original and decompressed files are not equal.");
        }
    }
}