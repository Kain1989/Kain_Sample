package com.kain.algorithm.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2/9/2017.
 */
public class Child extends Parent {

    private String child;

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public static void main(String args[]) {
        final Map<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 1; i < 10000; i++) {
            final int finalI = i;
            new Thread() {
                @Override
                public void run() {
                    map.put("1", finalI);
                }
            }.start();
        }
        System.out.println(map.size());
        System.out.println(map);
    }
}
