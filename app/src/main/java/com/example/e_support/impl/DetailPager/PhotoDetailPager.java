package com.example.e_support.impl.DetailPager;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.e_support.R;
import com.example.e_support.domain.PhotoListView;
import com.example.e_support.utils.CacheUtils;
import com.example.e_support.utils.GlobalContant;
import com.example.e_support.view.RefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

public class PhotoDetailPager extends BaseDetailPager {
    private RefreshListView listView;
    private PhotoListView listViewData = new PhotoListView();
    private ArrayList<PhotoListView.PhotoListItem> listItem = new ArrayList<>();
    private String moreUrl;
    private Adapter adapter = new Adapter();
    public PhotoDetailPager(Activity activity) {
        super(activity);
    }
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.photo_detail_pager,null);
        listView = view.findViewById(R.id.lv_list);
        return view;
    }

    @Override
    public void initData() {

        String cache = CacheUtils.getCache(mActivity,GlobalContant.HTTP_PATH + GlobalContant.PHOTO_LIST_VIEW);
        if(cache == null){
            getDataFromServer(GlobalContant.HTTP_PATH+ GlobalContant.PHOTO_LIST_VIEW);
        }else{
            processData(cache,false);
        }
        listView.setAdapter(adapter);
        listView.setRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMoreDataFromServer();
            }
        });
    }

    class Adapter extends BaseAdapter{

        private BitmapUtils bitmapUtils;
        public Adapter(){
            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.news_default);
        }
        @Override
        public int getCount() {
            return listItem.size();
        }

        @Override
        public Object getItem(int position) {
            return listItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if(convertView == null){
                convertView = View.inflate(mActivity,R.layout.photo_list_item,null);
                holder.imageView = convertView.findViewById(R.id.image);
                holder.textView = convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
                holder.textView.setText(listItem.get(position).title);
                bitmapUtils.display(holder.imageView,listItem.get(position).image);
            }
            return convertView;
        }
    }
    class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }

    private void getDataFromServer(final String url){
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        processData(result,false);
                        CacheUtils.setCache(mActivity,url,result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        error.printStackTrace();
                    }

                });
    }
    private void processData(String result,boolean isMore){
        Gson gson = new Gson();
        listViewData = gson.fromJson(result,PhotoListView.class);
        moreUrl = listViewData.more;
        if(!isMore){
            this.listItem = listViewData.data;
            //adapter.notifyDataSetChanged();
            return;
        }else{
            this.listItem.addAll(listViewData.data);
            adapter.notifyDataSetChanged();
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
                        processData(result,true);
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
