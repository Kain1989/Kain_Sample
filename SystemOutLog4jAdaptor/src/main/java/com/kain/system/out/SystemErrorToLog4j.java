package com.kain.system.out;

import java.io.PrintStream;

public class SystemErrorToLog4j extends PrintStream {

    private static final PrintStream originalSystemOut = System.err;

    public static SystemErrorToLog4j systemErrorToLog4j;

    public static void enableForClass(Class className) {
        systemErrorToLog4j = new SystemErrorToLog4j(originalSystemOut, className.getName());
        System.setErr(systemErrorToLog4j);
    }

    public static void enableForPackage(String packageToLog) {
        systemErrorToLog4j = new SystemErrorToLog4j(originalSystemOut, packageToLog);
        System.setErr(systemErrorToLog4j);
    }

    public static void disable() {
        System.setOut(originalSystemOut);
        systemErrorToLog4j = null;
    }

    private String packageOrClassToLog;

    private SystemErrorToLog4j(PrintStream original, String packageOrClassToLog) {
        super(original);
        this.packageOrClassToLog = packageOrClassToLog;
    }

    @Override
    public void println(String line) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement caller = findCallerToLog(stack);
        if (caller == null) {
            super.println(line);
            return;
        }
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(caller.getClassName());
        logger.error("SYSTEM ERROR " + stack[2].getClassName() + ":" + stack[2].getLineNumber() + " - " + line);
    }

    @Override
    public void println(Object x) {
        String s = String.valueOf(x);

        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StackTraceElement caller = findCallerToLog(stack);
        if (caller == null) {
            super.println(x);
            return;
        }
        synchronized (this) {
            org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(caller.getClassName());
            logger.error("SYSTEM ERROR " + stack[6].getClassName() + ":" + stack[6].getLineNumber() + " - " + s);
        }
    }

    public StackTraceElement findCallerToLog(StackTraceElement[] stack) {
        for (StackTraceElement element : stack) {
            if (element.getClassName().startsWith(packageOrClassToLog)) {
                return element;
            }
        }
        return null;
    }
}