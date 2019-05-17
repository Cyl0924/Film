package com.bw.movie.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bw.movie.R;
import com.bw.movie.activity.DetailsActivity;
import com.bw.movie.activity.MainActivity;
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
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;
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
    @BindView(R.id.LinLocation)
    LinearLayout linearLayout;
    @BindView(R.id.LoctionId)
    TextView LocaTv;


    ContractInterface.PresenterInterface presenterInterface;

    private List<ComingBean.ResultBean> Clist = new ArrayList<>();
    private List<ReleaseBean.ResultBean> Rlist = new ArrayList<>();
    private List<RoateBean.ResultBean> mlist = new ArrayList<>();
    private RecFlowAdapter adapter;
    private HotFilmRec hotFilmRec;
    private ReleaseFilmRec releaseFilmRec;
    private ComingFilmRec comingFilmRec;
    private List<RoateBean.ResultBean> olist = new ArrayList<>();

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private String addr;
    private String street;
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.layout_cinema,null);
        ButterKnife.bind(this,view);
        linearLayout.bringToFront();
        presenterInterface = new Presenter(this);
        requestRunPermissions();

        mLocationClient = new LocationClient(App.context);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象

        mLocationClient.start();

        init();

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

        return view;
    }

    private void requestRunPermissions() {
        List<PermissionItem> permisson = new ArrayList<>();
        permisson.add(new PermissionItem(Manifest.permission.READ_PHONE_STATE, "电话状态", R.drawable.permission_ic_phone));
        permisson.add(new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, "地理位置", R.drawable.permission_ic_location));
        permisson.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "地理位置", R.drawable.permission_ic_location));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            permisson.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, "读取权限", R.drawable.permission_ic_storage));
            permisson.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "写入权限", R.drawable.permission_ic_storage));
        }
        HiPermission.create(getActivity())
                .title("开启地图权限")
                .permissions(permisson)
                .msg("我们需要获得以下权限才能为您提供服务")
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                    }

                    @Override
                    public void onFinish() {
//                        showToastShort("所有权限申请完成");
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            //获取详细地址信息
            addr = location.getAddrStr();
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            //获取街道信息
            street = location.getStreet();

        }
    }

    private void init(){

        if(addr == null || street == null){
            LocaTv.setText("正在定位中....");
        }else{
            LocaTv.setText(addr+street);
        }

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
