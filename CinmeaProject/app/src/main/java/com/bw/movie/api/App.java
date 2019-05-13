package com.bw.movie.api;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;

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
    public static int Windowswidth;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Windowswidth = wm.getDefaultDisplay().getWidth();
    }
}
