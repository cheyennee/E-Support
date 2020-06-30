package com.example.e_support;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LinkToActivity extends AppCompatActivity {
    private TextView china;
    private TextView anhui,hebei,shanxi,liaoning,jilin,heilongjiang,jiangsu;
    private TextView zhejiang,fujian,jiangxi,shandong,henan,hubei,hunan,guangdong;
    private TextView hainan,sichuan,guizhou,yunnan,shanxisan,gansu,qinghai,taiwan;
    private TextView neimenggu,guangxi,xizang,ningxia,xinjaing,beijing,tianjin;
    private TextView shanghai,chongqing;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_to);
        initView();
        initData();
    }
    private void initView(){
        title = findViewById(R.id.tv_title);
        title.setText("各省扶贫办");
        china = findViewById(R.id.china);
        beijing = findViewById(R.id.beijing);
        tianjin = findViewById(R.id.tianjin);
        shanghai = findViewById(R.id.shanghai);
        chongqing = findViewById(R.id.chongqing);
        anhui = findViewById(R.id.anhui);
        hebei = findViewById(R.id.hebei);
        shanxi = findViewById(R.id.shanxi);
        liaoning = findViewById(R.id.liaoning);
        jilin = findViewById(R.id.jilin);
        heilongjiang = findViewById(R.id.heilongjiang);
        jiangsu = findViewById(R.id.jiangsu);
        zhejiang = findViewById(R.id.zhejiang);
        fujian = findViewById(R.id.fujian);
        jiangxi = findViewById(R.id.jiangxi);
        shandong = findViewById(R.id.shandong);
        henan = findViewById(R.id.henan);
        hubei = findViewById(R.id.hubei);
        hunan = findViewById(R.id.hunan);
        guangdong = findViewById(R.id.guangdong);
        hainan = findViewById(R.id.hainan);
        sichuan = findViewById(R.id.sichuan);
        guizhou = findViewById(R.id.guizhou);
        yunnan = findViewById(R.id.yunnan);
        shanxisan = findViewById(R.id.shanxisan);
        gansu = findViewById(R.id.gansu);
        qinghai = findViewById(R.id.qinghai);
        taiwan = findViewById(R.id.taiwan);
        neimenggu = findViewById(R.id.neimenggu);
        guangxi = findViewById(R.id.guangxi);
        xizang = findViewById(R.id.xizang);
        ningxia = findViewById(R.id.ningxia);
        xinjaing = findViewById(R.id.xinjiang);
    }
    private void initData(){
        china.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.cpad.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        anhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.ah.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        hebei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fp.hebei.gov.cn/webFup/index.html");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        shanxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.shanxi.gov.cn/sx/index;jsessionid=967F38BA9EEB0781EB7C26BA7A7E136F");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        liaoning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.ln.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        heilongjiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.heilongjiangfupin.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        jiangsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.nynct.jiangsu.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        fujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://nynct.fujian.gov.cn/ztzl/fpgz_1/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        jiangxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.jiangxi.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        shandong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpkfb.shandong.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        henan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.henan.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        hubei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.hubei.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        hunan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://hnsfpb.hunan.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        guangdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.gdfp.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        hainan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.hainan.gov.cn//");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        sichuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpkfj.sc.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        guizhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.guizhou.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        yunnan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://ynfp.yn.gov.cn/f");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        shanxisan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.shaanxi.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        gansu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.gansu.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        qinghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpj.qinghai.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        neimenggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.nmg.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        guangxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.gxzf.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        ningxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://fpb.nx.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        xinjaing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.xjfp.gov.cn/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
    }
}
