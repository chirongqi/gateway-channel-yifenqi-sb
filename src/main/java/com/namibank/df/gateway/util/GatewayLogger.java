package com.namibank.df.gateway.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网关日志类
 * 
 * @author CliveYuan
 * @date Mar 25, 2017 4:47:21 PM
 *
 */
public class GatewayLogger {

    private static final String LOG_PRIFX = "\n ######################################## \n";
    private Logger logger;
    private GatewayLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }
    
    public static GatewayLogger getLogger(Class<?> clazz) {
        return new GatewayLogger(clazz);
    }
    
    public void info(String msg) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        logger.info(stacks[1].getLineNumber() + LOG_PRIFX + msg);
    }
    
    public void info(String format, Object... arguments) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        logger.info(stacks[1].getLineNumber() + LOG_PRIFX + format, arguments);
    }
    
    public void error(String msg) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        logger.error(stacks[1].getLineNumber() + LOG_PRIFX + msg);
    }
    
    public void error(String msg, Throwable t) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        logger.error(stacks[1].getLineNumber() + LOG_PRIFX + msg, t);
    }

    public void warn(String format, Object... arguments) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        logger.warn(stacks[1].getLineNumber() + LOG_PRIFX + format, arguments);
    }

    public void debug(String format, Object... arguments) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        logger.debug(stacks[1].getLineNumber() + LOG_PRIFX + format, arguments);
    }
}
