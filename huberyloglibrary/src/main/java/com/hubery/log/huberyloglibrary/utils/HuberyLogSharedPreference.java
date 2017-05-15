package com.hubery.log.huberyloglibrary.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public class HuberyLogSharedPreference {

    private static final String NAME_CONFIG_INFO = "hubery_log_config";
    private static final String KEY_CONFIG = "hubery_log_path";

    public HuberyLogSharedPreference(Context context) {
        
    }

    public static void setHeburyLogPath(Context context, String path) {
        Editor editor = context.getSharedPreferences(NAME_CONFIG_INFO, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_CONFIG, path);
        editor.apply();
    }

    public static String getHeberyLogPath(Context context) {
        String result = context.getSharedPreferences(NAME_CONFIG_INFO, Context.MODE_PRIVATE).getString(KEY_CONFIG, null);
        return result;
    }

}
