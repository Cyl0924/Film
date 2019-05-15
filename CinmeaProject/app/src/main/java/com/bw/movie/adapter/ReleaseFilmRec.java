package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.api.App;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.RoateBean;
import com.bw.movie.fragment.CinemaFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class ReleaseFilmRec extends RecyclerView.Adapter<ReleaseFilmRec.holder> {

    List<ReleaseBean.ResultBean> list;
    Context context;
    CinemaFragment cinemaFragment;
    public ReleaseFilmRec(List<ReleaseBean.ResultBean> list, Context context,CinemaFragment cinemaFragment) {
        this.list = list;
        this.context = context;
        this.cinemaFragment = cinemaFragment;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_releasefilm,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int i) {
        holder.sim.setImageURI(list.get(i).getImageUrl());
        //holder.tv.setText("铁血战士      126分钟");
        holder.sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cinemaFragment.JumpDetails();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        @BindView(R.id.ReleaseFilmSim_id)
        SimpleDraweeView sim;
       /* @BindView(R.id.HotFimTv_id)
        TextView tv;*/
        public holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
