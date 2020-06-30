package com.example.e_support.impl;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.e_support.MainActivity;
import com.example.e_support.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class BasePager {
    public Activity mActivity;
    public View mRootView;
    public ImageButton btnMenu;
    public TextView tvTitle;
    public FrameLayout flContainer;

    public BasePager(Activity activity){
        this.mActivity = activity;
        this.mRootView = initView();
    }
    public View initView(){
        View view = View.inflate(mActivity, R.layout.layout_basepager,null);
        btnMenu = view.findViewById(R.id.btn_menu);
        tvTitle = view.findViewById(R.id.tv_title);
        flContainer = view.findViewById(R.id.fl_container);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        return view;
    }
    public void toggle(){
        MainActivity activity = (MainActivity) mActivity;
        SlidingMenu menu = activity.getSlidingMenu();
        menu.toggle();
    }
    public void initData(){

    }
}
