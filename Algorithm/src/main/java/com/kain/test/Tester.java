package com.kain.test;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created on 8/9/2017.1
 */
public class Tester {

    static int evicted = 0;

    public static void main(String args[]) throws InterruptedException, IOException {

        Thread.sleep(2000);
        ExecutorService threadPool = Executors.newFixedThreadPool(200);

        EvictionListener<String, String> listener = new EvictionListener<String, String>() {
            @Override
            public void onEviction(String key, String value) {
                synchronized (this) {
                    evicted++;
                }
//                System.out.println("Evicted key=" + key + ", value=" + value);
            }
        };
        final ConcurrentMap<String, String> map = new ConcurrentLinkedHashMap.Builder<String, String>().
                listener(listener).maximumWeightedCapacity(1024).concurrencyLevel(1).build();
        for (int j = 0; j < 200; j++) {
            final int tmp = j;
            Thread t = new Thread() {

                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        map.put(String.valueOf(tmp) + "-" + String.valueOf(i), "===================================================" + String.valueOf(i));
                    }
                    System.out.println(map.size());
                }
            };
            threadPool.execute(t);
        }
        threadPool.shutdown();
        threadPool.awaitTermination(24, TimeUnit.HOURS);
        Thread.sleep(1000);
        System.out.println("Final " + map.size());
        System.out.println("Goes in " + evicted);
        while (true) {
            if (System.in.read() == 103)
            break;
        }
    }
}
