package com.telenav.kain.test.process;

import java.util.concurrent.Executors;

/**
 * Created by zfshi on 8/10/2015.
 */
public class TestThreadId {

    public static void main(String args[]) {

        System.out.println("^([1-9]{1}[0-9]{0,4})(\\u5F04)([1-9]{1}[0-9]{0,4})(\\u53F7)%1%[4]%^([0-9]{1,5})([\\\\p{L}]{1,4})([0-9]{1,5})()%1%[4]");
                Executors.newFixedThreadPool(2).execute(
                        new Thread() {
                            @Override
                            public void run() {
                                System.out.println(Thread.currentThread().getId());
                                System.out.println(Thread.currentThread().getId());
                                System.out.println(Thread.currentThread().getId());
                                System.out.println(Thread.currentThread().getId());
                                System.out.println(Thread.currentThread().getId());
                System.out.println(Thread.currentThread().getId());

            }
        });
    }
}
