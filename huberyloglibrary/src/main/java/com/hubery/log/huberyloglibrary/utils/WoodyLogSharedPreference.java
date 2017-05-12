package com.hubery.log.huberyloglibrary.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public class WoodyLogSharedPreference {

    private static final String NAME_CONFIG_INFO = "woody_log_config";
    private static final String KEY_CONFIG = "woody_log_path";
    private static final String CUSTOM_VALUE= "custom_value";

    public WoodyLogSharedPreference(Context context) {
        
    }

    public static void setWoodyPath(Context context,String path) {
        Editor editor = context.getSharedPreferences(NAME_CONFIG_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_CONFIG, path);
        editor.apply();
    }

    public static String getWoodyPath(Context context) {
        String result = context.getSharedPreferences(NAME_CONFIG_INFO, Context.MODE_PRIVATE).getString(KEY_CONFIG, null);
        return result;
    }

    public static void setWoodyCustomValue(Context context,String value) {
        Editor editor = context.getSharedPreferences(NAME_CONFIG_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(CUSTOM_VALUE, value);
        editor.apply();
    }

    public static String getWoodyCustomValue(Context context) {
        String result = context.getSharedPreferences(NAME_CONFIG_INFO, Context.MODE_PRIVATE).getString(CUSTOM_VALUE, "");
        return result;
    }

}
