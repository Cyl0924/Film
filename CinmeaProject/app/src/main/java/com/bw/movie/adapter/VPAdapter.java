package com.bw.movie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.FilmFragment;
import com.bw.movie.fragment.MyFragment;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class VPAdapter extends FragmentPagerAdapter {

    public Fragment fragment;

    public VPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                fragment = new CinemaFragment();
                break;
            case 1:
                fragment = new FilmFragment();
                break;
            case 2:
                fragment = new MyFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
