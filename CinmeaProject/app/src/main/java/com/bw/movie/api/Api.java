package com.bw.movie.api;

import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public interface Api {

    //http://mobile.bwstudent.com/movieApi/movie/v1/findHotMovieList

    //注册接口
    @FormUrlEncoded
    @POST
    public Observable<RegisterBean> doPost(@Url String url , @Field("nickName") String nickName, @Field("phone") String phone, @Field("pwd") String pwd, @Field("pwd2") String pwd2, @Field("sex") int sex, @Field("birthday") String birthday, @Field("email") String email);

    //登录接口
    @FormUrlEncoded
    @POST
    public Observable<LoginBean> doLogin(@Url String url , @Field("phone") String phone , @Field("pwd") String pwd);

    //常用请求数据接口
    @GET
    public Observable<ResponseBody> doGet(@Url String url , @Header("userId") int userId , @Header("sessionId") String sessionId , @QueryMap HashMap<String,Integer> map);
}
