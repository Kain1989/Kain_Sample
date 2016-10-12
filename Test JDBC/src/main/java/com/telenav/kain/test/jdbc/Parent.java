package com.telenav.kain.test.jdbc;

/**
 * Created by zfshi on 8/26/2015.
 */
public class Parent {

    protected static String str = "parent";
    
    public static void doAction() {
        System.out.println(str);
    }

    protected static void setStr(String str) {
        Parent.str = str;
    }
}
