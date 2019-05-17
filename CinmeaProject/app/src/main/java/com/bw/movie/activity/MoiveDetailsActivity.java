package com.bw.movie.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bw.movie.R;
import com.bw.movie.adapter.PhotoRecAdapter;
import com.bw.movie.adapter.ReviewAdapter;
import com.bw.movie.api.App;
import com.bw.movie.bean.CommectBean;
import com.bw.movie.bean.MoiveDetailsBean;
import com.bw.movie.bean.ReviewBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.presenter.Presenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoiveDetailsActivity extends AppCompatActivity implements ContractInterface.MoiveDetailsView ,ContractInterface.ReviewMoiveView ,ContractInterface.ObjectView ,ContractInterface.RequestPostView{

    ContractInterface.PresenterInterface presenterInterface;
    MoiveDetailsBean moiveDetailsBean;
    boolean flag = true;

    @BindView(R.id.Moive_name)
    TextView MoiveName;
    @BindView(R.id.Moive_sim)
    SimpleDraweeView MoiveSim;
    @BindView(R.id.Moive_Details)
    TextView TvDetails;
    @BindView(R.id.Moive_Prevue)
    TextView TvPrevue;
    @BindView(R.id.Moive_photo)
    TextView TvPhoto;
    @BindView(R.id.Moive_Evaluate)
    TextView TvEvaluate;
    @BindView(R.id.Moive_Fan)
    ImageView FanImg;
    @BindView(R.id.Moive_Pay)
    TextView TvPay;
    @BindView(R.id.Moive_love)
    ImageView ImgLove;

    private SimpleDraweeView PopSim;
    private TextView PopName;
    private TextView PopTvDaoyan;
    private TextView PopTvTime;
    private TextView PopTvAddress;
    private TextView PopTvLeixing;
    private ImageView PopDown;
    private TextView PopJianjie;
    private TextView Yan1;
    private TextView Jue1;
    private TextView Yan2;
    private TextView Jue2;
    private TextView Yan3;
    private TextView Jue3;
    private TextView Yan4;
    private TextView Jue4;
    private MediaPlayer player;
    private MediaPlayer player1;
    private MediaPlayer player2;
    private SurfaceView sf1;
    private SurfaceView sf2;
    private SurfaceView sf3;
    private SimpleDraweeView sim1;
    private SimpleDraweeView sim2;
    private SimpleDraweeView sim3;
    private List<ReviewBean.ResultBean> RevList = new ArrayList<>();
    private ReviewAdapter adapter;
    private int moiveIds;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moivedetails);
        ButterKnife.bind(this);
        gson = new Gson();
        presenterInterface = new Presenter<>(this);
        final Intent intent = getIntent();
        moiveIds = intent.getIntExtra("moiveId",0);
        presenterInterface.MoiveDetailsPresenter(moiveIds);
        init();
        //查影评数据
        ImgLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moiveDetailsBean.getResult().getFollowMovie() != 2){
                    ImgLove.setImageResource(R.mipmap.com_icon_collection_default);
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("movieId",moiveIds);
                    App.Biaoshi = 1;
                    presenterInterface.FollowPresenter("movieApi/movie/v1/verify/cancelFollowMovie",map);
                }else{
                    ImgLove.setImageResource(R.mipmap.com_icon_collection_selected);
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("movieId",moiveIds);
                    App.Biaoshi = 1;
                    presenterInterface.FollowPresenter("movieApi/movie/v1/verify/followMovie",map);
                }
            }
        });
        TvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MoiveDetailsActivity.this,PayTicketActivity.class);
                App.MoiveId = moiveIds;
                App.MoiveName = moiveDetailsBean.getResult().getName();
                startActivity(intent1);
                finish();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MoiveDetailsActivity.this,DetailsActivity.class);
        startActivity(intent);
        finish();
    }

    private void init() {

        player = new MediaPlayer();
        player1 = new MediaPlayer();
        player2 = new MediaPlayer();


        final List<String> Stringlist = new ArrayList<>();

        FanImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MoiveDetailsActivity.this,DetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = View.inflate(MoiveDetailsActivity.this,R.layout.layout_detailspopu,null);
                final PopupWindow popupWindow = new PopupWindow(view1,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                PopSim =  view1.findViewById(R.id.Pop_Sim);
                PopSim.bringToFront();
                PopName =  view1.findViewById(R.id.Pop_name);
                PopTvDaoyan =  view1.findViewById(R.id.Pop_TvDaoyan);
                PopTvTime =  view1.findViewById(R.id.Pop_TvTime);
                PopTvAddress =  view1.findViewById(R.id.Pop_TvAddress);
                PopTvLeixing =  view1.findViewById(R.id.Pop_TvLeixing);
                PopDown =  view1.findViewById(R.id.Pop_down);
                PopJianjie =  view1.findViewById(R.id.Pop_Jianjie);
                Yan1 =  view1.findViewById(R.id.Yan_1);
                Jue1 =  view1.findViewById(R.id.Jue_1);
                Yan2 =  view1.findViewById(R.id.Yan_2);
                Jue2 =  view1.findViewById(R.id.Jue_2);
                Yan3 =  view1.findViewById(R.id.Yan_3);
                Jue3 =  view1.findViewById(R.id.Jue_3);
                Yan4 =  view1.findViewById(R.id.Yan_4);
                Jue4 =  view1.findViewById(R.id.Jue_4);
                PopSim.setImageURI(moiveDetailsBean.getResult().getImageUrl());
                PopName.setText(moiveDetailsBean.getResult().getName());
                PopTvDaoyan.setText(moiveDetailsBean.getResult().getDirector());
                PopTvTime.setText(moiveDetailsBean.getResult().getDuration());
                PopTvAddress.setText(moiveDetailsBean.getResult().getPlaceOrigin());
                PopTvLeixing.setText(moiveDetailsBean.getResult().getMovieTypes());
                PopDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                PopJianjie.setText(moiveDetailsBean.getResult().getSummary());
                String startimg = moiveDetailsBean.getResult().getStarring();
                String arr[] = startimg.split(",");
                Yan1.setText(arr[0]+"");
                Jue1.setText("男主角一号");
                Yan2.setText(arr[1]+"");
                Jue2.setText("男主角二号");
                Yan3.setText(arr[2]+"");
                Jue3.setText("女主角一号");
                Yan4.setText(arr[3]+"");
                Jue4.setText("女主角二号");
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(view1,Gravity.CENTER,0,0);
            }
        });


        /* *********************************分界线********************************* */

        TvPrevue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player == null || player1 == null || player2 == null){
                    player = new MediaPlayer();
                    player1 = new MediaPlayer();
                    player2 = new MediaPlayer();
                }
                View view2 = View.inflate(MoiveDetailsActivity.this,R.layout.layout_pupprevue,null);
                final PopupWindow popupWindow = new PopupWindow(view2,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView Down = view2.findViewById(R.id.PrevueDown);
                sf1 = view2.findViewById(R.id.PrevueSf_1);
                sf2 = view2.findViewById(R.id.PrevueSf_2);
                sf3 = view2.findViewById(R.id.PrevueSf_3);
                sim1 = view2.findViewById(R.id.Sf1Sim);
                sim1.bringToFront();
                sim2 = view2.findViewById(R.id.Sf2Sim);
                sim2.bringToFront();
                sim3 = view2.findViewById(R.id.Sf3Sim);
                sim3.bringToFront();
                sim1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sim1.setVisibility(View.GONE);
                        player.start();
                    }
                });
                sim2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sim2.setVisibility(View.GONE);
                        player1.start();
                    }
                });
                sim3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sim3.setVisibility(View.GONE);
                        player2.start();
                    }
                });
                Down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(player.isPlaying() || player1.isPlaying() || player2.isPlaying()){
                            player.release();
                            player1.release();
                            player2.release();
                            player = null;
                            player1 = null;
                            player2 = null;
                        }
                        if (player != null || player1 != null || player2 != null) {
                            player.stop();
                            player1.stop();
                            player2.stop();
                        }
                        popupWindow.dismiss();
                    }
                });
                List<MoiveDetailsBean.ResultBean.ShortFilmListBean> list = moiveDetailsBean.getResult().getShortFilmList();

                if(list.size() == 3){
                    try {
                        player.reset();
                        sim1.setImageURI(Uri.parse(list.get(0).getImageUrl()));
                        player.setDataSource(MoiveDetailsActivity.this,Uri.parse(list.get(0).getVideoUrl()));
                        SurfaceHolder holder = sf1.getHolder();
                        holder.addCallback(new Sf1Call());
                        player.prepare();
                        player1.reset();
                        sim2.setImageURI(Uri.parse(list.get(1).getImageUrl()));
                        player1.setDataSource(MoiveDetailsActivity.this,Uri.parse(list.get(1).getVideoUrl()));
                        SurfaceHolder holder1 = sf2.getHolder();
                        holder1.addCallback(new Sf2Call());
                        player1.prepare();
                        player2.reset();
                        sim3.setImageURI(Uri.parse(list.get(2).getImageUrl()));
                        player2.setDataSource(MoiveDetailsActivity.this,Uri.parse(list.get(2).getVideoUrl()));
                        SurfaceHolder holder2 = sf3.getHolder();
                        holder2.addCallback(new Sf3Call());
                        player2.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(list.size() == 2){
                    try {
                        sf3.setVisibility(View.GONE);
                        player.reset();
                        sim2.setImageURI(Uri.parse(list.get(1).getImageUrl()));
                        player.setDataSource(MoiveDetailsActivity.this,Uri.parse(list.get(0).getVideoUrl()));
                        SurfaceHolder holder = sf1.getHolder();
                        holder.addCallback(new Sf1Call());
                        player.prepare();
                        player1.reset();
                        sim3.setImageURI(Uri.parse(list.get(2).getImageUrl()));
                        player1.setDataSource(MoiveDetailsActivity.this,Uri.parse(list.get(1).getVideoUrl()));
                        SurfaceHolder holder1 = sf2.getHolder();
                        holder1.addCallback(new Sf2Call());
                        player1.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        sf2.setVisibility(View.GONE);
                        sf3.setVisibility(View.GONE);
                        player.reset();
                        sim3.setImageURI(Uri.parse(list.get(2).getImageUrl()));
                        player.setDataSource(MoiveDetailsActivity.this,Uri.parse(list.get(0).getVideoUrl()));
                        SurfaceHolder holder = sf1.getHolder();
                        holder.addCallback(new Sf1Call());
                        player.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(view2,Gravity.CENTER,0,0);
            }
        });

        /*  *******************************分界线****************************** */
        TvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view3 = View.inflate(MoiveDetailsActivity.this,R.layout.layout_photo,null);
                final PopupWindow popupWindow = new PopupWindow(view3,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
                ImageView ImgDown = view3.findViewById(R.id.PhotoDown);
                ImgDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                RecyclerView PhotoRec = view3.findViewById(R.id.PhotoRec);
                StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
               // layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                PhotoRec.setLayoutManager(layoutManager);
                Stringlist.clear();
                Stringlist.addAll(moiveDetailsBean.getResult().getPosterList());
                PhotoRecAdapter adapter = new PhotoRecAdapter(MoiveDetailsActivity.this,Stringlist);
                PhotoRec.setAdapter(adapter);

                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(view3,Gravity.CENTER,0,0);
            }
        });


        TvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View view4 = View.inflate(MoiveDetailsActivity.this,R.layout.layout_review,null);
                ImageView ReviewDown = view4.findViewById(R.id.ReviewDown);
                RecyclerView RevRec = view4.findViewById(R.id.ReviewRec);
                ImageView ReviewPing = view4.findViewById(R.id.ReviewPing);

                final PopupWindow popupWindow = new PopupWindow(view4,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
                ReviewDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                ReviewPing.setOnClickListener(new View.OnClickListener() {

                    private InputMethodManager imm;
                    private EditText editText;
                    private PopupWindow popupWindowChild;

                    @Override
                    public void onClick(View view) {
                        View viewChild = View.inflate(MoiveDetailsActivity.this,R.layout.layout_popchildping,null);
                        popupWindowChild = new PopupWindow(viewChild,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
                        editText = viewChild.findViewById(R.id.Ed_Ppinglun);
                        TextView textView = viewChild.findViewById(R.id.Tv_Tijiao);
                        editText.setFocusable(true);
                        imm = (InputMethodManager)MoiveDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindowChild.dismiss();
                                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                                String ed = editText.getText().toString().trim();
                                HashMap<String,Object> Smap = new HashMap<>();
                                Smap.put("movieId",moiveIds);
                                Smap.put("commentContent",ed);


                                presenterInterface.ObjectPresenter(Smap);
                            }
                        });
                        popupWindowChild.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        popupWindowChild.setOutsideTouchable(true);
                        popupWindowChild.showAtLocation(viewChild,Gravity.CENTER,0,0);
                    }
                });

                HashMap<String,Integer> map = new HashMap<>();
                map.put("movieId",moiveIds);
                map.put("page",1);
                map.put("count",100);
                presenterInterface.ReviewPresenter(map);
                LinearLayoutManager manager = new LinearLayoutManager(MoiveDetailsActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                RevRec.setLayoutManager(manager);
                adapter = new ReviewAdapter(MoiveDetailsActivity.this,RevList);
                RevRec.setAdapter(adapter);

                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAtLocation(view4,Gravity.CENTER,0,0);
            }
        });

    }

    public void CommenGreat(int commentId){
        HashMap<String,Object> map = new HashMap<>();
        map.put("commentId",commentId);
        presenterInterface.RequestPostPresenter("movieApi/movie/v1/verify/movieCommentGreat",map);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getReview(ReviewBean reviewBean) {
        RevList.clear();
        RevList.addAll(reviewBean.getResult());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getObjectData(Object obj) {
        CommectBean commectBean = (CommectBean)obj;
        if(commectBean.getMessage().equals("评论成功")){
            Toast.makeText(MoiveDetailsActivity.this,commectBean.getMessage(),Toast.LENGTH_SHORT).show();
            presenterInterface.MoiveDetailsPresenter(moiveIds);
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(MoiveDetailsActivity.this,commectBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void returnPost(Object obj) {
        CommectBean commectBean = gson.fromJson(obj.toString(),CommectBean.class);
        Toast.makeText(MoiveDetailsActivity.this,commectBean.getMessage(),Toast.LENGTH_SHORT).show();
    }


    public class Sf1Call implements  SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            player.setDisplay(surfaceHolder);
            sf1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player.isPlaying()){
                        player.pause();
                    }else{
                        player.start();
                    }
                }
            });
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

    public class Sf2Call implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            player1.setDisplay(surfaceHolder);
            sf2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player1.isPlaying()){
                        player1.pause();
                    }else{
                        player1.start();
                    }
                }
            });
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

    public class Sf3Call implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            player2.setDisplay(surfaceHolder);
            sf3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player2.isPlaying()){
                        player2.pause();
                    }else{
                        player2.start();
                    }
                }
            });
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

    @Override
    public void getMoive(MoiveDetailsBean moiveDetailsBean) {
        this.moiveDetailsBean = moiveDetailsBean;
        if(moiveDetailsBean.getResult().getFollowMovie() == 2){
            ImgLove.setImageResource(R.mipmap.com_icon_collection_default);
        }else{
            ImgLove.setImageResource(R.mipmap.com_icon_collection_selected);
        }
        MoiveName.setText(moiveDetailsBean.getResult().getName());
        MoiveSim.setImageURI(moiveDetailsBean.getResult().getImageUrl());
    }

}
