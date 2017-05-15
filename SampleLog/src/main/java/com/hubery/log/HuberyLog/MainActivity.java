package com.hubery.log.HuberyLog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hubery.log.huberyloglibrary.LogConfig;
import com.hubery.log.huberyloglibrary.iLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button mLogConsoleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogConfig.init(this,true,true);

        mLogConsoleBtn = (Button) this.findViewById(R.id.log_console);
        mLogConsoleBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == mLogConsoleBtn){
            //基本数据类型
            iLog.v(1111);
            iLog.v("string");
            iLog.v(12345.456f);
            iLog.v(true);

            //容器
            List<String> mList = new ArrayList<>();
            mList.add("str1");
            mList.add("str2");
            iLog.v(mList);

            //Map
            Map<Integer,String> map = new HashMap<>();
            map.put(1,"123");
            map.put(2,"456");
            iLog.v(map);

            //Json
            try {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("key",1);
                jsonArray.put(jsonObject);
                iLog.v(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
