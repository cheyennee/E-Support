package com.example.e_support;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_support.utils.DataCleanManager;

public class SettingActivity extends AppCompatActivity {

    private ImageButton menu,back;
    private TextView clean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        menu = findViewById(R.id.btn_menu);
        menu.setVisibility(View.INVISIBLE);
        back = findViewById(R.id.btn_back);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clean = findViewById(R.id.cleancache);
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(getApplicationContext());
                Toast.makeText(getApplicationContext(),"缓存清理完成",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
