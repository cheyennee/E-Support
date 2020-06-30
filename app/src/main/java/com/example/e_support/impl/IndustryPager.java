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
import com.example.e_support.DB.IndustryDetail;
import com.example.e_support.DB.TechDetail;
import com.example.e_support.IndustryActivity;
import com.example.e_support.R;

import java.util.ArrayList;

public class IndustryPager extends BasePager {
    private ListView listView;
    private ArrayList<IndustryDetail> detailsList = new ArrayList<>();
    private Adapter adapter = new Adapter();
    public IndustryPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.layout_industrypager_list,null);
        listView = view.findViewById(R.id.listview);
        flContainer.removeAllViews();
        initListData();
        listView.setAdapter(adapter);
        flContainer.addView(view);
        tvTitle.setText("产业链接");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, IndustryActivity.class);
                intent.putExtra("id",detailsList.get(position).id);
                mActivity.startActivity(intent);
            }
        });
    }
    private void initListData(){
        DBHelper helper = new DBHelper(mActivity,"ESupport.db",null,3);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("industry",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                IndustryDetail detail = new IndustryDetail();
                detail.corpName = cursor.getString(cursor.getColumnIndex("corpname"));
                detail.industryName = cursor.getString(cursor.getColumnIndex("industryname"));
                detail.place = cursor.getString(cursor.getColumnIndex("place"));
                detail.devTrend = cursor.getString(cursor.getColumnIndex("devtrend"));
                detail.id = cursor.getInt(cursor.getColumnIndex("id"));
                detailsList.add(detail);
            }while (cursor.moveToNext());
            cursor.close();
        }
    }
    class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return detailsList.size();
        }

        @Override
        public Object getItem(int position) {
            return detailsList.get(position);
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
                convertView = View.inflate(mActivity,R.layout.layout_industry_listitem,null);
                holder.corpName = convertView.findViewById(R.id.copname);
                holder.industryName = convertView.findViewById(R.id.industryname);
                holder.place = convertView.findViewById(R.id.place);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.corpName.setText(detailsList.get(position).corpName);
            holder.place.setText(detailsList.get(position).place);
            holder.industryName.setText(detailsList.get(position).industryName);
            return convertView;
        }
    }
    class ViewHolder {
        public TextView corpName,industryName,place;
    }
}
