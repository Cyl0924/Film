package com.bw.movie.contract;

import com.bw.movie.bean.ComingBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.MoiveDetailsBean;
import com.bw.movie.bean.PayTrueBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.ReviewBean;
import com.bw.movie.bean.WTrueBean;
import com.bw.movie.bean.WechatBean;
import com.bw.movie.bean.WechatLoginBean;
import com.bw.movie.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public interface ContractInterface {

    public interface ModelInterface{
        public void RegisterModel(String nickName , String phone , String pwd , String pwd2 , int sex , String birthday  , String email , Model.MyCallBcak myCallBcak);
        public void LoginModel(String phone, String pwd, Model.LoginCallBack loginCallBack);
        public void DataMode(HashMap<String , Integer> map, Model.ToDataCall toDataCall);
        public void DataReleaseModel(HashMap<String , Integer> map, Model.ToRelease toRelease);
        public void DataComingModel(HashMap<String , Integer> map, Model.ToComing toComing);
        public void MovieDetailsModel(int movieId, Model.MoiveCall moiveCall);
        public void ReviewModel(HashMap<String,Integer> map, Model.ReviewCall reviewCall);
        public void ObjectModel(HashMap<String,Object> Smap, Model.ObjectCall objectCall);
        public void FollowModel(String url,HashMap<String,Object> map, Model.AllCall allCall);
        public void RequestPostModel(String url, HashMap<String,Object> map, Model.RequestPostCall requestPostCall);
        public void RequestGetModel(String url, HashMap<String,Object> map, Model.RequestGetCall requestGetCall);
        public void RequestGetModelTwo(String url, HashMap<String,Object> map, Model.RequestGetCallTwo requestGetCallTwo);
        public void ToWechatModel(String url,String code, Model.WeiChatCall weiChatCall);
        public void PayModel(String url, int scheduleId, int amount, String sign, Model.PayCall payCall);
        public void WeiXinPayTrue(String url, int payType, String orderId, Model.WeiXinTrueCall weiXinTrueCall);
        public void ZfbModel(String url, int payType, String orderId, Model.ZfbCall zfbCall);
    }

    public interface PresenterInterface{
        public void RegisterPresnter(String nickName , String phone , String pwd , String pwd2 , int sex , String birthday, String email);
        public void LoginPresenter(String phone, String pwd);
        public void DataPresenter(HashMap<String , Integer> map);
        public void DataReleasePresenter(HashMap<String , Integer> map);
        public void DataComingPresenter(HashMap<String , Integer> map);
        public void MoiveDetailsPresenter(int movieId);
        public void ReviewPresenter(HashMap<String,Integer> map);
        public void ObjectPresenter(HashMap<String,Object> Smap);
        public void FollowPresenter(String url ,HashMap<String,Object> map);
        public void RequestPostPresenter(String url,HashMap<String,Object> map);
        public void RequestGetPresenter(String url, HashMap<String,Object> map);
        public void RequestGetPresenterTwo(String url, HashMap<String,Object> map);
        public void ToWechatPresenter(String url,String code);
        public void PayPresenter(int scheduleId, int amount, String sign);
        public void WeiXinPayTrueP(int payType, String orderId);
        public void ZfbPresenter(int payType, String orderId);
        public void Destory();
    }

    public interface ZfbView{
        public void returnZfb(Object obj);
    }
    public interface WeiXinPayTrue{
        public void returnTrue(WTrueBean wTrueBean);
    }

    public interface PayTrueView{
        public void returnPay(PayTrueBean payTrueBean);
    }

    public interface ToWeiChatView{
        public void returnWechat(WechatLoginBean wechatLoginBean);
    }

    public interface RequestGetView{
        public void returnGet(Object obj);
    }

    public interface RequestGetViewTwo{
        public void returnGetTwo(Object obj);
    }

    public interface RequestPostView{
        public void returnPost(Object obj);
    }

    public interface AllView{
        public void AllData(Object obj);
    }

    public interface ViewInterface{
        public void RegisterView(Object obj);
    }

    public interface ReleaseFilm{
        public void getRelease(ReleaseBean releaseBean);
    }

    public interface ComingFilm{
        public void getComing(ComingBean comingBean);
    }

    public interface MoiveDetailsView{
        public void getMoive(MoiveDetailsBean moiveDetailsBean);
    }

    public interface ReviewMoiveView{
       public void getReview(ReviewBean reviewBean);
    }

    public interface ObjectView{
        public void getObjectData(Object obj);
    }

}
