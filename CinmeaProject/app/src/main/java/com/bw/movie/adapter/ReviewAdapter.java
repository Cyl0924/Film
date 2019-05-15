package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.MoiveDetailsActivity;
import com.bw.movie.bean.ReviewBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.holder> {

    Context context;
    List<ReviewBean.ResultBean> list;
    MoiveDetailsActivity moiveDetailsActivity;
    public ReviewAdapter(Context context, List<ReviewBean.ResultBean> list) {
        this.context = context;
        this.list = list;
        moiveDetailsActivity = (MoiveDetailsActivity) context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_recview_rec,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder,final int i) {
        holder.sim.setImageURI(Uri.parse(list.get(i).getCommentHeadPic()));
        holder.name.setText(list.get(i).getCommentUserName());
        holder.nums.setText(list.get(i).getGreatNum()+"");
        holder.summery.setText(list.get(i).getCommentContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
        Date date = new Date();
        holder.times.setText(simpleDateFormat.format(date));
        if (list.get(i).getIsGreat() == 0){
            holder.Comm.setImageResource(R.mipmap.com_icon_praise_default);
        }else{
            holder.Comm.setImageResource(R.mipmap.com_icon_praise_selected);
        }
        holder.Comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moiveDetailsActivity.CommenGreat(list.get(i).getCommentId());
                holder.Comm.setImageResource(R.mipmap.com_icon_praise_selected);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{

        @BindView(R.id.PopRev_Sim)
        SimpleDraweeView sim;
        @BindView(R.id.PopRev_Name)
        TextView name;
        @BindView(R.id.PopRev_Summ)
        TextView summery;
        @BindView(R.id.PopRev_Nums)
        TextView nums;
        @BindView(R.id.PopRev_Time)
        TextView times;
        @BindView(R.id.PopRevComm)
        ImageView Comm;
        public holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
