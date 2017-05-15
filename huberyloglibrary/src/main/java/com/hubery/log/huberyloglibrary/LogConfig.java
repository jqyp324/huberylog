package com.hubery.log.huberyloglibrary;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.hubery.log.huberyloglibrary.crash.CrashHandler;
import com.hubery.log.huberyloglibrary.utils.HuberyLogSharedPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public class LogConfig {

    /**
     * 是否允许控制台输出普通debug日志
     */
    public static boolean mConfigAllowConsoleLog = false;
    /**
     * 是否允许写入日志文件
     */
    public static boolean mConfigWriteLog = false;

    /**
     * 文件path路径
     */
    private static String LOG_PAHT = "";
    /**
     * 单个文件大小限制
     */
    public static long LOG_MAXSIZE = 2 * 1024 * 1024;//默认为5M
    public static final long MAXLOGSIZE = 5*1024*1024;//限制最大文件大小为7M
    /**
     * 日志文件后缀
     */
    public static final String LOG_SUFFIX = ".log";
    /**
     * 文件最大保存天数
     */
    private static int LOG_MAXNUMS = 5;

    private static Context mContext;

    /**
     *
     * @param context
     * @param allowShowLog 是否允许控制台输出日志
     * @param allowWirteFile 是否允许记录写入文件
     */
    public static void init(Context context,boolean allowShowLog,boolean allowWirteFile){
        mContext = context;
        mConfigAllowConsoleLog = allowShowLog;
        mConfigWriteLog = allowWirteFile;
        initLogManager(context);
        checkLogFileNums();
    }

    /**
     * catch crash日志
     */
    public static void addCatchCrash() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init();
    }


    /**
     * @param fileMaxSize eg: 1*1024*1024
     * 设置文件最大不超过5M
     */
    public static void setLogFileMaxSize(long fileMaxSize) {
        if (fileMaxSize <= 0 || fileMaxSize > MAXLOGSIZE) {
            return;
        }
        LOG_MAXSIZE = fileMaxSize;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 设置最大日期数量
     * @param nums
     */
    public static void setLogMaxnums(int nums) {
        if(nums <= 0){
            return;
        }
        LOG_MAXNUMS = nums;
    }

    public static int getLogMaxnums() {
        return LOG_MAXNUMS;
    }

    public static String getLogPaht() {
        return LOG_PAHT;
    }

    /**
     * 日志初始化
     *
     * @param mContext
     */
    private static void initLogManager(Context mContext) {
        if (!TextUtils.isEmpty(LOG_PAHT)) {
            return;
        }
        if (!TextUtils.isEmpty(HuberyLogSharedPreference.getHeberyLogPath(mContext))) {
            LOG_PAHT = HuberyLogSharedPreference.getHeberyLogPath(mContext);
            return;
        }

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            LOG_PAHT = Environment.getExternalStorageDirectory() + File.separator + mContext.getPackageName() + File.separator;
        } else {
            LOG_PAHT = mContext.getCacheDir() + File.separator + mContext.getPackageName() + File.separator;
        }
        File mDir = new File(LOG_PAHT);
        if (!mDir.exists()) {
            mDir.mkdir();
        }
        HuberyLogSharedPreference.setHeburyLogPath(mContext, LOG_PAHT);
    }

    /**
     * 初始化时检测文件数量
     */
    private static void checkLogFileNums() {
        File[] currentLogList = HuberyLogFileHelper.getInstance().getLocalLogFileNums();
        try {
            if (null != currentLogList && currentLogList.length > getLogMaxnums()) {
                for (int i = 0; i < currentLogList.length - getLogMaxnums(); i++) {
                    HuberyLogFileHelper.getInstance().deleteProcessLogFile(currentLogList[i]);
                }
            }
        } catch (Exception e) {

        }
    }

    /**
     * 获取储存日志的日期
     * @return
     */
    public static List<String> getLocalLogDate() {
        List<String> mList = new ArrayList<String>();
        try {
            String[] fileNames = HuberyLogFileHelper.getInstance().getLocalLogNames();
            if (null != fileNames && fileNames.length > 0) {
                for (int i = fileNames.length - 1; i >= 0; i--) {
                    if(!TextUtils.isEmpty(fileNames[i])){
                        mList.add(fileNames[i].substring(0, fileNames[i].indexOf(".")));
                    }
                }
            }
        } catch (Exception e) {
            mList = Arrays.asList(HuberyLogFileHelper.getInstance().getLocalLogNames());
        }
        return mList;
    }

    /**
     * 获取文件大小
     * @param mList
     * @return
     */
    public static long getLogFileSize(List<String> mList){
        long fileSize = 0;
        if(null != mList && mList.size() > 0){
            fileSize = HuberyLogFileHelper.getInstance().getLogFileSize(mList);
        }
        return fileSize;
    }

    /**
     * 获取当天文件大小
     * @return
     */
    public static long getTodayFileSize(){
        long fileSize = 0;
        try {
            fileSize = HuberyLogFileHelper.getInstance().getTodayFileSize();
            return fileSize;
        } catch (Exception e) {
            return 0;
        }
    }

}
