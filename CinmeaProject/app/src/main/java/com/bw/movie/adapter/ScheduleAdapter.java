package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.activity.ScheduleActivity;
import com.bw.movie.bean.SechCinemaBean;

import java.util.List;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.holder> {

    Context context;
    List<SechCinemaBean.ResultBean> list;
    ScheduleActivity scheduleActivity;

    public ScheduleAdapter(Context context, List<SechCinemaBean.ResultBean> list) {
        this.context = context;
        this.list = list;
        scheduleActivity = (ScheduleActivity) context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.layout_scehrec,null);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, final int i) {
        holder.YingName.setText(list.get(i).getScreeningHall());
        holder.YingStart.setText(list.get(i).getBeginTime());
        holder.YingEnd.setText(list.get(i).getEndTime()+"  end");
        holder.YingPrice.setText(list.get(i).getPrice()+"");
        holder.Show.setText("￥"+"");
        holder.YingVery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleActivity.JumpSelecSeat(list.get(i).getBeginTime(),list.get(i).getEndTime(),list.get(i).getScreeningHall(),list.get(i).getId(),list.get(i).getPrice(),list.get(i).getSeatsTotal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        private TextView YingName;
        private TextView YingStart;
        private TextView YingEnd;
        private TextView Show;
        private TextView YingPrice;
        private ImageView YingVery;
        public holder(@NonNull View itemView) {
            super(itemView);
            YingName = itemView.findViewById(R.id.Ying_Name);
            YingStart = itemView.findViewById(R.id.Ying_start);
            YingEnd = itemView.findViewById(R.id.Ying_end);
            YingPrice = itemView.findViewById(R.id.Ying_Price);
            YingVery = itemView.findViewById(R.id.Ying_Very);
            Show = itemView.findViewById(R.id.Ying_shou);
        }
    }

}
