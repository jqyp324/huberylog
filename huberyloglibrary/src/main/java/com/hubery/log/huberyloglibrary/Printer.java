package com.hubery.log.huberyloglibrary;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public interface Printer {
//    void d(StackTraceElement element, String message, Object... args);
    void d(StackTraceElement element, Object message);

//    void e(StackTraceElement element, String message, Object... args);
    void e(StackTraceElement element, Object message);

//    void w(StackTraceElement element, String message, Object... args);
    void w(StackTraceElement element, Object message);

//    void i(StackTraceElement element, String message, Object... args);
    void i(StackTraceElement element, Object message);

//    void v(StackTraceElement element, String message, Object... args);
    void v(StackTraceElement element, Object message);

    /**
     * 解决混淆的问题,直接传入tag和method
     */
    void v(String tag, Object message);
    void i(String tag, Object message);
    void d(String tag, Object message);
    void w(String tag, Object message);
    void e(String tag, Object message);
}
