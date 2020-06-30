package com.example.e_support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.e_support.impl.ManagerPager;
import com.example.e_support.impl.SpecialistPager;
import com.example.e_support.utils.CacheUtils;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText account,password;
    private Button login,register;
    private ImageButton menu,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }
    private void initView(){
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnregister);
        menu = findViewById(R.id.btn_menu);
        menu.setVisibility(View.INVISIBLE);
        back = findViewById(R.id.btn_back);
        back.setVisibility(View.VISIBLE);
    }
    private void initData(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account.getText() == null || password.getText() == null){
                    Toast.makeText(getApplicationContext(),"账号或密码不能为空",Toast.LENGTH_SHORT).show();
                    return ;
                }
                JMessageClient.register(account.getText().toString(), password.getText().toString(), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if(i != 0){
                            Toast.makeText(getApplicationContext(),"注册失败，请重试",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"注册成功，请登录",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JMessageClient.login(account.getText().toString(), password.getText().toString(), new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
                        if(i != 0){
                            Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                            //CacheUtils.setCache(getApplicationContext(),"username",account.getText().toString());
                            //CacheUtils.setCache(getApplicationContext(),"password",password.getText().toString());
                            ManagerPager.currentState = ManagerPager.STATE_LOGIN;
                            ManagerPager.cacheName = account.getText().toString();
                            ManagerPager.login.setText("登出");
                            if(SpecialistActivity.currentAccount == null){
                                SpecialistActivity.currentAccount = new String();
                                SpecialistActivity.currentAccount = account.getText().toString();
                            }else{
                                SpecialistActivity.currentAccount = null;
                                SpecialistActivity.currentAccount = account.getText().toString();
                            }
                            finish();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
