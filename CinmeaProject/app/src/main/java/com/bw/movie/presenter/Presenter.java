package com.bw.movie.presenter;

import android.util.Log;

import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.model.Model;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：描述信息
 */
public class Presenter implements ContractInterface.PresenterInterface {

    ContractInterface.ViewInterface viewInterface;
    Model model;

    public Presenter(ContractInterface.ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
        model = new Model();
    }


    @Override
    public void RegisterPresnter(String nickName, String phone, String pwd, String pwd2, int sex, String birthday, String email) {
        //Log.e("tag",nickName);
        model.RegisterModel(nickName, phone, pwd, pwd2, sex, birthday,email,new Model.MyCallBcak() {
            @Override
            public void returnData(RegisterBean registerBean) {
                Log.e("tag",registerBean.getMessage());
                viewInterface.RegisterView(registerBean);
            }
        });
    }

    @Override
    public void LoginPresenter(String phone, String pwd) {
        model.LoginModel(phone, pwd, new Model.LoginCallBack() {
            @Override
            public void returnLogin(LoginBean loginBean) {
                viewInterface.RegisterView(loginBean);
            }
        });
    }
}
