package com.example.e_support.fragment;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.e_support.MainActivity;
import com.example.e_support.R;
import com.example.e_support.domain.NewsMenu;
import com.example.e_support.impl.NewsPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

public class LeftMenuFragment extends BaseFragment {

    private ListView listView;
    private String[] itemId = {"新闻","组图"};
    private LeftMenuAdapter adapter = new LeftMenuAdapter();
    private int currentPos = 0;
    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.left_menu_listview,null);
        listView = view.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.notifyDataSetChanged();
                setMenuDetailPager(position);
                currentPos = position;
            }
        });
        return view;
    }

    protected void setMenuDetailPager(int position){
        MainActivity activity = (MainActivity) mActivity;
        ContentFragment fragment = activity.getContentFragment();
        NewsPager pager = fragment.getNewsPager();
        pager.setDetailPager(position);
        toggle();
    }
    //收起侧边栏
    protected void toggle(){
        MainActivity activity = (MainActivity) mActivity;
        SlidingMenu menu = activity.getSlidingMenu();
        menu.toggle();
    }
    class LeftMenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return itemId.length;
        }

        @Override
        public Object getItem(int position) {
            return itemId[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            if(convertView == null){
                convertView = View.inflate(mActivity,R.layout.left_menu_listview_item,null);
                holder.textView = convertView.findViewById(R.id.item);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(itemId[position]);
            if(currentPos == position){
                holder.textView.setEnabled(true);
            }else{
                holder.textView.setEnabled(false);
            }
            return convertView;
        }
    }
    class ViewHolder{
        TextView textView;
    }
}
