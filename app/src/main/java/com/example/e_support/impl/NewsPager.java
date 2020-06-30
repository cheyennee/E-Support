package com.example.e_support.impl;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.example.e_support.MainActivity;
import com.example.e_support.domain.NewsMenu;
import com.example.e_support.fragment.LeftMenuFragment;
import com.example.e_support.impl.DetailPager.BaseDetailPager;
import com.example.e_support.impl.DetailPager.NewsDetailPager;
import com.example.e_support.impl.DetailPager.PhotoDetailPager;
import com.example.e_support.utils.CacheUtils;
import com.example.e_support.utils.GlobalContant;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.xutils.http.RequestParams;
import org.xutils.common.Callback;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.x;

import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

public class NewsPager extends BasePager {
    public ArrayList<BaseDetailPager> mDetailPagers = new ArrayList<>();
    public NewsPager(Activity activity) {
        super(activity);
    }
    public String[] text = {"新闻","组图"};
    @Override
    public void initData() {
        mDetailPagers.add(new NewsDetailPager(mActivity));
        mDetailPagers.add(new PhotoDetailPager(mActivity));
        setDetailPager(0);
    }

    public void setDetailPager(int position){
        BaseDetailPager pager = mDetailPagers.get(position);
        flContainer.removeAllViews();
        flContainer.addView(pager.mRootView);
        tvTitle.setText(text[position]);
        pager.initData();
    }
}
