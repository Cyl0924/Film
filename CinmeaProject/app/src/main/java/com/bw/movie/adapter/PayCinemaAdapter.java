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
import com.bw.movie.activity.PayTicketActivity;
import com.bw.movie.bean.PayCinemaBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class PayCinemaAdapter extends RecyclerView.Adapter<PayCinemaAdapter.holder> {

    List<PayCinemaBean.ResultBean> list;
    Context context;
    PayTicketActivity payTicketActivity;

    public PayCinemaAdapter(List<PayCinemaBean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
        payTicketActivity = (PayTicketActivity) context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.layout_recpaycinema,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder, final int i) {
        holder.PaySim.setImageURI(Uri.parse(list.get(i).getLogo()));
        holder.PayCinName.setText(list.get(i).getName());
        holder.PayCinAdd.setText(list.get(i).getAddress());
        holder.PayCinKm.setText(list.get(i).getId()+"Km");
        if(list.get(i).getFollowCinema() != 0){
            holder.PayCinLove.setImageResource(R.mipmap.com_icon_collection_selected);
        }else{
            holder.PayCinLove.setImageResource(R.mipmap.com_icon_collection_default);
        }
        holder.PayCinLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(i).getFollowCinema() == 0){
                    payTicketActivity.FollowCinema(list.get(i).getId());
                    holder.PayCinLove.setImageResource(R.mipmap.com_icon_collection_selected);
                }else{
                    payTicketActivity.CancelCinema(list.get(i).getId());
                    holder.PayCinLove.setImageResource(R.mipmap.com_icon_collection_default);
                }
            }
        });

        holder.PaySim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payTicketActivity.JumpScheduleList(list.get(i).getId(),list.get(i).getName(),list.get(i).getAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        private SimpleDraweeView PaySim;
        private TextView PayCinName;
        private TextView PayCinAdd;
        private TextView PayCinKm;
        private ImageView PayCinLove;
        public holder(@NonNull View itemView) {
            super(itemView);
;
            PaySim = itemView.findViewById(R.id.PaySim);
            PayCinName = itemView.findViewById(R.id.PayCinName);
            PayCinAdd = itemView.findViewById(R.id.PayCinAdd);
            PayCinKm = itemView.findViewById(R.id.PayCinKm);
            PayCinLove = itemView.findViewById(R.id.PayCinLove);

        }
    }

}
