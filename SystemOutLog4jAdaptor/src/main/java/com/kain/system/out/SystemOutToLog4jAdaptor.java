package com.kain.system.out;

import java.io.PrintStream;

public class SystemOutToLog4jAdaptor extends AbstractSystemOuputAdaptor {

    private static final PrintStream originalSystemOut = System.out;

    private static SystemOutToLog4jAdaptor systemOutToLogger;

    public static void enableForClass(Class className) {
        systemOutToLogger = new SystemOutToLog4jAdaptor(originalSystemOut, className.getName());
        System.setOut(systemOutToLogger);
    }

    public static void enableForPackage(String packageToLog) {
        systemOutToLogger = new SystemOutToLog4jAdaptor(originalSystemOut, packageToLog);
        System.setOut(systemOutToLogger);
    }

    private SystemOutToLog4jAdaptor(PrintStream original, String packageOrClassToLog) {
        super(original, packageOrClassToLog);
    }

    @Override
    protected int getStackLayerNum() {
        return 1;
    }

    @Override
    protected void customisePrintln(String log) {
        synchronized (this) {
            LoggerAttribute inner = new LoggerAttribute().invoke(packageOrClassToLog);
            inner.getLogger().info("SYSTEM ERR " + inner.getCaller().getClassName() + ":" + inner.getCaller().getLineNumber() + " - " + log);
        }
    }

}