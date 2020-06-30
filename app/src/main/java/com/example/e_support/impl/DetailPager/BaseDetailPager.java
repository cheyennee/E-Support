package com.example.e_support.impl.DetailPager;

import android.app.Activity;
import android.view.View;

public abstract class BaseDetailPager {
    public Activity mActivity;
    public View mRootView;
    public BaseDetailPager(Activity activity){
        mActivity = activity;
        mRootView = initView();
    }
    public abstract View initView();
    public void initData(){}
}
