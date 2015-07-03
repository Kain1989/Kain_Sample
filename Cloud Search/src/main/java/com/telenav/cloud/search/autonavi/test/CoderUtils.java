package com.telenav.cloud.search.test;

import java.io.IOException;

/**
 * Created by zfshi on 6/5/2015.
 */
public class CoderUtils {

    public static char ascii2Char(int ASCII) {
        return (char) ASCII;
    }

    public static int char2ASCII(char c) {
        return (int) c;
    }

    public static String ascii2String(int[] ASCIIs) {
        StringBuilder sb = new StringBuilder();
        for (int ASCII : ASCIIs) {
            sb.append(ascii2Char(ASCII));
        }
        return sb.toString();
    }

    public static String ascii2String(String ASCIIs) {
        String[] ASCIIss = ASCIIs.split(",");
        StringBuilder sb = new StringBuilder();
        for (String ASCII : ASCIIss) {
            sb.append(ascii2Char(Integer.parseInt(ASCII)));
        }
        return sb.toString();
    }

    public static int[] string2ASCII(String s) {// 字符串转换为ASCII码
        if (s == null || "".equals(s)) {
            return null;
        }

        char[] chars = s.toCharArray();
        int[] asciiArray = new int[chars.length];

        for (int i = 0; i < chars.length; i++) {
            asciiArray[i] = char2ASCII(chars[i]);
        }
        return asciiArray;
    }

    public static String getIntArrayString(int[] intArray) {
        return getIntArrayString(intArray, ",");
    }

    public static String getIntArrayString(int[] intArray, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int each : intArray) {
            sb.append(each).append(delimiter);
        }
        return sb.toString();
    }

    public static String getASCII(int begin, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = begin; i < end; i++) {
            sb.append(i).append(":").append((char) i).append("/t");
            // sb.append((char) i).append("/t");
            if (i % 10 == 0) {
                sb.append("/n");
            }
        }
        return sb.toString();
    }

    public static String getCHASCII(int begin, int end) {
        return getASCII(19968, 40869);
    }

    public static void showASCII(int begin, int end) {
        for (int i = begin; i < end; i++) {
            // System.out.print(i + ":" + (char) i + "/t");
            System.out.print((char) i + "/t");
            if (i % 10 == 0) {
                System.out.println();
            }
        }
    }

    public static void showCHASCII() {
        showASCII(19968, 40869);
    }

    public static void showIntArray(int[] intArray) {
        showIntArray(intArray, ",");
    }

    public static void showIntArray(int[] intArray, String delimiter) {
        for (int each : intArray) {
            System.out.print(each + delimiter);
        }
    }

//    public static void createFile(String filePathAndName, String fileContent)
//            throws IOException {
//
//        String filePath = filePathAndName;
//        filePath = filePath.toString();
//        File myFilePath = new File(filePath);
//        if (!myFilePath.exists()) {
//            myFilePath.createNewFile();
//        }
//        FileWriter resultFile = new FileWriter(myFilePath);
//        PrintWriter myFile = new PrintWriter(resultFile);
//        String strContent = fileContent;
//        myFile.println(strContent);
//        myFile.close();
//        resultFile.close();
//    }

    public static void main(String[] args) throws IOException {

//        System.out.println(char2ASCII('A'));
//        System.out.println(char2ASCII('a'));
//        System.out.println(char2ASCII('一'));
//        System.out.println(char2ASCII('二'));

//        String s = "好好学习！天天向上！————笑的自然 2009年3月11日";
//        showIntArray(string2ASCII(s), " ");
//        System.out.println();
//        System.out.println(ascii2String(string2ASCII(s)));
//
//        createFile("c://console_ch.txt", getCHASCII(0, 50000));
    }

}
