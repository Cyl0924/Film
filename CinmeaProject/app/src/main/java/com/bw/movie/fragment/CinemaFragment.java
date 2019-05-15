package com.bw.movie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.adapter.ComingFilmRec;
import com.bw.movie.adapter.HotFilmRec;
import com.bw.movie.adapter.RecFlowAdapter;
import com.bw.movie.adapter.ReleaseFilmRec;
import com.bw.movie.api.App;
import com.bw.movie.bean.ComingBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.ReleaseBean;
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
public class CinemaFragment extends Fragment implements ContractInterface.ViewInterface , ContractInterface.ReleaseFilm , ContractInterface.ComingFilm{

    @BindView(R.id.RecFlow_id)
    RecyclerCoverFlow RecFlow;
    @BindView(R.id.HotRec_id)
    RecyclerView HotRec;
    @BindView(R.id.ReleaseFilm_id)
    RecyclerView ReleaseRec;
    @BindView(R.id.ComingFilm_id)
    RecyclerView ComingRec;

    ContractInterface.PresenterInterface presenterInterface;

    private List<ComingBean.ResultBean> Clist = new ArrayList<>();
    private List<ReleaseBean.ResultBean> Rlist = new ArrayList<>();
    private List<RoateBean.ResultBean> mlist = new ArrayList<>();
    private RecFlowAdapter adapter;
    private HotFilmRec hotFilmRec;
    private ReleaseFilmRec releaseFilmRec;
    private ComingFilmRec comingFilmRec;
    private List<RoateBean.ResultBean> olist = new ArrayList<>();

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
        presenterInterface.DataReleasePresenter(map);
        presenterInterface.DataComingPresenter(map);
        if(App.Biaoshi == 1){
            presenterInterface.DataPresenter(map);
            presenterInterface.DataReleasePresenter(map);
            presenterInterface.DataComingPresenter(map);
            App.Biaoshi = 0;
        }
        init();
        return view;
    }

    private void init(){
        adapter = new RecFlowAdapter(getActivity(),mlist);
        RecFlow.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        HotRec.setLayoutManager(manager);
        hotFilmRec = new HotFilmRec(mlist,getActivity(),this);
        HotRec.setAdapter(hotFilmRec);

        //热映
        LinearLayoutManager manager1 = new LinearLayoutManager(getActivity());
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        ReleaseRec.setLayoutManager(manager1);
        releaseFilmRec = new ReleaseFilmRec(Rlist,getActivity(),this);
        ReleaseRec.setAdapter(releaseFilmRec);

        //即将上映
        LinearLayoutManager manager2 = new LinearLayoutManager(getActivity());
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        ComingRec.setLayoutManager(manager2);
        comingFilmRec = new ComingFilmRec(Clist,getActivity(),this);
        ComingRec.setAdapter(comingFilmRec);

    }

    public void JumpDetails(){
        Intent intent = new Intent(getActivity(),DetailsActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void RegisterView(Object obj) {
        RoateBean roateBean = (RoateBean)obj;
        olist.clear();
        olist.addAll(roateBean.getResult());
        App.AHlist = olist;
        mlist.addAll(olist);
        adapter.notifyDataSetChanged();
        hotFilmRec.notifyDataSetChanged();
    }


    @Override
    public void getRelease(ReleaseBean releaseBean) {
        Rlist.clear();
        Rlist.addAll(releaseBean.getResult());
        App.ARlist = Rlist;
        releaseFilmRec.notifyDataSetChanged();
    }

    @Override
    public void getComing(ComingBean comingBean) {
        Clist.clear();
        Clist.addAll(comingBean.getResult());
        App.AClist = Clist;
        comingFilmRec.notifyDataSetChanged();
    }
}
