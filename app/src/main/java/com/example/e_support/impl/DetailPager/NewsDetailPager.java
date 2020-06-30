package com.example.e_support.impl.DetailPager;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.e_support.MainActivity;
import com.example.e_support.R;
import com.example.e_support.impl.TabDetailPager.BaseTabDetailPager;
import com.example.e_support.impl.TabDetailPager.CountyNewTabDetailPager;
import com.example.e_support.impl.TabDetailPager.GovNewsTabDetailPager;
import com.example.e_support.impl.TabDetailPager.SupportNewsTabDetailPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

public class NewsDetailPager extends BaseDetailPager implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TabPageIndicator indicator;
    private String[] text = {"国务院扶贫","湖北省扶贫","市县扶贫"};
    private ArrayList<BaseTabDetailPager> mTabDetailPagers = new ArrayList<>();
    public NewsDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.news_detail_pager,null);
        viewPager = view.findViewById(R.id.vp_news_menu_detail);
        indicator = view.findViewById(R.id.indicator);
        return view;
    }

    @Override
    public void initData() {
        mTabDetailPagers.add(new GovNewsTabDetailPager(mActivity));
        mTabDetailPagers.add(new SupportNewsTabDetailPager(mActivity));
        mTabDetailPagers.add(new CountyNewTabDetailPager(mActivity));
        viewPager.setAdapter(new MyViewPagerAdapter());
        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(this);
    }

    private void setSlidingMenuEnable(boolean enable){
        MainActivity activity = (MainActivity) mActivity;
        SlidingMenu menu = activity.getSlidingMenu();
        if(enable){
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 0){
            setSlidingMenuEnable(true);
        }else{
            setSlidingMenuEnable(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyViewPagerAdapter extends PagerAdapter{

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return text[position];
        }

        @Override
        public int getCount() {
            return mTabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BaseTabDetailPager pager = mTabDetailPagers.get(position);
            pager.initData();
            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
}
