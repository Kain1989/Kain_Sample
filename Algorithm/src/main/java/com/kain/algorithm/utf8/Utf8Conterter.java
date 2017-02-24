package com.kain.algorithm.utf8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 2/23/2017.
 */
public class Utf8Conterter {

    static int byteNum = 0;

    public static void main(String args[]) {
        List<String> reversedUtf8 = convertToUtf8("10000000000000000000000000000");
        Collections.reverse(reversedUtf8);
        for (String utf8 : reversedUtf8) {
            System.out.println(utf8);
        }
    }

    public static List<String> convertToUtf8(String input) {
        byteNum++;
        int len = input.length();
        List<String> utf8List = new ArrayList<>();
        if (byteNum == 1 && len <= 7) {
            String tmp = "000000" + input;
            utf8List.add("0" + tmp.substring(tmp.length() - 7, tmp.length()));
        } else if (byteNum > 1 && len <= 7 - byteNum) {
            String tmp = "0000" + input;
            String pos = "";
            for (int i = 0; i < byteNum; i++) {
                pos += "1";
            }
            utf8List.add(pos + "0" + tmp.substring(tmp.length() - (7 - byteNum), tmp.length()));
        } else {
            String tmp = input;
            if (len < 7) {
                String pos = "";
                for (int i = 0; i < (7 - len); i++) {
                    pos += "0";
                }
                tmp = pos + input;
            }
            utf8List.add("10" + tmp.substring(tmp.length() - 6, tmp.length()));
            utf8List.addAll(convertToUtf8(tmp.substring(0, tmp.length() - 6)));
        }
        return utf8List;

    }
}
