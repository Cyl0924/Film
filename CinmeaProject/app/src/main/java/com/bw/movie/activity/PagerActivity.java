package com.bw.movie.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.adapter.VPAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerActivity extends AppCompatActivity {


    @BindView(R.id.ZongVp_id)
    ViewPager VP;
    @BindView(R.id.Filmbtn_id)
    ImageView FilmBtn;
    @BindView(R.id.Cinemabtn_id)
    ImageView CinemaBtn;
    @BindView(R.id.Mybtn_id)
    ImageView MyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);
        final VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        VP.setAdapter(adapter);
        FilmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VP.setCurrentItem(0);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(FilmBtn,"scaleX",1.16f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(FilmBtn,"scaleY",1.16f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator,objectAnimator1);
                animatorSet.setDuration(100);
                animatorSet.start();
                FilmBtn.setImageResource(R.mipmap.com_icon_film_selected);

                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(CinemaBtn,"scaleX",1f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(CinemaBtn,"scaleY",1f);
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(objectAnimator2,objectAnimator3);
                animatorSet1.setDuration(100);
                animatorSet1.start();
                CinemaBtn.setImageResource(R.mipmap.com_icon_cinema_default);

                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(MyBtn,"scaleX",1f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(MyBtn,"scaleY",1f);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(objectAnimator4,objectAnimator5);
                animatorSet2.setDuration(100);
                animatorSet2.start();
                MyBtn.setImageResource(R.mipmap.com_icon_my_default);

            }
        });


        CinemaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VP.setCurrentItem(1);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(FilmBtn,"scaleX",1f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(FilmBtn,"scaleY",1f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator,objectAnimator1);
                animatorSet.setDuration(100);
                animatorSet.start();
                FilmBtn.setImageResource(R.mipmap.com_icon_film_fault);

                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(CinemaBtn,"scaleX",1.16f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(CinemaBtn,"scaleY",1.16f);
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(objectAnimator2,objectAnimator3);
                animatorSet1.setDuration(100);
                animatorSet1.start();
                CinemaBtn.setImageResource(R.mipmap.com_icon_cinema_selected);

                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(MyBtn,"scaleX",1f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(MyBtn,"scaleY",1f);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(objectAnimator4,objectAnimator5);
                animatorSet2.setDuration(100);
                animatorSet2.start();
                MyBtn.setImageResource(R.mipmap.com_icon_my_default);

            }
        });

        MyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VP.setCurrentItem(2);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(FilmBtn,"scaleX",1f);
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(FilmBtn,"scaleY",1f);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimator,objectAnimator1);
                animatorSet.setDuration(100);
                animatorSet.start();
                FilmBtn.setImageResource(R.mipmap.com_icon_film_fault);

                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(CinemaBtn,"scaleX",1f);
                ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(CinemaBtn,"scaleY",1f);
                AnimatorSet animatorSet1 = new AnimatorSet();
                animatorSet1.playTogether(objectAnimator2,objectAnimator3);
                animatorSet1.setDuration(100);
                animatorSet1.start();
                CinemaBtn.setImageResource(R.mipmap.com_icon_cinema_default);

                ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(MyBtn,"scaleX",1.16f);
                ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(MyBtn,"scaleY",1.16f);
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.playTogether(objectAnimator4,objectAnimator5);
                animatorSet2.setDuration(100);
                animatorSet2.start();
                MyBtn.setImageResource(R.mipmap.com_icon_my_selected);
            }
        });

    }
}
