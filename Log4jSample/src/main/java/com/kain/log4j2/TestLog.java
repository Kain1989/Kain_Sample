package com.kain.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLog {

    private static Logger log = LogManager.getLogger(TestLog.class);

    public static void main(String[] args) throws InterruptedException {
        log.info("Start test");
        log.debug("Start test");
        TestLog2.test1();
        Thread.sleep(2000);
        log.info("Start test 2");
        log.debug("Start test 2");
        TestLog2.test2();
        Thread.sleep(2000);
        TestLog2.test2();
        log.debug("Finished");
    }
}
