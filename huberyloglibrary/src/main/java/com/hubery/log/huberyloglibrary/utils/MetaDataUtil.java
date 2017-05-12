package com.hubery.log.huberyloglibrary.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.text.TextUtils;

import com.hubery.log.huberyloglibrary.LogConfig;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public final class MetaDataUtil {

    private static String mAppId = "";
    private static String mAppVersionName = "";

    public static String UPLOADHOST = "";
    public static int UPLOADPORT = 0;

    private MetaDataUtil() {
    }

    public static String getVersionName() {
        if (!TextUtils.isEmpty(mAppVersionName)) {
            return mAppVersionName;
        }
        try {
            if (null != LogConfig.getWoodyContext()) {
                PackageInfo pinfo =
                        LogConfig.getWoodyContext().getApplicationContext().getPackageManager()
                                .getPackageInfo(LogConfig.getWoodyContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
                mAppVersionName = pinfo.versionName;
                return mAppVersionName;
            }
        } catch (Exception e) {
        }
        return "";
    }

    private static int getMetaDataInt(Context context, String key) {
        ApplicationInfo info = null;
        try {
            info =
                    context.getPackageManager().getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (info != null && info.metaData != null) {
            return info.metaData.getInt(key);
        }
        return -1;
    }

    private static String getMetaDataString(Context context, String key) {
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (info != null && info.metaData != null) {
            return info.metaData.getString(key);
        }
        return null;
    }


    /**
     * 获取系统版本
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSystemModel(){
        return  Build.MODEL;
    }

}
