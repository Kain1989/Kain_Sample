package com.telenav.kain;

import java.util.LinkedList;

/**
 * Created on 4/13/2016.
 */
public class JosephCircle {

    private static StringBuffer removedStr = new StringBuffer("");

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        process(20, 1, 3);
        System.out.println(removedStr.substring(0, removedStr.length() - 1));
        long endTime = System.currentTimeMillis();
        System.out.println("Time cost ： " + (endTime - startTime) + "ms");
    }

    public static void process(int n, int k, int m) {

        LinkedList<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < n; i++) {
            if (i + k > n) {
                list.add(i + k - n);
            } else {
                list.add(i + k);
            }
        }
        int count = 1;
        cycleCal(list, count, m);
    }

    public static void cycleCal(LinkedList<Integer> list, int count, int m) {
        int len = list.size();
        if (len > 1) {
            for (int i = 0; i < len; i++) {
                if (count == m) {
                    removedStr.append(list.get(i)).append(",");
                    list.remove(i);
                    len = list.size();
                    i--;
                    count = 0;
                }
                count++;
            }
            cycleCal(list, count, m);
        } else {
            if (len != 0) {
                removedStr.append(list.get(0)).append(",");
            }
        }
    }
}
