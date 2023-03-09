package com.filezipper;

import com.huffmanzipper.charbased.HuffmanCharApproachImpl;
import com.huffmanzipper.wordcharbased.HuffmanWordPlusCharApproachImpl;

import java.util.Scanner;

public class AlgorithmCreator {

    public static IZipperAlgorithm create(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Choice:\n1-For character based algo\n2-For word + character based algo");
        String choice = sc.nextLine();

        if (choice.equals("2"))
            return new HuffmanWordPlusCharApproachImpl();
        else
            return new HuffmanCharApproachImpl();
    }
}
