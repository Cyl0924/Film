package com.bw.movie.myactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.activity.PagerActivity;
import com.bw.movie.api.App;

public class AttentionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        App.biaoshi = 1;
        Intent intent = new Intent(AttentionActivity.this,PagerActivity.class);
        overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
        startActivity(intent);
    }
}
