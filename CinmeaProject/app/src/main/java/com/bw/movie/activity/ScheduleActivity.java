package com.bw.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.ScheduleAdapter;
import com.bw.movie.api.App;
import com.bw.movie.bean.MoiveDetailsBean;
import com.bw.movie.bean.SechCinemaBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.presenter.Presenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

public class ScheduleActivity extends AppCompatActivity implements ContractInterface.MoiveDetailsView,ContractInterface.RequestGetView{

    ContractInterface.PresenterInterface presenterInterface;
    private SimpleDraweeView ScehSimId;
    private TextView ScehNameId;
    private TextView CinTvDaoyan;
    private TextView CinTvTime;
    private TextView CinTvAddress;
    private TextView CinTvLeixing;
    private RecyclerView ScehRecId;
    private Gson gson;
    private TextView scehCinName;
    private TextView scehCinAdd;
    private ImageView ImgFan;

    private List<SechCinemaBean.ResultBean> mlist = new ArrayList<>();
    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        gson = new Gson();
        ScehSimId = findViewById(R.id.ScehSim_id);
        ScehNameId = findViewById(R.id.ScehName_id);
        CinTvDaoyan = findViewById(R.id.Cin_TvDaoyan);
        CinTvTime = findViewById(R.id.Cin_TvTime);
        CinTvAddress = findViewById(R.id.Cin_TvAddress);
        CinTvLeixing = findViewById(R.id.Cin_TvLeixing);
        scehCinName = findViewById(R.id.ScehCinName);
        scehCinAdd = findViewById(R.id.ScehCinAdd);
        ScehRecId = findViewById(R.id.ScehRec_id);
        ImgFan = findViewById(R.id.Sceh_ImgFan);
        scehCinName.setText(App.CinemaName);
        scehCinAdd.setText(App.CinemaAdd);
        presenterInterface = new Presenter<>(this);
        presenterInterface.MoiveDetailsPresenter(App.MoiveId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("cinemasId",App.CinemasId);
        map.put("movieId",App.MoiveId);
        presenterInterface.RequestGetPresenter("movieApi/movie/v1/findMovieScheduleList",map);
        ImgFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleActivity.this,PayTicketActivity.class);
                startActivity(intent);
            }
        });
        init();
    }


    private void init(){
        LinearLayoutManager manager = new LinearLayoutManager(ScheduleActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        ScehRecId.setLayoutManager(manager);
        adapter = new ScheduleAdapter(ScheduleActivity.this,mlist);
        ScehRecId.setAdapter(adapter);
    }

    public void JumpSelecSeat(String startTime,String endTime,String CinemaHall,int scheduleId,Double Price,int SeatsTotal){
        App.StartTime = startTime;
        App.EndTime = endTime;
        App.CinemaHall = CinemaHall;
        App.ScheduleId = scheduleId;
        App.SeatMoney = Price;
        App.DrawSeat = SeatsTotal;
        Intent intent = new Intent(ScheduleActivity.this,SelectSeatActivity.class);
        startActivity(intent);
    }

    @Override
    public void getMoive(MoiveDetailsBean moiveDetailsBean) {
        ScehSimId.setImageURI(Uri.parse(moiveDetailsBean.getResult().getImageUrl()));
        ScehNameId.setText(moiveDetailsBean.getResult().getName());
        CinTvDaoyan.setText(moiveDetailsBean.getResult().getDirector());
        CinTvTime.setText(moiveDetailsBean.getResult().getDuration());
        CinTvAddress.setText(moiveDetailsBean.getResult().getPlaceOrigin());
        CinTvLeixing.setText(moiveDetailsBean.getResult().getMovieTypes());
    }

    @Override
    public void returnGet(Object obj) {
        SechCinemaBean sechCinemaBean = gson.fromJson(obj.toString(),SechCinemaBean.class);
        //Log.e("tag",sechCinemaBean.getResult().size()+"****");
        mlist.addAll(sechCinemaBean.getResult());
        adapter.notifyDataSetChanged();
    }
}
