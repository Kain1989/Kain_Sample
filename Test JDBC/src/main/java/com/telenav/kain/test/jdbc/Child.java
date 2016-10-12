package com.telenav.kain.test.jdbc;

/**
 * Created by zfshi on 8/26/2015.
 */
public class Child extends Parent {

    public static void doAction() {
        Parent.str = "Child";
//        Parent.setStr("Child");
        Parent.doAction();
    }
}
