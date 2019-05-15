package com.bw.movie.util;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class RetrofitUtil {

    private static RetrofitUtil util;
    private  Retrofit retrofit;
    private  Api api;

    private RetrofitUtil(){
        retrofit = new Retrofit.Builder()
                    .baseUrl("http://mobile.bwstudent.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    public static RetrofitUtil getUtil(){
        if (util == null){
            synchronized (RetrofitUtil.class){
                if(util == null){
                    util = new RetrofitUtil();
                }
            }
        }
        return util;
    }

    public void Register(String url , String nickName , String phone , String pwd , String pwd2 , int sex , String birthday , String email ,Observer<RegisterBean> observer){
        //Log.e("tag",url);
        Observable observable = api.doPost(url,nickName,phone,pwd,pwd2,sex,birthday,email);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void Login(String url , String phone , String pwd , Observer<LoginBean> observer){
        Observable observable = api.doLogin(url,phone,pwd);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void ToData(String url, int userId , String sessionId, HashMap<String , Integer> map, Observer<ResponseBody> observer){
        Observable observable = api.doGet(url,userId,sessionId,map);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void ToIntData(String url, int userId , String sessionId, int movieId, Observer<ResponseBody> observer){
        Observable observable = api.toData(url,userId,sessionId,movieId);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void toIntMap(String url, int userId , String sessionId,HashMap<String,Integer> map,Observer<ResponseBody> observer){
        Observable observable = api.toIntData(url,userId,sessionId,map);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    public void objectData(String url, int userId , String sessionId,HashMap<String,Object> Smap,Observer<ResponseBody> observer){
        Observable observable = api.ObjectData(url,userId,sessionId,Smap);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

    }

    public void AllGet(String url, int userId , String sessionId,HashMap<String,Object> Smap,Observer<ResponseBody> observer){
        Observable observable = api.AllData(url,userId,sessionId,Smap);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

}
