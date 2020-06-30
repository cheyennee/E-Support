package com.example.e_support.JMessage;

import android.app.Application;

import com.example.e_support.chat.GlobalEventListener;

import cn.jpush.im.android.api.JMessageClient;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JMessageClient.setDebugMode(true);
        JMessageClient.init(getApplicationContext(), true);
        JMessageClient.registerEventReceiver(new GlobalEventListener(getApplicationContext()));
    }
}
