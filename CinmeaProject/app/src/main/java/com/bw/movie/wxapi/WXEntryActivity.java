package com.bw.movie.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.activity.PagerActivity;
import com.bw.movie.api.App;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.WechatBean;
import com.bw.movie.bean.WechatLoginBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.presenter.Presenter;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @Author：Chen
 * @E-mail： 1850915912@qq.com
 * @Date：2019/3/15 11:29
 * @Description：微信回调
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler ,ContractInterface.ToWeiChatView {

    ContractInterface.PresenterInterface presenterInterface;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_toweichat);
        presenterInterface = new Presenter<>(this);
        regToWx();
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(),this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH){
            //Log.e("tag","微信登录");
            SendAuth.Resp authResp = (SendAuth.Resp) baseResp;
            String code = authResp.code;
            //Log.e("tag",code);
            presenterInterface.ToWechatPresenter("movieApi/user/v1/weChatBindingLogin",code);
        }
    }


    @Override
    public void returnWechat(WechatLoginBean wechatLoginBean) {
        if(wechatLoginBean.getMessage().length() != 0){
            //Log.e("tag", wechatLoginBean.getResult().getSessionId()+"*********");
            App.sessionId = wechatLoginBean.getResult().getSessionId();
            App.userId = wechatLoginBean.getResult().getUserId();
            App.birthday = wechatLoginBean.getResult().getUserInfo().getBirthday();
            App.headPic = wechatLoginBean.getResult().getUserInfo().getHeadPic();
            App.ids = wechatLoginBean.getResult().getUserInfo().getId();
            App.lastLoginTime = wechatLoginBean.getResult().getUserInfo().getLastLoginTime();
            App.nickName = wechatLoginBean.getResult().getUserInfo().getNickName();
            App.phones = wechatLoginBean.getResult().getUserInfo().getPhone();
            App.sex = wechatLoginBean.getResult().getUserInfo().getSex();
            Toast.makeText(WXEntryActivity.this,wechatLoginBean.getMessage(),Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(WXEntryActivity.this,PagerActivity.class);
            startActivity(intent1);
        }else{
            //Log.e("tag",wechatLoginBean.getMessage());
            Toast.makeText(WXEntryActivity.this,wechatLoginBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
