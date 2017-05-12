package com.hubery.log.huberyloglibrary;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.hubery.log.huberyloglibrary.utils.ArrayUtil;
import com.hubery.log.huberyloglibrary.utils.SystemUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by hubery on 17/5/12.
 * email: jqyp324@foxmail.com
 * github: https://github.com/jqyp324/huberylog.git
 */
public final class Logger implements Printer {

    protected Logger() {

    }

    /**
     *
     * @param type
     * @param element
     * @param msg
     */
    private void logString(LogType type, StackTraceElement element, String msg) {
        String[] tags = generateTag(element);
        outConsole(type,tags[1],tags[0],msg);//console打印
//        writeLog(type,tags[1],msg);//写入日志
    }

    /**
     *
     * @param type
     * @param tag
     * @param msg
     */
    private void logString(LogType type, String tag,String msg) {
        outConsole(type,tag,msg);//console打印
//        writeLog(type,tag,msg);//写入日志
    }

    private void logObject(LogType type, String tag, Object object) {

        if (object != null) {
            final String simpleName = object.getClass().getSimpleName();
            if (object instanceof Throwable) {
                logString(type, tag, object.toString());
            } else if (object instanceof String) {
                logString(type, tag,(String) object);
            } else if (object.getClass().isArray()) {
                String msg = "Temporarily not support more than two dimensional Array!";
                int dim = ArrayUtil.getArrayDimension(object);
                switch (dim) {
                    case 1:
                        Pair pair = ArrayUtil.arrayToString(object);
                        msg = simpleName.replace("[]", "[" + pair.first + "] {\n");
                        msg += pair.second + "\n";
                        break;
                    case 2:
                        Pair pair1 = ArrayUtil.arrayToObject(object);
                        Pair pair2 = (Pair) pair1.first;
                        msg = simpleName.replace("[][]", "[" + pair2.first + "][" + pair2.second + "] {\n");
                        msg += pair1.second + "\n";
                        break;
                    default:
                        break;
                }
                logString(type, tag, msg + "}");
            } else if (object instanceof Collection) {
                Collection collection = (Collection) object;
                String msg = "%s size = %d [\n";
                msg = String.format(msg, simpleName, collection.size());
                if (!collection.isEmpty()) {
                    Iterator<Object> iterator = collection.iterator();
                    int flag = 0;
                    while (iterator.hasNext()) {
                        String itemString = "[%d]:%s%s";
                        Object item = iterator.next();
                        msg += String.format(itemString, flag, SystemUtil.objectToString(item),
                                flag++ < collection.size() - 1 ? ",\n" : "\n");
                    }
                }
                logString(type, tag,msg + "\n]");
            } else if (object instanceof Map) {
                String msg = simpleName + " {\n";
                Map<Object, Object> map = (Map<Object, Object>) object;
                Set<Object> keys = map.keySet();
                for (Object key : keys) {
                    String itemString = "[%s -> %s]\n";
                    Object value = map.get(key);
                    msg += String.format(itemString, SystemUtil.objectToString(key),
                            SystemUtil.objectToString(value));
                }
                logString(type, tag,msg + "}");
            } else if (object instanceof JSONArray || object instanceof JSONObject) {
                String msg = json(object.toString());
                logString(type, tag,msg);
            } else {
                logString(type, tag, SystemUtil.objectToString(object));
            }
        } else {
            logString(type, tag,SystemUtil.objectToString(object));
        }
    }

    /**
     * @param type
     * @param element
     * @param object
     */
    private void logObject(LogType type, StackTraceElement element, Object object) {

        if (object != null) {
            final String simpleName = object.getClass().getSimpleName();
            if (object instanceof Throwable) {
                logString(type, element,object.toString());
            } else if (object instanceof String) {
                logString(type, element,(String) object);
            } else if (object.getClass().isArray()) {
                String msg = "Temporarily not support more than two dimensional Array!";
                int dim = ArrayUtil.getArrayDimension(object);
                switch (dim) {
                    case 1:
                        Pair pair = ArrayUtil.arrayToString(object);
                        msg = simpleName.replace("[]", "[" + pair.first + "] {\n");
                        msg += pair.second + "\n";
                        break;
                    case 2:
                        Pair pair1 = ArrayUtil.arrayToObject(object);
                        Pair pair2 = (Pair) pair1.first;
                        msg = simpleName.replace("[][]", "[" + pair2.first + "][" + pair2.second + "] {\n");
                        msg += pair1.second + "\n";
                        break;
                    default:
                        break;
                }
                logString(type, element,msg + "}");
            } else if (object instanceof Collection) {
                Collection collection = (Collection) object;
                String msg = "%s size = %d [\n";
                msg = String.format(msg, simpleName, collection.size());
                if (!collection.isEmpty()) {
                    Iterator<Object> iterator = collection.iterator();
                    int flag = 0;
                    while (iterator.hasNext()) {
                        String itemString = "[%d]:%s%s";
                        Object item = iterator.next();
                        msg += String.format(itemString, flag, SystemUtil.objectToString(item),
                                flag++ < collection.size() - 1 ? ",\n" : "\n");
                    }
                }
                logString(type, element,msg + "\n]");
            } else if (object instanceof Map) {
                String msg = simpleName + " {\n";
                Map<Object, Object> map = (Map<Object, Object>) object;
                Set<Object> keys = map.keySet();
                for (Object key : keys) {
                    String itemString = "[%s -> %s]\n";
                    Object value = map.get(key);
                    msg += String.format(itemString, SystemUtil.objectToString(key),
                            SystemUtil.objectToString(value));
                }
                logString(type, element, msg + "}");
            } else if (object instanceof JSONArray || object instanceof JSONObject) {
                String msg = json(object.toString());
                logString(type, element, msg);
            } else {
                logString(type, element, SystemUtil.objectToString(object));
            }
        } else {
            logString(type, element, SystemUtil.objectToString(object));
        }
    }

