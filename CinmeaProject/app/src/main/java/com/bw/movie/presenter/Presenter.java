package com.bw.movie.presenter;

import android.util.Log;

import com.bw.movie.bean.ComingBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MoiveDetailsBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.ReviewBean;
import com.bw.movie.bean.RoateBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class Presenter<T> implements ContractInterface.PresenterInterface {

    Model model;
    T tt;
    public Presenter(T t) {
        this.tt = t;
        model = new Model();
    }


    @Override
    public void RegisterPresnter(String nickName, String phone, String pwd, String pwd2, int sex, String birthday, String email) {
        //Log.e("tag",nickName);
        model.RegisterModel(nickName, phone, pwd, pwd2, sex, birthday,email,new Model.MyCallBcak() {
            @Override
            public void returnData(RegisterBean registerBean) {
                Log.e("tag",registerBean.getMessage());
                ContractInterface.ViewInterface  viewInterface = (ContractInterface.ViewInterface) tt;
                viewInterface.RegisterView(registerBean);
            }
        });
    }

    @Override
    public void LoginPresenter(String phone, String pwd) {
        model.LoginModel(phone, pwd, new Model.LoginCallBack() {
            @Override
            public void returnLogin(LoginBean loginBean) {
                ContractInterface.ViewInterface  viewInterface = (ContractInterface.ViewInterface) tt;
                viewInterface.RegisterView(loginBean);
            }
        });
    }

    @Override
    public void DataPresenter(HashMap<String , Integer> map) {
        model.DataMode(map, new Model.ToDataCall() {
            @Override
            public void returnDatas(RoateBean roateBean) {
                ContractInterface.ViewInterface  viewInterface = (ContractInterface.ViewInterface) tt;
                viewInterface.RegisterView(roateBean);
            }
        });
    }

    @Override
    public void DataReleasePresenter(HashMap<String, Integer> map) {
        model.DataReleaseModel(map, new Model.ToRelease() {
            @Override
            public void returnRelease(ReleaseBean releaseBean) {
                ContractInterface.ReleaseFilm releaseFilm = (ContractInterface.ReleaseFilm) tt;
                releaseFilm.getRelease(releaseBean);
            }
        });
    }

    @Override
    public void DataComingPresenter(HashMap<String, Integer> map) {
        model.DataComingModel(map, new Model.ToComing() {
            @Override
            public void returnComing(ComingBean comingBean) {
                ContractInterface.ComingFilm comingFilm = (ContractInterface.ComingFilm) tt;
                comingFilm.getComing(comingBean);
            }
        });
    }

    @Override
    public void MoiveDetailsPresenter(int movieId) {
        model.MovieDetailsModel(movieId, new Model.MoiveCall() {
            @Override
            public void returnMoive(MoiveDetailsBean moiveDetailsBean) {
                ContractInterface.MoiveDetailsView moiveDetailsView = (ContractInterface.MoiveDetailsView) tt;
                moiveDetailsView.getMoive(moiveDetailsBean);
            }
        });
    }

    @Override
    public void ReviewPresenter(HashMap<String, Integer> map) {
        model.ReviewModel(map, new Model.ReviewCall() {
            @Override
            public void returnReview(ReviewBean reviewBean) {
                ContractInterface.ReviewMoiveView reviewMoiveView = (ContractInterface.ReviewMoiveView) tt;
                reviewMoiveView.getReview(reviewBean);
            }
        });
    }

    @Override
    public void ObjectPresenter(HashMap<String,Object> Smap) {
        model.ObjectModel(Smap, new Model.ObjectCall() {
            @Override
            public void returnObject(Object obj) {
                ContractInterface.ObjectView objectView = (ContractInterface.ObjectView) tt;
                objectView.getObjectData(obj);
            }
        });
    }

    @Override
    public void FollowPresenter(String url , HashMap<String, Object> map) {
        //Log.e("tag","url = " +url);
        model.FollowModel(url, map, new Model.AllCall() {
            @Override
            public void returnAll(Object obj) {
                ContractInterface.AllView allView = (ContractInterface.AllView) tt;
                allView.AllData(obj);
            }
        });
    }

    @Override
    public void RequestPostPresenter(String url, HashMap<String, Object> map) {
        model.RequestPostModel(url, map, new Model.RequestPostCall() {
            @Override
            public void returnPost(Object obj) {
                ContractInterface.RequestPostView requestPostView = (ContractInterface.RequestPostView) tt;
                requestPostView.returnPost(obj);
            }
        });
    }

    @Override
    public void RequestGetPresenter(String url, HashMap<String, Object> map) {
        model.RequestGetModel(url, map, new Model.RequestGetCall() {
            @Override
            public void returnGet(Object obj) {
                ContractInterface.RequestGetView requestGetView = (ContractInterface.RequestGetView) tt;
                requestGetView.returnGet(obj);
            }
        });
    }

    @Override
    public void RequestGetPresenterTwo(String url, HashMap<String, Object> map) {
        model.RequestGetModelTwo(url, map, new Model.RequestGetCallTwo() {
            @Override
            public void returnGetTwo(Object obj) {
                ContractInterface.RequestGetViewTwo requestGetViewTwo = (ContractInterface.RequestGetViewTwo) tt;
                requestGetViewTwo.returnGetTwo(obj);
            }
        });
    }

    @Override
    public void Destory() {
        tt = null;
    }
}
