package com.kain.algorithm.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2/9/2017.
 */
public class Runner {

    public static void main(String args[]) {
        System.out.println(getValue(100));
        System.out.println(getValueNoStorage(100));
    }

    public static int getValueNoStorage(int input) {
        int size = 0;
        int round = 1;
        OUTER : while(size < input) {
            for (int i = 1; i <= round; i++) {
                String value = String.valueOf(i);
                for (char ch : value.toCharArray()) {
                    size ++;
                    if (size >= input) {
                        return Integer.valueOf(String.valueOf(ch));
                    }
                }
            }
        }
        return 0;
    }

    public static int getValue(int input) {
        List<Integer> list = new ArrayList<>();
        int size = 0;
        int round = 1;
        OUTER : while(size < input) {
            for (int i = 1; i <= round; i++) {
                String value = String.valueOf(i);
                for (char ch : value.toCharArray()) {
                    list.add(Integer.valueOf(String.valueOf(ch)));
                    if (list.size() >= input) {
                        break OUTER;
                    }
                }
            }
            size = list.size();
            round++;
        }
        System.out.println(list);
        return list.get(input - 1);
    }
}
