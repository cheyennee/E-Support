package com.example.e_support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_support.DB.DBHelper;
import com.example.e_support.DB.SpecialistDetail;
import com.example.e_support.chat.ChatActivity;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class SpecialistActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton btnMenu,btnBack,btnFontSize,btnShare;
    private LinearLayout linearLayout;
    private int id;
    private SpecialistDetail detail = new SpecialistDetail();
    private TextView name,honor,place,field,description,phone;
    private Button consultant,phonecall;
    public static String currentAccount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);
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
        place = findViewById(R.id.place);
        honor = findViewById(R.id.honor);
        field = findViewById(R.id.field);
        description = findViewById(R.id.description);
        consultant = findViewById(R.id.consultant);
        phonecall = findViewById(R.id.phonecall);
        phone = findViewById(R.id.phone);
    }
    private void initData(){
        btnBack.setOnClickListener(this);
        btnFontSize.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        phonecall.setOnClickListener(this);
        consultant.setOnClickListener(this);
        btnMenu.setVisibility(View.INVISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        btnFontSize.setVisibility(View.INVISIBLE);
        queryDB();
        name.setText(detail.name);
        field.setText(detail.field);
        place.setText(detail.place);
        honor.setText(detail.honor);
        description.setText(detail.description);
        phone.setText(detail.phone);
    }

    private void queryDB(){
        DBHelper helper = new DBHelper(this,"ESupport.db",null,3);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("specialist",null,"id=?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor.moveToFirst()){
            detail.place = cursor.getString(cursor.getColumnIndex("place"));
            detail.name = cursor.getString(cursor.getColumnIndex("name"));
            detail.honor = cursor.getString(cursor.getColumnIndex("honor"));
            detail.field = cursor.getString(cursor.getColumnIndex("field"));
            detail.description = cursor.getString(cursor.getColumnIndex("description"));
            detail.phone = cursor.getString(cursor.getColumnIndex("phone"));
            detail.account = cursor.getString(cursor.getColumnIndex("account"));
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
            case R.id.consultant:
                if(currentAccount!=null)
                    sendMsg();
                else
                    Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.phonecall:
                makePhoneCall();
                break;
        }
    }
    private void sendMsg(){
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("id",detail.account);
        startActivity(intent);
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
    private void makePhoneCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phone.getText()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
