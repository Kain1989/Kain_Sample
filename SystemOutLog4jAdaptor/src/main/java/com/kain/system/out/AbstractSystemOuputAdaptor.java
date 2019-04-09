package com.kain.system.out;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.PrintStream;
import java.util.Arrays;

public abstract class AbstractSystemOuputAdaptor extends PrintStream {

    private static final Logger logger = Logger.getLogger(AbstractSystemOuputAdaptor.class);

    public static final int STACK_LAYER_NUM = 2;

    protected String packageOrClassToLog;

    protected AbstractSystemOuputAdaptor(PrintStream original, String packageOrClassToLog) {
        super(original);
        this.packageOrClassToLog = packageOrClassToLog;
    }

    /**
     * Number of stack layers to call real log output
     *
     * @return
     */
    protected abstract int getStackLayerNum();

    protected abstract void customisePrintln(String log);

    @Override
    public void println(boolean x) {
        customisePrintln(String.valueOf(x));
    }

    @Override
    public void println(char x) {
        customisePrintln(String.valueOf(x));
    }

    @Override
    public void println(int x) {
        customisePrintln(String.valueOf(x));
    }

    @Override
    public void println(long x) {
        customisePrintln(String.valueOf(x));
    }

    @Override
    public void println(float x) {
        customisePrintln(String.valueOf(x));
    }

    @Override
    public void println(double x) {
        customisePrintln(String.valueOf(x));
    }

    @Override
    public void println(char x[]) {
        customisePrintln(StringUtils.join(Arrays.asList(x), ","));
    }

    @Override
    public void println(String log) {
        customisePrintln(log);
    }

    @Override
    public void println(Object obj) {
        String log = String.valueOf(obj);
        customisePrintln(log);
    }

    protected class LoggerAttribute {

        private StackTraceElement[] stack;

        private StackTraceElement caller;

        private Logger logger;

        public StackTraceElement[] getStack() {
            return stack;
        }

        public StackTraceElement getCaller() {
            return caller;
        }

        public Logger getLogger() {
            return logger;
        }

        public LoggerAttribute invoke(String packageOrClassToLog) {
            stack = Thread.currentThread().getStackTrace();
            caller = findCallerToLog(stack, packageOrClassToLog);
            if (caller == null) {
                logger = Logger.getLogger("SYSTEM OUT");
            } else {
                logger = Logger.getLogger(caller.getClassName());
            }
            return this;
        }
    }

    public StackTraceElement findCallerToLog(StackTraceElement[] stack, String packageOrClassToLog) {
        int i = 0;
        for (StackTraceElement element : stack) {
            i++;
            if (!element.getClassName().startsWith(packageOrClassToLog)) {
                continue;
            }
            if (i <= getStackLayerNum() + STACK_LAYER_NUM) {
                continue;
            }
            boolean isExceptionClass = false;
            try {
                Class clazz = Class.forName(element.getClassName());
                if ((clazz.newInstance() instanceof Throwable)) {
                    isExceptionClass = true;
                }
            } catch (Throwable t) {
            }
            if (isExceptionClass) {
                logger.debug("Met exception class, continue look upper stack trace");
                continue;
            } else {
                return element;
            }

        }
        return null;
    }

}
