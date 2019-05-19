package com.bw.movie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.api.App;
import com.bw.movie.myactivity.AttentionActivity;
import com.bw.movie.myactivity.FeedbackActivity;
import com.bw.movie.myactivity.MessageActivity;
import com.bw.movie.myactivity.RccordActivity;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class MyFragment extends Fragment {

    private ImageView ImgRemind;
    private SimpleDraweeView MySim;
    private TextView MyNameTv;
    private LinearLayout MyMessage;
    private LinearLayout MyAttention;
    private LinearLayout MyRccord;
    private LinearLayout MyFeedback;
    private LinearLayout MyVersion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_my,null);
        ImgRemind = view.findViewById(R.id.ImgRemind);
        MySim = view.findViewById(R.id.MySim);
        MyNameTv = view.findViewById(R.id.MyNameTv);
        MyMessage = view.findViewById(R.id.My_Message);
        MyAttention = view.findViewById(R.id.My_Attention);
        MyRccord = view.findViewById(R.id.My_Rccord);
        MyFeedback = view.findViewById(R.id.My_Feedback);
        MyVersion = view.findViewById(R.id.My_Version);
        MySim.setImageURI(Uri.parse(App.headPic));
        MyNameTv.setText(App.nickName);
        init();
        return view;
    }

    private void init() {

        MyVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"已经是最新版本",Toast.LENGTH_SHORT).show();
            }
        });

        MyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MessageActivity.class);
                getActivity().overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                startActivity(intent);
                getActivity().finish();
            }
        });
        MyFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),FeedbackActivity.class);
                getActivity().overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                startActivity(intent);
                getActivity().finish();
            }
        });
        MyAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AttentionActivity.class);
                getActivity().overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                startActivity(intent);
                getActivity().finish();
            }
        });
        MyRccord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),RccordActivity.class);
                getActivity().overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }


}
