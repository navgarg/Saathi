package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        WebView myWebView = (WebView) findViewById(R.id.web_view);
        myWebView.loadUrl("http://navyagarg.in/saathi-bot/");
        Log.d("ChatActivity", "onCreate: webView loaded");
    }
}