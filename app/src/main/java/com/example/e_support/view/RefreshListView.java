package com.example.e_support.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.e_support.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RefreshListView extends ListView {
    private View mHeaderView;
    private TextView tvState,tvTime;
    private ImageView ivArrow;
    private ProgressBar pbLoading;
    private int mHeaderViewHeight;
    private static final int STATE_PULL_TO_REFRESH = 0;
    private static final int STATE_RELEASE_TO_REFRESH = 1;
    private static final int STATE_LOADING = 2;
    private int currentState = STATE_PULL_TO_REFRESH;
    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
    }
    private void initHeaderView(){
        mHeaderView = View.inflate(getContext(), R.layout.refresh_header,null);
        addHeaderView(mHeaderView);
        tvState = mHeaderView.findViewById(R.id.tv_state);
        tvTime = mHeaderView.findViewById(R.id.tv_time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tvTime.setText(sdf.format(new Date()));
        ivArrow = mHeaderView.findViewById(R.id.iv_arrow);
        pbLoading = mHeaderView.findViewById(R.id.pb_loading);
        mHeaderView.measure(0,0);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
    }

    private int startY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if(currentState == STATE_PULL_TO_REFRESH){
                    mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
                }else if(currentState == STATE_RELEASE_TO_REFRESH){
                    mHeaderView.setPadding(0,0,0,0);
                    currentState = STATE_LOADING;
                    refreshState();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(startY == -1){
                    startY = (int) ev.getY();
                }
                int endY = (int) ev.getY();
                int dy = endY - startY;
                if(currentState == STATE_LOADING){
                    break;
                }
                int firstVisiblePosition = this.getFirstVisiblePosition();
                if(firstVisiblePosition == 0 && dy > 0){
                    int padding = -mHeaderViewHeight + dy;
                    if(padding < 0 && currentState != STATE_PULL_TO_REFRESH){
                        currentState = STATE_PULL_TO_REFRESH;
                    }else if(padding > 0 && currentState != STATE_RELEASE_TO_REFRESH){
                        currentState = STATE_RELEASE_TO_REFRESH;
                    }
                    refreshState();
                    mHeaderView.setPadding(0,padding,0,0);
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    private void refreshState(){
        switch (currentState){
            case STATE_PULL_TO_REFRESH:
                tvState.setText("下拉刷新");
                pbLoading.setVisibility(View.INVISIBLE);
                ivArrow.setVisibility(View.VISIBLE);
                break;
            case STATE_LOADING:
                tvState.setText("正在刷新");
                pbLoading.setVisibility(View.VISIBLE);
                ivArrow.clearAnimation();
                ivArrow.setVisibility(View.INVISIBLE);
                if(mListener != null){
                    mListener.onRefresh();
                }
                break;
            case STATE_RELEASE_TO_REFRESH:
                tvState.setText("松开刷新");
                pbLoading.setVisibility(View.INVISIBLE);
                ivArrow.setVisibility(View.VISIBLE);
                break;
        }
    }
    private OnRefreshListener mListener;
    public void setRefreshListener(OnRefreshListener listener){
        mListener = listener;
    }
    public interface OnRefreshListener{
        public void onRefresh();
    }
    public void setRefreshTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String currentTime = sdf.format(date);
        tvTime.setText(currentTime);
    }
    public void onRefreshComplete(){
        mHeaderView.setPadding(0,-mHeaderViewHeight,0,0);
        currentState = STATE_PULL_TO_REFRESH;
        setRefreshTime();
    }
}
