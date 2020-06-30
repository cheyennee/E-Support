package com.example.e_support;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener {

    String url;
    private ImageButton btnMenu,btnBack,btnFontSize,btnShare;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private WebView webView;
    private WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        initView();
        initData();
    }
    private void initView(){
        btnMenu = findViewById(R.id.btn_menu);
        btnBack = findViewById(R.id.btn_back);
        linearLayout = findViewById(R.id.ll_control);
        btnFontSize = findViewById(R.id.btn_font_size);
        btnShare = findViewById(R.id.btn_share);
        webView = findViewById(R.id.webview);
        progressBar = findViewById(R.id.pb_loading);
    }
    private void initData(){
        btnBack.setOnClickListener(this);
        btnFontSize.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnMenu.setVisibility(View.INVISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(false);//解决图片不显示
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        settings.setAppCacheEnabled(true);//是否使用缓存
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
            //跳转连接
            //让其在自己的浏览器中继续显示
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_font_size:
                chooseFontSize();
                break;
            case R.id.btn_share:
                share();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            finish();
        }
    }
    private int tempWhich ;
    private int currentWhich = 2;
    private void chooseFontSize(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置字号");
        String[] items = new String[]{"超大号","大号","正常","小号","超小号"};
        builder.setSingleChoiceItems(items, currentWhich,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempWhich = which;
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        settings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        settings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        settings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        settings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        settings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                }
                currentWhich = tempWhich;
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }
    private void share(){
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle("分享");
        oks.setTheme(OnekeyShareTheme.CLASSIC);
        oks.setTitleUrl(url);
        oks.setText("和我一起来看扶贫新鲜事儿吧~");
        oks.setUrl(url);
        oks.setSite("E-Support");
        oks.setSiteUrl(url);
        oks.show(this);
    }
}
