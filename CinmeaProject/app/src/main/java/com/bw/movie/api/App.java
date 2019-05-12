package com.bw.movie.api;

import android.app.Application;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class App extends Application {

    public static String sessionId;
    public static int userId;
    public static String headPic;
    public static String nickName;
    public static long birthday;
    public static int ids;
    public static long lastLoginTime;
    public static String phones;
    public static int sex;


    @Override
    public void onCreate() {
        super.onCreate();
    }
}
