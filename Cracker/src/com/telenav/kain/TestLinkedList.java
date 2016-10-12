package com.telenav.kain;

import java.util.LinkedList;

/**
 * Created on 4/13/2016.
 */
public class TestLinkedList {

    public static void main(String args[]) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
        for (int i = 0; i < 30; i++) {
            if (i % 3 == 0) {
                list.remove(i);
            }
        }
        System.out.println(list);
    }
}
