package com.example.e_support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.e_support.utils.DensityUtils;
import com.example.e_support.utils.PrefUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button btnGuide;
    private int[] imageId = {
      R.drawable.guide_one,R.drawable.guide_two,R.drawable.guide_three
    };
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private LinearLayout linearLayout;
    private ImageView redPoint;
    private int pointDis = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }
    private void initView(){
        viewPager = findViewById(R.id.guide_viewpager);
        btnGuide = findViewById(R.id.btn_enter);
        btnGuide.setVisibility(View.INVISIBLE);
        linearLayout = findViewById(R.id.ll_container);
        redPoint = findViewById(R.id.point_red);
    }
    private void initData(){
        for (int i = 0;i<imageId.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageId[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            //从第二个开始设置左边距
            if(i > 0){
                //params.leftMargin = 10;
                //屏幕适配
                params.leftMargin = DensityUtils.dipToPx(10,this);
            }
            point.setLayoutParams(params);
            linearLayout.addView(point);
        }
        viewPager.setAdapter(new MyViewPagerAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //修改小红点的左边距来更新小红点的位置
                int leftMargin = (int)(position * pointDis + pointDis * positionOffset + 0.5f);
                //获取小红点的布局
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) redPoint.getLayoutParams();
                params.leftMargin = leftMargin;
                redPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    btnGuide.setVisibility(View.VISIBLE);
                }else{
                    btnGuide.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //计算圆点移动距离 = 第二个原点的距离-第一个原点的距离
        //measure --> layout --> draw,必须在onCreate执行结束后，才开始绘制
        //mPointDis = mLinearLayout.getChildAt(1).getLeft()-mLinearLayout.getChildAt(0).getLeft();
        redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //计算两个圆点的距离
                pointDis = linearLayout.getChildAt(1).getLeft() - linearLayout.getChildAt(0).getLeft();
                //移除观察者
                redPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.putBoolean(getApplicationContext(),"guide_is_read",true);
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    class MyViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
}
