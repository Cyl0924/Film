package com.bw.movie.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;

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

        init();
    }

    private void init() {

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
                presenterInterface.LoginPresenter(loginPhone,miPwd);
            }
        });


        ToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowActivity.this,RegisterActivity.class);
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
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(ShowActivity.this,loginBean.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
