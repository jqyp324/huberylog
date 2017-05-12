package com.hubery.log.huberyloglibrary;

import com.hubery.log.huberyloglibrary.utils.SystemUtil;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public final class iLog {

    public static Logger logger;

    static {
        logger = new Logger();
    }

    public iLog() {
        throw new AssertionError("can not instantiate iLog");
    }

    public static void i(Object message) {
        try {
            logger.i(SystemUtil.getStackTrace(),message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void i(String tag,Object message) {
        try {
            logger.i(tag,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(Object message) {
        try {
            logger.d(SystemUtil.getStackTrace(),message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(String tag,Object message) {
        try {
            logger.d(tag,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(Object message) {
        try {
            logger.e(SystemUtil.getStackTrace(),message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(String tag,Object message) {
        try {
            logger.e(tag,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void w(Object message) {
        try {
            logger.w(SystemUtil.getStackTrace(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void w(String tag,Object message) {
        try {
            logger.w(tag,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void v(Object message) {
        try {
            logger.v(SystemUtil.getStackTrace(), message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void v(String tag,Object message) {
        try {
            logger.v(tag,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

