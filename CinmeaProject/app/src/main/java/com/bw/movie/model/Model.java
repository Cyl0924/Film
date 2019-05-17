package com.bw.movie.model;
import android.util.Log;

import com.bw.movie.api.App;
import com.bw.movie.bean.ComingBean;
import com.bw.movie.bean.CommectBean;
import com.bw.movie.bean.FollowBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MoiveDetailsBean;
import com.bw.movie.bean.PayTrueBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.ReviewBean;
import com.bw.movie.bean.RoateBean;
import com.bw.movie.bean.WTrueBean;
import com.bw.movie.bean.WechatBean;
import com.bw.movie.bean.WechatLoginBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.util.RetrofitUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observer;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class Model implements ContractInterface.ModelInterface {

    MyCallBcak myCallBcak;
    LoginCallBack loginCallBack;
    ToDataCall toDataCall;
    ToRelease toRelease;
    ToComing toComing;
    MoiveCall moiveCall;
    ReviewCall reviewCall;
    ObjectCall objectCall;
    AllCall allCall;
    RequestPostCall requestPostCall;
    RequestGetCall requestGetCall;
    RequestGetCallTwo requestGetCallTwo;
    WeiChatCall weiChatCall;
    PayCall payCall;
    WeiXinTrueCall weiXinTrueCall;
    ZfbCall zfbCall;




    Gson gson = new Gson();

    //注册
    @Override
    public void RegisterModel(String nickName, String phone, String pwd, String pwd2, int sex, String birthday , String email, final MyCallBcak myCallBcak) {
        this.myCallBcak = myCallBcak;
        //Log.e("tag",phone);
        RetrofitUtil.getUtil().Register("movieApi/user/v1/registerUser", nickName, phone, pwd, pwd2, sex, birthday,email, new Observer<RegisterBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                //Log.e("tag",e.getMessage());
            }

            @Override
            public void onNext(RegisterBean registerBean) {
                //Log.e("tag",registerBean.getMessage()+"1");
                myCallBcak.returnData(registerBean);
            }
        });
    }


    //登录
    @Override
    public void LoginModel(String phone, String pwd, final LoginCallBack loginCallBack) {
        this.loginCallBack = loginCallBack;
        RetrofitUtil.getUtil().Login("movieApi/user/v1/login", phone, pwd, new Observer<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(LoginBean loginBean) {
                loginCallBack.returnLogin(loginBean);
            }
        });
    }

    //旋转木马
    @Override
    public void DataMode(HashMap<String , Integer> map, final ToDataCall toDataCall) {
        this.toDataCall = toDataCall;
        RetrofitUtil.getUtil().ToData("movieApi/movie/v1/findHotMovieList", App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jaon = responseBody.string();
                    RoateBean roateBean = gson.fromJson(jaon,RoateBean.class);
                    toDataCall.returnDatas(roateBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //热映
    @Override
    public void DataReleaseModel(HashMap<String, Integer> map, final ToRelease toRelease) {
        this.toRelease = toRelease;
        RetrofitUtil.getUtil().ToData("movieApi/movie/v1/findReleaseMovieList", App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jaon = responseBody.string();
                    ReleaseBean roateBean = gson.fromJson(jaon,ReleaseBean.class);
                    toRelease.returnRelease(roateBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //即将上映
    @Override
    public void DataComingModel(HashMap<String, Integer> map, final ToComing toComing) {
        this.toComing = toComing;
        RetrofitUtil.getUtil().ToData("movieApi/movie/v1/findComingSoonMovieList", App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String jaon = responseBody.string();
                    ComingBean comingBean = gson.fromJson(jaon,ComingBean.class);
                    toComing.returnComing(comingBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //影片详情
    @Override
    public void MovieDetailsModel(int movieId, final MoiveCall moiveCall) {
        this.moiveCall = moiveCall;
        RetrofitUtil.getUtil().ToIntData("movieApi/movie/v1/findMoviesDetail", App.userId, App.sessionId, movieId, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    //Log.e("tag",json);
                    MoiveDetailsBean moiveDetailsBean = gson.fromJson(json,MoiveDetailsBean.class);
                    //Log.e("tags",moiveDetailsBean.getMessage()+"model");
                    moiveCall.returnMoive(moiveDetailsBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //影评
    @Override
    public void ReviewModel(HashMap<String, Integer> map, final ReviewCall reviewCall) {
        this.reviewCall = reviewCall;
        RetrofitUtil.getUtil().toIntMap("movieApi/movie/v1/findAllMovieComment", App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    ReviewBean reviewBean = gson.fromJson(json,ReviewBean.class);
                    reviewCall.returnReview(reviewBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void ObjectModel(HashMap<String,Object> Smap,final ObjectCall objectCall) {
        this.objectCall = objectCall;
        Log.e("tag",Smap.get("movieId")+"");
        RetrofitUtil.getUtil().objectData("movieApi/movie/v1/verify/movieComment", App.userId, App.sessionId, Smap, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    CommectBean commectBean = gson.fromJson(json,CommectBean.class);
                    objectCall.returnObject(commectBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void FollowModel(String url, HashMap<String, Object> map, final AllCall allCall) {
        this.allCall = allCall;
        RetrofitUtil.getUtil().AllGet(url, App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    FollowBean commectBean = gson.fromJson(json,FollowBean.class);
                    allCall.returnAll(commectBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void RequestPostModel(String url, HashMap<String, Object> map, final RequestPostCall requestPostCall) {
        this.requestPostCall = requestPostCall;
        RetrofitUtil.getUtil().objectData(url, App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    requestPostCall.returnPost(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void RequestGetModel(String url, HashMap<String, Object> map, final RequestGetCall requestGetCall) {
        this.requestGetCall = requestGetCall;
        RetrofitUtil.getUtil().AllGet(url, App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    //Log.e("tag",json);
                    requestGetCall.returnGet(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void RequestGetModelTwo(String url, HashMap<String, Object> map, final RequestGetCallTwo requestGetCallTwo) {
        this.requestGetCallTwo = requestGetCallTwo;
        RetrofitUtil.getUtil().AllGet(url, App.userId, App.sessionId, map, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    requestGetCallTwo.returnGetTwo(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void ToWechatModel(String url, String code ,final WeiChatCall weiChatCall) {
        this.weiChatCall = weiChatCall;
        RetrofitUtil.getUtil().ToWeichat(url, code, new Observer<WechatLoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WechatLoginBean wechatLoginBean) {
                weiChatCall.returnWechat(wechatLoginBean);
            }
        });
    }

    @Override
    public void PayModel(String url, int scheduleId, int amount, String sign, final PayCall payCall) {
        this.payCall = payCall;
        RetrofitUtil.getUtil().toPay(url, scheduleId, amount, sign, new Observer<PayTrueBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PayTrueBean payTrueBean) {
                payCall.returnPay(payTrueBean);
            }
        });
    }

    @Override
    public void WeiXinPayTrue(String url, int payType, String orderId, final WeiXinTrueCall weiXinTrueCall) {
        this.weiXinTrueCall = weiXinTrueCall;
        RetrofitUtil.getUtil().toWpay(url, payType, orderId, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    weiXinTrueCall.returnWeiXin(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void ZfbModel(String url, int payType, String orderId, final ZfbCall zfbCall) {
        this.zfbCall = zfbCall;
        RetrofitUtil.getUtil().toWpay(url, payType, orderId, new Observer<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    zfbCall.returnZfb(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public interface ZfbCall{
        public void returnZfb(Object obj);
    }

    public interface WeiXinTrueCall{
        public void returnWeiXin(Object obj);
    }

    public interface PayCall{
        public void returnPay(PayTrueBean payTrueBean);
    }

    public interface WeiChatCall{
        public void returnWechat(WechatLoginBean wechatLoginBean);
    }

    public interface RequestGetCallTwo{
        public void returnGetTwo(Object obj);
    }

    public interface RequestGetCall{
        public void returnGet(Object obj);
    }

    public interface RequestPostCall{
        public void returnPost(Object obj);
    }

    public interface AllCall{
        public void returnAll(Object obj);
    }

    public interface MyCallBcak{
        public void returnData(RegisterBean registerBean);
    }

    public interface LoginCallBack{
        public void returnLogin(LoginBean loginBean);
    }

    public interface ToDataCall{
        public void returnDatas(RoateBean roateBean);
    }

    public interface ToRelease{
        public void returnRelease(ReleaseBean releaseBean);
    }

    public interface ToComing{
        public void returnComing(ComingBean comingBean);
    }

    public interface MoiveCall{
        public void returnMoive(MoiveDetailsBean moiveDetailsBean);
    }

    public interface ReviewCall{
        public void returnReview(ReviewBean reviewBean);
    }

    public interface ObjectCall{
        public void returnObject(Object obj);
    }

}
