package com.kain.algorithm.search;

/**
 * Created on 12/29/2016.
 */
public class MissingNumber {

    public static void main(String args[]) {

        long start = System.nanoTime();
        long sum = 0;
        for (long i = 1; i <= 1000000000; i++) {
            sum += i;
        }
        long end = System.nanoTime();
        System.out.println("Sum cost : " + ((end - start) / 1000000.0));

        boolean array[] = new boolean[1000000000];
        start = System.nanoTime();
        for (int i = 0; i < 1000000000; i++) {
            array[i] = true;
        }
        end = System.nanoTime();
        System.out.println("Travling data array cost : " + ((end - start) / 1000000.0));
        start = System.nanoTime();
        for (int i = 0; i < 1000000000; i++) {
            if (!array[i]) {
                break;
            }
        }
        end = System.nanoTime();
        System.out.println("Travling boolean array cost : " + ((end - start) / 1000000.0));
    }
}
