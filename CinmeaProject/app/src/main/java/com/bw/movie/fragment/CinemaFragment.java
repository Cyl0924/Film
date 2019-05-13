package com.bw.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.RecFlowAdapter;
import com.bw.movie.api.App;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RoateBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.presenter.Presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class CinemaFragment extends Fragment implements ContractInterface.ViewInterface {

    @BindView(R.id.RecFlow_id)
    RecyclerCoverFlow RecFlow;

    ContractInterface.PresenterInterface presenterInterface;

    private List<RoateBean.ResultBean> mlist = new ArrayList<>();
    private RecFlowAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_cinema,null);
        ButterKnife.bind(this,view);
        presenterInterface = new Presenter(this);
        HashMap<String , Integer> map = new HashMap<>();
        map.put("page",1);
        map.put("count",7);
        presenterInterface.DataPresenter(map);
        //Log.e("tag", App.userId+"---"+App.sessionId);
        init();
        return view;
    }

    private void init(){
        adapter = new RecFlowAdapter(getActivity(),mlist);
        RecFlow.setAdapter(adapter);
    }

    @Override
    public void RegisterView(Object obj) {
        RoateBean roateBean = (RoateBean)obj;
        mlist.addAll(roateBean.getResult());
        adapter.notifyDataSetChanged();
    }


}
