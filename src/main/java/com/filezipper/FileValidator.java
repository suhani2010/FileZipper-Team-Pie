package com.filezipper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileValidator {
    public static boolean areFilesEqual(String orgFile, String decompFile)
    {
        File aF = new File(orgFile);
        File bF = new File(decompFile);
        Path file1=aF.toPath();
        Path file2=bF.toPath();
        try {
            if (Files.size(file1) != Files.size(file2)) {
                return false;
            }

            byte[] f1 = Files.readAllBytes(file1);
            byte[] f2 = Files.readAllBytes(file2);

            return Arrays.equals(f1, f2);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
