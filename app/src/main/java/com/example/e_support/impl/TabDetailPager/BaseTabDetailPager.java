package com.example.e_support.impl.TabDetailPager;

import android.app.Activity;
import android.view.View;

public abstract class BaseTabDetailPager {
    public Activity mActivity;
    public View mRootView;
    public BaseTabDetailPager(Activity activity){
        this.mActivity = activity;
        mRootView = initView();
    }
    public abstract View initView();

    public void initData(){}
}
