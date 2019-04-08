package com.kain.system.out;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

public class SystemOutToLog4jTest {

    final static Logger logger = Logger.getLogger(SystemOutToLog4jTest.class.getName());

    static {
        SystemOutToLog4jAdaptor.enableForPackage("com.kain");
        SystemErrorToLog4jAdaptor.enableForPackage("com.kain");
    }

    public static void main(String[] args) {

        System.out.println(123123);

        logger.debug("Hello this is a debug message");

        TestException exception = new TestException();
        exception.printStackTrace();
        System.out.println(new SimpleDateFormat());

        Throwable e = new Throwable();
        e.printStackTrace();

        test1();
        System.out.println("Print out In Log File");
        System.err.println("Print error In Log File");
        logger.info("Hello this is a info message");
    }

    public static void test1() {
        RuntimeException exception = new RuntimeException();
        exception.printStackTrace();
        test2();
    }

    public static void test2() {
        StringIndexOutOfBoundsException exception = new StringIndexOutOfBoundsException();
        exception.printStackTrace();
    }

}