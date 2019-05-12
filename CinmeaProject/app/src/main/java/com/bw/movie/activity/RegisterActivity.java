package com.bw.movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.contract.ContractInterface;
import com.bw.movie.contract.ContractInterface.ViewInterface;
import com.bw.movie.presenter.Presenter;
import com.bw.movie.util.EncryptUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements ViewInterface {

    ContractInterface.PresenterInterface presenterInterface;

    @BindView(R.id.regis_name)
    EditText name;
    @BindView(R.id.regis_sex)
    EditText sex;
    @BindView(R.id.regis_data)
    EditText birthday;
    @BindView(R.id.regis_phone)
    EditText phone;
    @BindView(R.id.regis_email)
    EditText email;
    @BindView(R.id.regis_pwd)
    EditText pwd;
    @BindView(R.id.btn_regis)
    Button btnRegister;
    int sexI;
    private String regisName;
    private String regisSex;
    private String regisBirthday;
    private String regisPhone;
    private String regisEmail;
    private String regisPwd;
    private String miPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenterInterface = new Presenter(this);
        init();
    }

    private void init() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regisName = name.getText().toString().trim();
                regisSex = sex.getText().toString().trim();
                regisBirthday = birthday.getText().toString().trim();
                regisPhone = phone.getText().toString().trim();
                regisEmail = email.getText().toString().trim();
                regisPwd = pwd.getText().toString().trim();
                miPwd = EncryptUtil.encrypt(regisPwd);
                if (regisSex.equals("男")){
                    sexI = 1;
                }else{
                    sexI = 2;
                }
                //Log.e("tag",regisName+regisPhone+miPwd+sexI+regisBirthday+regisEmail);
                presenterInterface.RegisterPresnter(regisName, regisPhone, miPwd, miPwd,sexI, regisBirthday, regisEmail);
            }
        });
    }

    @Override
    public void RegisterView(Object obj) {
        RegisterBean registerBean = (RegisterBean)obj;
        Toast.makeText(RegisterActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
        if(registerBean.getMessage().equals("注册成功")){
            Intent intent = new Intent(RegisterActivity.this,ShowActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
