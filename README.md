# Android日志打印工具-huberylog-1.0.0



## 简介:

> ​	huberylog是一个android平台非常好用的日志打印工具，
>
> 1.可以很好的打印对象，不必关系传入的值是不是string，无需关心TAG是什么 (Log.v(TAG,"msg"))自动会以当前被打印的类名为tag，
>
> 比如：
>
> ​	MainActivity.java 里调用了iLog.v(xxx); tag为MainActivity，并自动打印出所在行数，也支持传入tag: iLog.v(TAG,xxx);
>
> 2. 支持在控制台输出日志的同时自动写入文件(可做后续上报使用，也可以在qa测试期间浮现bug)



## 如何使用：

1. 引入library

2. 选填(如果有写入文件的需求)，Mainfest加入以下权限

   ```xml
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

   //6.0以后需要动态获取sd卡写入权限，如果app本身申请了，可以忽略
   <activity android:name="com.hubery.log.huberyloglibrary.permission.LogPermissionActivity"
                     android:theme="@android:style/Theme.Translucent" />
               />
   ```

3. 初始化HuberyLog工具：

   ```java
   LogConfig.init(this,true,true);//参数2为是否在控制台输出日志，参数3为是否同时将日志写入文件
   ```

## 示例：

```java
//基本数据类型打印
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



日志输出：
MainActivity:MainActivity.onClick(MainActivity.java:44):1111
MainActivity:MainActivity.onClick(MainActivity.java:45):string
MainActivity:MainActivity.onClick(MainActivity.java:46):12345.456
MainActivity:MainActivity.onClick(MainActivity.java:47):true
MainActivity:MainActivity.onClick(MainActivity.java:52):ArrayList size = 2 [
              [0]:str1,
              [1]:str2
              
              ]
MainActivity:MainActivity.onClick(MainActivity.java:57):HashMap {
              [1 -> 123]
              [2 -> 456]
              }
MainActivity: MainActivity.onClick(MainActivity.java:65):[                                                                           {
                                                                                "key": 1
                                                                            }
                                                                        ]
```



> ## 特别说明：
>
> 1.写入日志的文件路径为sd卡根路径下得app包名下
>
> 2.日志文件的名字为当天日期+.log
>
> 3.单个日志文件默认为2M，最大为7M，超过会删除之前的日志
>
> 4.默认只保存最近5天的日志，如果第6天则会删除最早一天的日志



## 其他功能：

```java

LogConfig.addCatchCrash();//如果增加了捕获crash则会自动打印
LogConfig.setLogFileMaxSize(long fileMaxSize);//设置文件最大不超过5M
LogConfig.setLogMaxnums(int nums);//设置最大日期数量
String Path = LogConfig.getLogMaxnums();//获取日志保存路径
List<String> list = LogConfig.getLocalLogDate()//获取储存日志的日期
long size = LogConfig.getLogFileSize(List<String> mList){//获取日志文件大小
long size = LogConfig.getTodayFileSize()//获取当天文件大小
```



android log tools,you can print object , write log file