package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.util.RetrofitUtil;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int i=5;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                i--;
                if(i == 0){
                    editor = sp.edit();
                    editor.putBoolean("guidance",true);
                    editor.commit();
                }
            }
        }
    };

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ImageView imageView = findViewById(R.id.Guiden_id);
        if (sp.getBoolean("guidance",false) == true){
            Toast.makeText(MainActivity.this,"点击进入主页面",Toast.LENGTH_SHORT).show();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean b =RetrofitUtil.getUtil().isNetworkConnected(MainActivity.this);
                    if(b){
                        Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                        overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(MainActivity.this,NetworkActivity.class);
                        overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                        startActivity(intent);
                    }
                }
            });
        }else{
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            },0,1000);
        }
    }
}
