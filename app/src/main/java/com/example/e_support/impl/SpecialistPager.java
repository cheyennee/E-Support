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
import com.example.e_support.DB.SpecialistDetail;
import com.example.e_support.R;
import com.example.e_support.SpecialistActivity;

import java.util.ArrayList;

public class SpecialistPager extends BasePager {
    private ListView listView;
    private ArrayList<SpecialistDetail> detailsList = new ArrayList<>();
    private Adapter adapter = new Adapter();
    public SpecialistPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {
        View view = View.inflate(mActivity, R.layout.layout_specialistpager_list,null);
        listView = view.findViewById(R.id.listview);
        flContainer.removeAllViews();
        initListData();
        listView.setAdapter(adapter);
        flContainer.addView(view);
        tvTitle.setText("咨询");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, SpecialistActivity.class);
                intent.putExtra("id",detailsList.get(position).id);
                mActivity.startActivity(intent);
            }
        });
    }
    private void initListData(){
        DBHelper helper = new DBHelper(mActivity,"ESupport.db",null,3);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("specialist",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                SpecialistDetail detail = new SpecialistDetail();
                detail.id = cursor.getInt(cursor.getColumnIndex("id"));
                detail.description = cursor.getString(cursor.getColumnIndex("description"));
                detail.field = cursor.getString(cursor.getColumnIndex("field"));
                detail.honor = cursor.getString(cursor.getColumnIndex("honor"));
                detail.name = cursor.getString(cursor.getColumnIndex("name"));
                detail.place = cursor.getString(cursor.getColumnIndex("place"));
                detail.phone = cursor.getString(cursor.getColumnIndex("phone"));
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
                convertView = View.inflate(mActivity,R.layout.layout_specialistpager_listitem,null);
                holder.name = convertView.findViewById(R.id.tv_name);
                holder.field = convertView.findViewById(R.id.tv_field);
                holder.honor = convertView.findViewById(R.id.tv_honor);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(detailsList.get(position).name);
            holder.honor.setText(detailsList.get(position).honor);
            holder.field.setText(detailsList.get(position).field);
            return convertView;
        }
    }
    class ViewHolder{
        public TextView name,honor,field;
    }
}