    /**
     * 自动生成tag
     *
     * @return
     */
    private String[] generateTag(StackTraceElement caller) {
        String[] tags = new String[2];
        tags[0] = "%s.%s%s";
        String stackTrace = caller.toString();
        stackTrace = stackTrace.substring(stackTrace.lastIndexOf('('), stackTrace.length());
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        if (callerClazzName.contains("$")) {
            callerClazzName = callerClazzName.substring(0, callerClazzName.indexOf("$"));
        }
        tags[0] = String.format(tags[0], callerClazzName, caller.getMethodName(), stackTrace);
        tags[1] = callerClazzName;
        return tags;
    }

    private static void outConsole(LogType type,String tag,String value,String msg){

        if (LogConfig.mConfigAllowConsoleLog) {
            if(!TextUtils.isEmpty(value)){
                String contentStr = value + ":" + msg;
                outConsole(type,tag,contentStr);
            }else{
                outConsole(type,tag,msg);
            }
        }

    }

    private static void outConsole(LogType type,String tag,String contentStr){
        if(type == LogType.Verbose){
            Log.v(tag, contentStr);
        }else if((type == LogType.Debug)){
            Log.d(tag, contentStr);
        }else if((type == LogType.Error)){
            Log.e(tag, contentStr);
        }else if((type == LogType.Info)){
            Log.i(tag, contentStr);
        }else if((type == LogType.Warn)){
            Log.w(tag, contentStr);
        }
    }

    private static void writeLog(LogType type,String msg){
//        if (IhumanLogConfig.mConfigWriteLog){
//            String logType = "NOTICE";
//            if (type == LogType.Info){
//                logType = "TRACE";
//            }
//            if(!LogErrorNo.DEFAULT.value.equals(errorNo)){
//                logType = "ERROR";
//            }
//            String log = createTraceLog(logType,subSysModel,modle,url,request,operator,errorNo,msg);
//            WoodyLogFileHelper.getInstance().saveLogFile(log);
//        }
    }

    public String json(String json) {
        int indent = 4;
        if (TextUtils.isEmpty(json)) {
            return "JSON{json is null}";
        }
        String msg = "";
        try {
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                msg = jsonObject.toString(indent);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                msg = jsonArray.toString(indent);
            }
        } catch (JSONException e) {
            msg = e.toString();
        }
        return msg;
    }




    @Override
    public void d(StackTraceElement element,Object message) {
        logObject(LogType.Debug, element, message);
    }

    @Override
    public void e(StackTraceElement element,Object message) {
        logObject(LogType.Error, element,message);
    }

    @Override
    public void w(StackTraceElement element,Object message) {
        logObject(LogType.Warn, element, message);
    }

    @Override
    public void i(StackTraceElement element,Object message) {
        logObject(LogType.Info, element,message);
    }

    @Override
    public void v(StackTraceElement element, Object message) {
        logObject(LogType.Verbose, element, message);
    }

    @Override
    public void v(String tag,Object message) {
        logObject(LogType.Verbose, tag,message);
    }

    @Override
    public void i(String tag,Object message) {
        logObject(LogType.Info, tag,message);
    }

    @Override
    public void d(String tag,Object message) {
        logObject(LogType.Debug, tag,message);
    }

    @Override
    public void w(String tag, Object message) {
        logObject(LogType.Warn, tag, message);
    }

    @Override
    public void e(String tag, Object message) {
        logObject(LogType.Error, tag, message);
    }

}
