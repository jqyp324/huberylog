package com.hubery.log.huberyloglibrary.crash;

import com.hubery.log.huberyloglibrary.Logger;
import com.hubery.log.huberyloglibrary.iLog;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;


    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        handleException(ex);

        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }

    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        StringBuffer exceptionStr = new StringBuffer();
        exceptionStr.append(ex.getMessage());
        StackTraceElement[] elements = ex.getStackTrace();
        if (null != ex.getStackTrace() && ex.getStackTrace().length > 0) {
            for (int i = 0; i < elements.length; i++) {
                exceptionStr.append(elements[i].toString());
            }
        }
        String[] tags = Logger.generateTag(elements[0]);
        iLog.e(tags[1],tags[0] + ":" + exceptionStr.toString());
        return true;
    }

}
