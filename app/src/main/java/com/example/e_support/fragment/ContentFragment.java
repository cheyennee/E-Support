package com.example.e_support.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.e_support.MainActivity;
import com.example.e_support.R;
import com.example.e_support.impl.BasePager;
import com.example.e_support.impl.IndustryPager;
import com.example.e_support.impl.ManagerPager;
import com.example.e_support.impl.NewsPager;
import com.example.e_support.impl.SpecialistPager;
import com.example.e_support.impl.TechPager;
import com.example.e_support.view.NoScrollViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {
    public NoScrollViewPager viewPager;
    private RadioGroup rgGroup;
    public ArrayList<BasePager> mBasePagers = new ArrayList<>();
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.layout_content_fragment,null);
        viewPager = view.findViewById(R.id.vp_content);
        rgGroup = view.findViewById(R.id.rg_group);
        return view;
    }
    public NewsPager getNewsPager(){
        return (NewsPager) mBasePagers.get(0);
    }
    @Override
    public void initData() {
        mBasePagers.add(new NewsPager(mActivity));
        mBasePagers.add(new SpecialistPager(mActivity));
        mBasePagers.add(new TechPager(mActivity));
        mBasePagers.add(new IndustryPager(mActivity));
        mBasePagers.add(new ManagerPager(mActivity));
        viewPager.setAdapter(new ContentViewPagerAdapter());
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_news:
                        viewPager.setCurrentItem(0,false);
                        break;
                    case R.id.rb_specialist:
                        viewPager.setCurrentItem(1,false);
                        break;
                    case R.id.rb_tech:
                        viewPager.setCurrentItem(2,false);
                        break;
                    case R.id.rb_industry:
                        viewPager.setCurrentItem(3,false);
                        break;
                    case R.id.rb_manage:
                        viewPager.setCurrentItem(4,false);
                        break;
                }
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BasePager pager = mBasePagers.get(position);
                pager.initData();
                if(position == 0){
                    setSlidingMenuEnable(true);
                }else{
                    setSlidingMenuEnable(false);
                    pager.btnMenu.setVisibility(View.INVISIBLE);
                }
                /*RadioButton btn = (RadioButton) rgGroup.getChildAt(position);
                btn.setChecked(true);*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //手动初始化第一页数据
        mBasePagers.get(0).initData();
        //手动开启第一页侧边栏
        setSlidingMenuEnable(true);
    }
    private void setSlidingMenuEnable(boolean flag){
        MainActivity activity = (MainActivity) mActivity;
        SlidingMenu menu = activity.getSlidingMenu();
        if(flag){
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }
    class ContentViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mBasePagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            BasePager pager = mBasePagers.get(position);
            container.addView(pager.mRootView);
            return pager.mRootView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
}
