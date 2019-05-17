package com.bw.movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.DetailsAdapter;
import com.bw.movie.api.App;
import com.bw.movie.bean.CommectBean;
import com.bw.movie.bean.FollowBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.presenter.Presenter;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements ContractInterface.AllView {


    @BindView(R.id.Detail_hotFilm)
    TextView HotFilm;
    @BindView(R.id.Detail_ReleseFilm)
    TextView ReleseFilm;
    @BindView(R.id.Detail_ComingFilm)
    TextView ComingFilm;
    @BindView(R.id.Detalis_RecId)
    RecyclerView Rec;
    @BindView(R.id.Fanhui_img)
    ImageView FanImg;

    ContractInterface.PresenterInterface presenterInterface;
    private DetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        presenterInterface = new Presenter<>(this);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailsActivity.this,PagerActivity.class);
        startActivity(intent);
        finish();
    }

    private void init() {

        FanImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this,PagerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        HotFilm.setBackgroundResource(R.drawable.corners_tvselect);
        ReleseFilm.setBackgroundResource(R.drawable.corners_tv);
        ComingFilm.setBackgroundResource(R.drawable.corners_tv);
        LinearLayoutManager manager = new LinearLayoutManager(DetailsActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        Rec.setLayoutManager(manager);
        adapter = new DetailsAdapter(App.AClist,App.ARlist,App.AHlist,1,DetailsActivity.this);
        Rec.setAdapter(adapter);

        HotFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotFilm.setBackgroundResource(R.drawable.corners_tvselect);
                ReleseFilm.setBackgroundResource(R.drawable.corners_tv);
                ComingFilm.setBackgroundResource(R.drawable.corners_tv);
                LinearLayoutManager manager = new LinearLayoutManager(DetailsActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                Rec.setLayoutManager(manager);
                adapter = new DetailsAdapter(App.AClist,App.ARlist,App.AHlist,1,DetailsActivity.this);
                Rec.setAdapter(adapter);
            }
        });

        ReleseFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotFilm.setBackgroundResource(R.drawable.corners_tv);
                ReleseFilm.setBackgroundResource(R.drawable.corners_tvselect);
                ComingFilm.setBackgroundResource(R.drawable.corners_tv);
                LinearLayoutManager manager = new LinearLayoutManager(DetailsActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                Rec.setLayoutManager(manager);
                adapter = new DetailsAdapter(App.AClist,App.ARlist,App.AHlist,2,DetailsActivity.this);
                Rec.setAdapter(adapter);
            }
        });

        ComingFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HotFilm.setBackgroundResource(R.drawable.corners_tv);
                ReleseFilm.setBackgroundResource(R.drawable.corners_tv);
                ComingFilm.setBackgroundResource(R.drawable.corners_tvselect);
                LinearLayoutManager manager = new LinearLayoutManager(DetailsActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                Rec.setLayoutManager(manager);
                adapter = new DetailsAdapter(App.AClist,App.ARlist,App.AHlist,3,DetailsActivity.this);
                Rec.setAdapter(adapter);
            }
        });
    }

    public void FollowMoive(int movieId){
        //Log.e("tag",movieId+"");
        HashMap<String,Object> map = new HashMap<>();
        map.put("movieId",movieId);
        presenterInterface.FollowPresenter("movieApi/movie/v1/verify/followMovie",map);
        App.Biaoshi = 1;
    }

    public void FollowCancel(int movieId){
        HashMap<String,Object> map = new HashMap<>();
        map.put("movieId",movieId);
        presenterInterface.FollowPresenter("movieApi/movie/v1/verify/cancelFollowMovie",map);
        App.Biaoshi = 1;
    }

    public void JumpDetailsActivity(int movieId){
        Intent intent = new Intent(DetailsActivity.this,MoiveDetailsActivity.class);
        intent.putExtra("moiveId",movieId);
        startActivity(intent);
        finish();
    }


    @Override
    public void AllData(Object obj) {
        Log.e("tag","obj");
        FollowBean commectBean = (FollowBean)obj;
        Toast.makeText(DetailsActivity.this,commectBean.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
