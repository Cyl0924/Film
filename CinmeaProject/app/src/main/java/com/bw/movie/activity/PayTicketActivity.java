package com.bw.movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.PayCinemaAdapter;
import com.bw.movie.adapter.PhotoRecAdapter;
import com.bw.movie.api.App;
import com.bw.movie.bean.FollowCinemaBean;
import com.bw.movie.bean.PayCinemaBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.presenter.Presenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayTicketActivity extends AppCompatActivity implements ContractInterface.RequestGetView,ContractInterface.RequestGetViewTwo{


    @BindView(R.id.Pay_Name)
    TextView PayName;
    @BindView(R.id.Pay_Rec)
    RecyclerView PayRec;
    @BindView(R.id.Pay_ImgFan)
    ImageView ImgFan;

    ContractInterface.PresenterInterface presenterInterface;
    private Gson gson;

    private List<PayCinemaBean.ResultBean> PayList = new ArrayList<>();
    private PayCinemaAdapter adapter;
    private HashMap<String, Object> maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ticket);
        ButterKnife.bind(this);
        ImgFan.bringToFront();
        gson = new Gson();
        presenterInterface = new Presenter<>(this);
        PayName.setText(App.MoiveName);
        ImgFan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayTicketActivity.this,DetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
        init();
    }

    private void init() {
        maps = new HashMap<>();
        maps.put("movieId",App.MoiveId);
        presenterInterface.RequestGetPresenter("movieApi/movie/v1/findCinemasListByMovieId",maps);
        LinearLayoutManager manager = new LinearLayoutManager(PayTicketActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        PayRec.setLayoutManager(manager);
        adapter = new PayCinemaAdapter(PayList,PayTicketActivity.this);
        PayRec.setAdapter(adapter);
    }


    public void JumpScheduleList(int CinemasIds,String CinemaName,String CinemaAdd){
        App.CinemasId = CinemasIds;
        App.CinemaName = CinemaName;
        App.CinemaAdd = CinemaAdd;
        Intent intent = new Intent(PayTicketActivity.this,ScheduleActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void returnGet(Object obj) {
        PayCinemaBean payCinemaBean = gson.fromJson(obj.toString(),PayCinemaBean.class);
        PayList.clear();
        PayList.addAll(payCinemaBean.getResult());
        adapter.notifyDataSetChanged();
    }

    public void FollowCinema(int cinemaId){
        HashMap<String,Object> map = new HashMap<>();
        map.put("cinemaId",cinemaId);
        presenterInterface.RequestGetPresenterTwo("movieApi/cinema/v1/verify/followCinema",map);
        presenterInterface.RequestGetPresenter("movieApi/movie/v1/findCinemasListByMovieId",maps);
    }

    public void CancelCinema(int cinemaId){
        HashMap<String,Object> map = new HashMap<>();
        map.put("cinemaId",cinemaId);
        presenterInterface.RequestGetPresenterTwo("movieApi/cinema/v1/verify/cancelFollowCinema",map);
        presenterInterface.RequestGetPresenter("movieApi/movie/v1/findCinemasListByMovieId",maps);
    }


    @Override
    public void returnGetTwo(Object obj) {
        FollowCinemaBean followCinemaBean = gson.fromJson(obj.toString(),FollowCinemaBean.class);
        Toast.makeText(PayTicketActivity.this,followCinemaBean.getMessage(),Toast.LENGTH_SHORT).show();
    }
}
