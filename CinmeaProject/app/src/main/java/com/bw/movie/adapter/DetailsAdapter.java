package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.bean.ComingBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.RoateBean;
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
public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.holder> {

    List<ComingBean.ResultBean> clist;
    List<ReleaseBean.ResultBean> rlist;
    List<RoateBean.ResultBean> slist;
    int s;
    Context context;
    DetailsActivity detailsActivity;
    public DetailsAdapter(List<ComingBean.ResultBean> clist, List<ReleaseBean.ResultBean> rlist, List<RoateBean.ResultBean> slist, int s, Context context) {
        this.clist = clist;
        this.rlist = rlist;
        this.slist = slist;
        this.s = s;
        this.context = context;
        detailsActivity = (DetailsActivity) context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_detalis,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder, final int i) {
        holder.lin.bringToFront();
        if(s == 1){
            holder.sim.setImageURI(slist.get(i).getImageUrl());
            holder.name.setText(slist.get(i).getName());
            holder.detalis.setText(slist.get(i).getSummary());
            if(slist.get(i).getFollowMovie() != 2){
                holder.Love.setImageResource(R.mipmap.com_icon_collection_selected);
            }else{
                holder.Love.setImageResource(R.mipmap.com_icon_collection_default);
            }
            holder.sim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsActivity.JumpDetailsActivity(slist.get(i).getId());
                }
            });
            holder.Love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(slist.get(i).getFollowMovie() == 2){
                        holder.Love.setImageResource(R.mipmap.com_icon_collection_selected);
                        detailsActivity.FollowMoive(slist.get(i).getId());
                    }else{
                        holder.Love.setImageResource(R.mipmap.com_icon_collection_default);
                        detailsActivity.FollowCancel(slist.get(i).getId());
                    }
                }
            });
        }else if(s == 2){
            holder.sim.setImageURI(clist.get(i).getImageUrl());
            holder.name.setText(clist.get(i).getName());
            holder.detalis.setText(clist.get(i).getSummary());
            if(clist.get(i).getFollowMovie() != 2){
                holder.Love.setImageResource(R.mipmap.com_icon_collection_selected);
            }else{
                holder.Love.setImageResource(R.mipmap.com_icon_collection_default);
            }
            holder.sim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsActivity.JumpDetailsActivity(clist.get(i).getId());
                }
            });
            holder.Love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(clist.get(i).getFollowMovie() == 2){
                        holder.Love.setImageResource(R.mipmap.com_icon_collection_selected);
                        detailsActivity.FollowMoive(clist.get(i).getId());
                    }else{
                        holder.Love.setImageResource(R.mipmap.com_icon_collection_default);
                        detailsActivity.FollowCancel(clist.get(i).getId());
                    }
                }
            });
        }else{

            holder.sim.setImageURI(rlist.get(i).getImageUrl());
            holder.name.setText(rlist.get(i).getName());
            holder.detalis.setText(rlist.get(i).getSummary());
            if(rlist.get(i).getFollowMovie() != 2){
                holder.Love.setImageResource(R.mipmap.com_icon_collection_selected);
            }else{
                holder.Love.setImageResource(R.mipmap.com_icon_collection_default);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsActivity.JumpDetailsActivity(rlist.get(i).getId());
                }
            });
            holder.Love.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rlist.get(i).getFollowMovie() == 2){
                        holder.Love.setImageResource(R.mipmap.com_icon_collection_selected);
                        detailsActivity.FollowMoive(rlist.get(i).getId());
                    }else{
                        holder.Love.setImageResource(R.mipmap.com_icon_collection_default);
                        detailsActivity.FollowCancel(rlist.get(i).getId());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        if(s == 1){
            return slist.size();
        }else if(s == 2){
            return clist.size();
        }else{
            return rlist.size();
        }

    }

    public class holder extends RecyclerView.ViewHolder{

        @BindView(R.id.Lin_Details)
        LinearLayout lin;
        @BindView(R.id.XQ_sim)
        SimpleDraweeView sim;
        @BindView(R.id.XQ_name)
        TextView name;
        @BindView(R.id.XQ_details)
        TextView detalis;
        @BindView(R.id.XQ_love)
        ImageView Love;

        public holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
