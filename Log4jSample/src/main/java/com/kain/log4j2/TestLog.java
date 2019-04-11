package com.kain.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class TestLog {

    private static Logger log = LogManager.getLogger(TestLog.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("Start test");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Thread.currentThread().setName(String.valueOf(UUID.randomUUID()));
                log.info("info");
                log.debug("debug");
                log.error("error");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("info1");
                log.debug("debug1");
                log.error("error1");

                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("info2");
                log.debug("debug2");
                log.error("error2");
            }).start();
        }
Thread.sleep(2000);
        log.info("Start test 2");
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Thread.currentThread().setName(String.valueOf(UUID.randomUUID()));
                log.info("info3");
                log.debug("debug3");
                log.error("error3");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("info4");
                log.debug("debug4");
                log.error("error4");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 10000; j++) {
                    log.info("info5");
                    log.debug("debug5===================================================================================");
                    log.error("error5");
                }
            }).start();
        }
    }
}
