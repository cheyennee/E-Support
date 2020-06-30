package com.example.e_support.impl;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_support.DB.DBHelper;
import com.example.e_support.DB.TechDetail;
import com.example.e_support.R;
import com.example.e_support.TechActivity;

import java.util.ArrayList;

public class TechPager extends BasePager {
    private ListView listView;
    private ArrayList<TechDetail> detailLists = new ArrayList<>();
    private Adapter adapter = new Adapter();
    public TechPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.layout_techpager_list,null);
        listView = view.findViewById(R.id.listview);
        flContainer.removeAllViews();
        //flContainer.addView(view);
        tvTitle.setText("技术支持");
        initListData();
        listView.setAdapter(adapter);
        flContainer.addView(view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, TechActivity.class);
                intent.putExtra("id",detailLists.get(position).id);
                mActivity.startActivity(intent);
            }
        });
    }
    private void initListData(){
        DBHelper helper = new DBHelper(mActivity,"ESupport.db",null,3);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("tech",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                TechDetail detail = new TechDetail();
                detail.id = cursor.getInt(cursor.getColumnIndex("id"));
                detail.techName = cursor.getString(cursor.getColumnIndex("techname"));
                detail.techDomain = cursor.getString(cursor.getColumnIndex("techdomain"));
                detail.suitArea = cursor.getString(cursor.getColumnIndex("suitarea"));
                detail.linkman = cursor.getString(cursor.getColumnIndex("linkman"));
                detail.tel = cursor.getString(cursor.getColumnIndex("tel"));
                detail.org = cursor.getString(cursor.getColumnIndex("org"));
                detail.techOwner = cursor.getString(cursor.getColumnIndex("techowner"));
                detail.techBrief = cursor.getString(cursor.getColumnIndex("techbrief"));
                detailLists.add(detail);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }
    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return detailLists.size();
        }

        @Override
        public Object getItem(int position) {
            return detailLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = View.inflate(mActivity,R.layout.layout_tech_listitem,null);
                holder.techName = convertView.findViewById(R.id.techname);
                holder.techDomain = convertView.findViewById(R.id.techdomain);
                holder.suitArea = convertView.findViewById(R.id.suitarea);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.techName.setText(detailLists.get(position).techName);
            holder.techDomain.setText(detailLists.get(position).techDomain);
            holder.suitArea.setText(detailLists.get(position).suitArea);
            return convertView;
        }
    }
    class ViewHolder{
        public TextView techName,techDomain,suitArea;
    }
}
