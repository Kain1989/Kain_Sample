package com.kain.system.out;

import java.io.PrintStream;

public class SystemErrorToLog4jAdaptor extends AbstractSystemOuputAdaptor {

    private static final PrintStream originalSystemOut = System.err;

    private static SystemErrorToLog4jAdaptor systemOutToLogger;

    public static void enableForClass(Class className) {
        systemOutToLogger = new SystemErrorToLog4jAdaptor(originalSystemOut, className.getName());
        System.setErr(systemOutToLogger);
    }

    public static void enableForPackage(String packageToLog) {
        systemOutToLogger = new SystemErrorToLog4jAdaptor(originalSystemOut, packageToLog);
        System.setErr(systemOutToLogger);
    }

    private SystemErrorToLog4jAdaptor(PrintStream original, String packageOrClassToLog) {
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