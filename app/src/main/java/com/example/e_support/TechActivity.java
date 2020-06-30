package com.example.e_support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.e_support.DB.DBHelper;
import com.example.e_support.DB.TechDetail;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class TechActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnMenu,btnBack,btnFontSize,btnShare;
    private LinearLayout linearLayout;
    private int id;
    private TextView name,field,suitarea,linkman,tel,owner,brief;
    private TechDetail detail = new TechDetail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);
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
        name = findViewById(R.id.name);
        field = findViewById(R.id.field);
        suitarea = findViewById(R.id.suitarea);
        linkman = findViewById(R.id.linkman);
        tel = findViewById(R.id.tel);
        owner = findViewById(R.id.owner);
        brief = findViewById(R.id.brief);
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
        name.setText(detail.techName);
        field.setText(detail.techDomain);
        suitarea.setText(detail.suitArea);
        linkman.setText(detail.linkman);
        tel.setText(detail.tel);
        owner.setText(detail.techOwner);
        brief.setText(detail.techBrief);
    }

    private void queryDB(){
        DBHelper helper = new DBHelper(this,"ESupport.db",null,3);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("tech",null,"id=?",new String[]{String.valueOf(id)},null,null,null);
        //Cursor cursor = db.rawQuery("select * from tech where id = ? ",new String[]{String.valueOf(id)});
        //Cursor cursor = db.query("tech",null,null,null,null,null,null);
        Log.e("gothere","hey");
        if(cursor.moveToFirst()){
            do{
                if(cursor.getInt(cursor.getColumnIndex("id")) == id){
                    break;
                }else{
                    cursor.moveToNext();
                }
            }while(true);
            detail.techName = cursor.getString(cursor.getColumnIndex("techname"));
            detail.techDomain = cursor.getString(cursor.getColumnIndex("techdomain"));
            detail.techBrief = cursor.getString(cursor.getColumnIndex("techbrief"));
            detail.suitArea = cursor.getString(cursor.getColumnIndex("suitarea"));
            detail.linkman = cursor.getString(cursor.getColumnIndex("linkman"));
            detail.tel = cursor.getString(cursor.getColumnIndex("tel"));
            detail.org = cursor.getString(cursor.getColumnIndex("org"));
            detail.techOwner = cursor.getString(cursor.getColumnIndex("techowner"));
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
