package com.bw.movie.contract;

import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.model.Model;

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
    }

    public interface PresenterInterface{
        public void RegisterPresnter(String nickName , String phone , String pwd , String pwd2 , int sex , String birthday, String email);
        public void LoginPresenter(String phone, String pwd);
    }

    public interface ViewInterface{
        public void RegisterView(Object obj);
    }

}
