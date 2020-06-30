package com.example.e_support.impl.TabDetailPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.e_support.NewsActivity;
import com.example.e_support.R;
import com.example.e_support.domain.GovListView;
import com.example.e_support.domain.GovViewPager;
import com.example.e_support.utils.CacheUtils;
import com.example.e_support.utils.GlobalContant;
import com.example.e_support.utils.PrefUtils;
import com.example.e_support.view.CustomViewPager;
import com.example.e_support.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class GovNewsTabDetailPager extends BaseTabDetailPager {
    private View view;
    //listview
    private RefreshListView listView;
    private GovListView serverData = new GovListView();
    private ArrayList<GovListView.NewsListData> data = new ArrayList<>();
    private ListViewAdapter adapter = new ListViewAdapter();
    private String moreUrl;
    //viewpager
    private CustomViewPager viewPager;
    private CirclePageIndicator indicator;
    private TextView tvTitle;
    private GovViewPager pagers = new GovViewPager();
    private ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
    private Handler mHandler;
    private ArrayList<GovViewPager.Pager> pagerData = new ArrayList<>();

    private static final int DATA_LIST_VIEW = 0;
    private static final int DATA_VIEW_PAGER = 1;
    public GovNewsTabDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        view = View.inflate(mActivity, R.layout.gov_news_tab_detail_pager,null);
        View headerView = View.inflate(mActivity,R.layout.tab_detail_viewpager,null);
        viewPager = headerView.findViewById(R.id.vp_tab);
        indicator = headerView.findViewById(R.id.indicator);
        tvTitle = headerView.findViewById(R.id.tv_title);
        listView = view.findViewById(R.id.lv_list);
        listView.addHeaderView(headerView);
        return view;
    }

    @Override
    public void initData() {
        viewPager.setAdapter(viewPagerAdapter);
        String cachePager = CacheUtils.getCache(mActivity,GlobalContant.HTTP_PATH + GlobalContant.GOV_VIEW_PAGER);
        if(cachePager != null){
            processData(DATA_VIEW_PAGER,cachePager,false);
        }else{
            getDataFromServer(DATA_VIEW_PAGER,GlobalContant.HTTP_PATH + GlobalContant.GOV_VIEW_PAGER);
        }
        indicator.setViewPager(viewPager);
        indicator.setSnap(true);
        indicator.onPageSelected(0);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tvTitle.setText(pagers.data.get(position).title);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置轮播
        if(mHandler == null){
            mHandler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    int currentItem = viewPager.getCurrentItem();
                    if(currentItem < pagers.data.size() - 1){
                        currentItem++;
                    }else{
                        currentItem = 0;
                    }
                    viewPager.setCurrentItem(currentItem);
                    viewPagerAdapter.notifyDataSetChanged();
                    mHandler.sendEmptyMessageDelayed(0,2000);
                }
            };
            mHandler.sendEmptyMessageDelayed(0,2000);
        }
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        //停止轮播
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        //当按住头条新闻后，突然上下滑动listview，导致当前viewpager事件被取消，不响应ACTION_UP
                        mHandler.sendEmptyMessageDelayed(0,2000);
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0,2000);
                        break;
                }
                return false;
            }
        });
        String cache = CacheUtils.getCache(mActivity,GlobalContant.HTTP_PATH+GlobalContant.GOV_LIST_VIEW);
        if(cache != null){
            processData(DATA_LIST_VIEW,cache,false);
        }else{
            getDataFromServer(DATA_LIST_VIEW,GlobalContant.HTTP_PATH + GlobalContant.GOV_LIST_VIEW);
        }
        listView.setAdapter(adapter);
        listView.setRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreDataFromServer();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int headerViewsCount = listView.getHeaderViewsCount();
                position -= headerViewsCount;
                GovListView.NewsListData news = data.get(position);
                int newsId = news.id;
                String readids = PrefUtils.getString(mActivity,"gov_read_ids","");
                if(readids.contains(String.valueOf(newsId))){
                    readids += String.valueOf(newsId) + ",";
                    PrefUtils.putString(mActivity,"gov_read_ids",readids);
                }
                TextView tv = view.findViewById(R.id.tv_title);
                tv.setTextColor(Color.GRAY);
                Intent intent = new Intent(mActivity, NewsActivity.class);
                intent.putExtra("url",data.get(position).url);
                mActivity.startActivity(intent);
            }
        });
    }

    class ViewPagerAdapter extends PagerAdapter{

        private BitmapUtils mBitmapUtils;
        public ViewPagerAdapter(){
            mBitmapUtils = new BitmapUtils(mActivity);
            mBitmapUtils.configDefaultLoadingImage(R.drawable.news_default);
        }
        @Override
        public int getCount() {
            return pagerData.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(imageView,pagers.data.get(position).image);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
    class ListViewAdapter extends BaseAdapter{

        private BitmapUtils mBitmapUtils;
        public ListViewAdapter(){
            mBitmapUtils = new BitmapUtils(mActivity);
            mBitmapUtils.configDefaultLoadingImage(R.drawable.news_default);
        }
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoder hoder = new ViewHoder();
            if(convertView == null){
                convertView = View.inflate(mActivity,R.layout.gov_news_tab_list_item,null);
                hoder.imageView = convertView.findViewById(R.id.iv_icon);
                hoder.title = convertView.findViewById(R.id.tv_title);
                hoder.time = convertView.findViewById(R.id.tv_time);
                convertView.setTag(hoder);
            }else{
                hoder = (ViewHoder) convertView.getTag();
            }
            hoder.title.setText(data.get(position).title);
            hoder.time.setText(data.get(position).pubdate);
            if(data.get(position).image.equals("")){
                hoder.imageView.setImageResource(R.drawable.news_default);
            }else
                mBitmapUtils.display(hoder.imageView,data.get(position).image);
            return convertView;
        }
    }
    class ViewHoder {
        public ImageView imageView;
        public TextView title,time;
    }
    private void getDataFromServer(final int type,final String url){
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        processData(type,result,false);
                        CacheUtils.setCache(mActivity,url,result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();
                    }

                });
    }
    private void processData(final int type,String result,boolean isMore){

        Gson gson = new Gson();
        if(type == DATA_LIST_VIEW){
            serverData = gson.fromJson(result,GovListView.class);
            moreUrl = serverData.more;
            if(!isMore){
                this.data = serverData.data;
                return;
            }else{
                this.data.addAll(serverData.data);
                adapter.notifyDataSetChanged();
            }
        }else{
            pagers = gson.fromJson(result,GovViewPager.class);
            pagerData = pagers.data;
            viewPagerAdapter.notifyDataSetChanged();
        }

    }
    private void getMoreDataFromServer(){
        if(moreUrl.equals("")){
            Toast.makeText(mActivity,"没有更多数据了",Toast.LENGTH_SHORT).show();
            listView.onRefreshComplete();
            return ;
        }
        final String url = GlobalContant.HTTP_PATH + moreUrl;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String >() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        processData(DATA_LIST_VIEW,result,true);
                        CacheUtils.setCache(mActivity,url,result);
                        listView.onRefreshComplete();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();
                        listView.onRefreshComplete();
                    }
                });
    }
}
