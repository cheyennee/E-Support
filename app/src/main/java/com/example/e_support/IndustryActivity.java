package com.example.e_support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.e_support.DB.DBHelper;
import com.example.e_support.DB.IndustryDetail;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class IndustryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnMenu,btnBack,btnFontSize,btnShare;
    private LinearLayout linearLayout;
    private int id;
    private TextView corpname,industryname,place,devtrend;
    private IndustryDetail detail = new IndustryDetail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_industry);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        initView();
        initData();
    }
    private void initView(){
        btnMenu = findViewById(R.id.btn_menu);
        btnBack = findViewById(R.id.btn_back);
        linearLayout = findViewById(R.id.ll_control);
        btnFontSize = findViewById(R.id.btn_font_size);
        btnShare = findViewById(R.id.btn_share);
        corpname = findViewById(R.id.corpname);
        industryname = findViewById(R.id.industryname);
        place = findViewById(R.id.place);
        devtrend = findViewById(R.id.devtrend);
    }
    private void initData(){
        btnBack.setOnClickListener(this);
        btnFontSize.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnMenu.setVisibility(View.INVISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        btnFontSize.setVisibility(View.INVISIBLE);
        queryDB();
        corpname.setText(detail.corpName);
        industryname.setText(detail.industryName);
        place.setText(detail.place);
        devtrend.setText(detail.devTrend);
    }

    private void queryDB(){
        DBHelper helper = new DBHelper(this,"ESupport.db",null,3);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("industry",null,"id=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            detail.devTrend = cursor.getString(cursor.getColumnIndex("devtrend"));
            detail.place = cursor.getString(cursor.getColumnIndex("place"));
            detail.industryName = cursor.getString(cursor.getColumnIndex("industryname"));
            detail.corpName = cursor.getString(cursor.getColumnIndex("corpname"));
            cursor.close();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_share:
                share();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
    private void share(){
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("分享");
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        oks.setText("和我一起来看扶贫新项目吧~");
        oks.setSite("E-Support");
        oks.show(this);
    }
}
