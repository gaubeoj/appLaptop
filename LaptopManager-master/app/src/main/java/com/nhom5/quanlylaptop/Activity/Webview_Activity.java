package com.nhom5.quanlylaptop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nhom5.quanlylaptop.R;

public class Webview_Activity extends AppCompatActivity {

    WebView webView;
    String title, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webView);
        getDataWeb();
        useToolbar(title);
        setWebView(url);
        webView.setWebViewClient(new WebViewClient());
    }

    private void getDataWeb(){
        Intent intent = getIntent();
        if (intent != null){
            try {
                title = intent.getStringExtra("titleWeb");
                url = intent.getStringExtra("urlWeb");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void useToolbar(String title) {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_Normal));
        TextView titleToolbar = findViewById(R.id.textView_Title_Toolbar);
        titleToolbar.setText(title);
        ImageButton back = findViewById(R.id.imageButton_Back_Toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("about:blank");
                finish();
            }
        });
    }

    private void setWebView(String url){
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.loadUrl(url);
    }
}