package com.bw.movie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuidanceActivity extends AppCompatActivity {

    //int[] img = {R.mipmap.yi,R.mipmap.er,R.mipmap.san,R.mipmap.si};

    private List<Integer> list = new ArrayList();

    @BindView(R.id.Guiden_Vp)
    ViewPager Vp;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                int i = Vp.getCurrentItem();
                i++;
                Vp.setCurrentItem(i);
                handler.sendEmptyMessageDelayed(0,1500);
                if(i == 3){
                    Toast.makeText(GuidanceActivity.this,"点击进入主页面",Toast.LENGTH_SHORT).show();
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean b =RetrofitUtil.getUtil().isNetworkConnected(GuidanceActivity.this);
                            if(b){
                                Intent intent = new Intent(GuidanceActivity.this,ShowActivity.class);
                                overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(GuidanceActivity.this,NetworkActivity.class);
                                overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        }
    };
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidance);
        ButterKnife.bind(this);
        list.add(R.mipmap.yi);
        list.add(R.mipmap.er);
        list.add(R.mipmap.san);
        list.add(R.mipmap.si);
        MyAdapter myAdapter = new MyAdapter();
        Vp.setAdapter(myAdapter);

        handler.sendEmptyMessageDelayed(0,1500);
    }

    class MyAdapter extends PagerAdapter{
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            imageView = new ImageView(GuidanceActivity.this);
            imageView.setImageResource(list.get(position));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }

}
