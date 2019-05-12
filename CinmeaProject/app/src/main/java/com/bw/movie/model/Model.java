package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.util.RetrofitUtil;

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


    public interface MyCallBcak{
        public void returnData(RegisterBean registerBean);
    }

    public interface LoginCallBack{
        public void returnLogin(LoginBean loginBean);
    }

}
