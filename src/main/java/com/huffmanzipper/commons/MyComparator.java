
package com.huffmanzipper.commons;

import java.util.Comparator;

public class MyComparator implements Comparator<Node>{
    @Override
    public int compare(Node x, Node y)
    {
        return x.freq - y.freq;
    }
}

