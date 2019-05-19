package com.bw.movie.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.api.App;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.presenter.Presenter;
import com.bw.movie.util.EncryptUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;

public class ShowActivity extends AppCompatActivity implements ContractInterface.ViewInterface {

    ContractInterface.PresenterInterface presenterInterface;
    @BindView(R.id.Login_phone)
    EditText phone;
    @BindView(R.id.Login_pwd)
    EditText pwd;
    @BindView(R.id.rember_pwd)
    CheckBox RemberPwd;
    @BindView(R.id.automatic_login)
    CheckBox AutomaticLogin;
    @BindView(R.id.to_register)
    TextView ToRegister;
    @BindView(R.id.to_wechat)
    ImageView ToWechat;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        presenterInterface = new Presenter(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if(sp.getBoolean("rember",false) && sp.getBoolean("automaticLogin",false)){
            String lphone = sp.getString("phone", null);
            String lpwd = sp.getString("pwd", null);
            presenterInterface.LoginPresenter(lphone,lpwd);
            RemberPwd.setChecked(true);
            AutomaticLogin.setChecked(true);
        }

        if(sp.getBoolean("rember",false)){
            String rphone = sp.getString("phone", null);
            String rpwd = sp.getString("pwd", null);
            String decrpty = EncryptUtil.decrypt(rpwd);
            phone.setText(rphone);
            pwd.setText(decrpty);
            RemberPwd.setChecked(true);
        }else{
            phone.setText(null);
            pwd.setText(null);
            RemberPwd.setChecked(false);
        }

        regToWx();
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(ShowActivity.this,"退出程序!",Toast.LENGTH_SHORT).show();
        android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
        System.exit(0);
    }



    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
    }

    public static class Utils {
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private static long lastClickTime;

        public static boolean isFastClick() {
            boolean flag = false;
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                flag = true;
            }
            lastClickTime = curClickTime;
            return flag;
        }
    }


    private void init() {

        ToWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = sp.edit();
                String loginPhone = phone.getText().toString().trim();
                String loginPwd = pwd.getText().toString().trim();
                String miPwd = EncryptUtil.encrypt(loginPwd);
                boolean isCk = RemberPwd.isChecked();
                boolean automaticLogin = AutomaticLogin.isChecked();
                if(automaticLogin){
                    editor.putBoolean("automaticLogin",true);
                    editor.commit();
                }
                if(isCk){
                    editor.putBoolean("rember",true);
                    editor.putString("phone",loginPhone);
                    editor.putString("pwd",miPwd);
                    editor.commit();
                }else{
                    editor.putBoolean("rember",false);
                    editor.putString("phone",null);
                    editor.putString("pwd",null);
                    editor.commit();
                }
                if(Utils.isFastClick()){
                    //to do sth
                    presenterInterface.LoginPresenter(loginPhone,miPwd);
                }
            }
        });


        ToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowActivity.this,RegisterActivity.class);
                overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
                startActivity(intent);
            }
        });
    }

    @Override
    public void RegisterView(Object obj) {
        LoginBean loginBean = (LoginBean)obj;
        if(loginBean.getMessage().equals("登陆成功")){
            Toast.makeText(ShowActivity.this,loginBean.getMessage(),Toast.LENGTH_SHORT).show();
            App.sessionId = loginBean.getResult().getSessionId();
            App.userId = loginBean.getResult().getUserId();
            App.birthday = loginBean.getResult().getUserInfo().getBirthday();
            App.headPic = loginBean.getResult().getUserInfo().getHeadPic();
            App.ids = loginBean.getResult().getUserInfo().getId();
            App.lastLoginTime = loginBean.getResult().getUserInfo().getLastLoginTime();
            App.nickName = loginBean.getResult().getUserInfo().getNickName();
            App.phones = loginBean.getResult().getUserInfo().getPhone();
            App.sex = loginBean.getResult().getUserInfo().getSex();
            Intent intent = new Intent(ShowActivity.this,PagerActivity.class);
            overridePendingTransition(R.anim.anim_right,R.anim.anim_left);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(ShowActivity.this,loginBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
