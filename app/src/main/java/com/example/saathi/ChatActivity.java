package com.example.saathi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        WebView myWebView = (WebView) findViewById(R.id.web_view);
        myWebView.loadUrl("https://github.com"); //todo: where? cant find github: working :)
    }
}