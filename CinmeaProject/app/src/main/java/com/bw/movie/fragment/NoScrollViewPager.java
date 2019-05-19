package com.bw.movie.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class NoScrollViewPager extends ViewPager {

    // 是否禁止 viewpager 左右滑动
    /**
     * TODO    ViewPager的处理滑动事件的代码肯定在 onTouchEvent 中，我们重写此方法，并返回false，不再处理滑动事件。
     * TODO    同时也重写onInterceptTouchEvent方法并返回fasle，不拦截Touch事件。不拦截，不消费，
     * TODO    所有当触摸屏幕的时候从宏观上看就好像没有了这个ViewPager了一样，当然，也就不会滑动到它了。
     */
    private boolean noScroll = true;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else{
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll){
            return false;
        }else{
            return super.onInterceptTouchEvent(arg0);
        }
    }


}