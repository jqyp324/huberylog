package com.hubery.log.huberyloglibrary.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 权限请求activity
 */
public class LogPermissionActivity extends Activity {

    private static AtomicBoolean isRequest = new AtomicBoolean(false);

    public static final String PERMISSION = "permission";
    private static final int REQ = 1;

    private String[] mPermissionNames;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionNames = getIntent().getStringArrayExtra(PERMISSION);
            if(null != mPermissionNames && mPermissionNames.length > 0){
                requestPermissions(mPermissionNames,REQ);
            }
        }else{
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQ){
            if(grantResults.length >0 &&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                //用户同意授权
            }else{
                //用户拒绝授权
            }
        }
        finish();
    }

    public synchronized static void launch(Context context, String[] permissions){
        if(isRequest.get()){
          return;
        }
        Intent mIntent = new Intent(context,LogPermissionActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.putExtra(PERMISSION,permissions);
        context.startActivity(mIntent);
        isRequest.set(true);
    }

}