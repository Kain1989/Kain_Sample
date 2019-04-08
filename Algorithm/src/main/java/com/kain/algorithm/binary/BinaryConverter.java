package com.kain.algorithm.binary;

/**
 * Created on 4/27/2017.
 */
public class BinaryConverter {

    public static void main(String args[]) {
        System.out.println(convert(9));
    }

    public static String convert(int input) {
        StringBuilder sb = new StringBuilder();
        sb.append(input % 2);
        if ((input / 2) < 1) {
            return "";
        } else {
            sb.append(convert(input / 2));
        }
        return sb.toString();
    }
}
