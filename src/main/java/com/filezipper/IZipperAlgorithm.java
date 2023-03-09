package com.filezipper;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface IZipperAlgorithm {
    public void zip(String orgFile, String compFile) throws IOException, ExecutionException, InterruptedException;
    public void unzip(String compFile, String decompFile) throws IOException, ClassNotFoundException;
}
