package com.example.e_support.impl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.e_support.AboutUsActivity;
import com.example.e_support.LinkToActivity;
import com.example.e_support.LoginActivity;
import com.example.e_support.R;
import com.example.e_support.SettingActivity;
import com.example.e_support.SpecialistActivity;
import com.example.e_support.utils.CacheUtils;

import cn.jpush.im.android.api.JMessageClient;

public class ManagerPager extends BasePager {
    private TextView setting,aboutus,current,linkto;
    public static TextView login;
    public static final int STATE_NOT_LOGIN = 0;
    public static final int STATE_LOGIN = 1;
    public static int currentState = STATE_NOT_LOGIN;
    public static String cacheName = null;
    public ManagerPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.layout_manage,null);
        flContainer.removeAllViews();
        flContainer.addView(view);
        tvTitle.setText("我的");
        //name = view.findViewById(R.id.name);
        setting = view.findViewById(R.id.setting);
        aboutus = view.findViewById(R.id.aboutus);
        login = view.findViewById(R.id.login);
        current = view.findViewById(R.id.current);
        linkto = view.findViewById(R.id.linkto);
        if(currentState == STATE_NOT_LOGIN){
            login.setText("登录/注册");
        }else{
            login.setText("登出");
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentState){
                    case STATE_NOT_LOGIN:
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        mActivity.startActivity(intent);
                        break;
                    case STATE_LOGIN:
                        JMessageClient.logout();
                        login.setText("登录/注册");
                        currentState = STATE_NOT_LOGIN;
                        if(SpecialistActivity.currentAccount != null){
                            SpecialistActivity.currentAccount = null;
                        }
                        break;
                }
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AboutUsActivity.class);
                mActivity.startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SettingActivity.class);
                mActivity.startActivity(intent);
            }
        });
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                if(currentState == STATE_NOT_LOGIN)
                    builder.setTitle("当前账号").setMessage("请先登录/注册").setPositiveButton("确定",null).show();
                else {
                    //String cacheName = CacheUtils.getCache(mActivity,"username");
                    builder.setTitle("当前账号").setMessage(cacheName).setPositiveButton("确定",null).show();
                }
            }
        });
        linkto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, LinkToActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }

}
