package com.example.utilisateur1.navigateurweb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TextView t = (TextView) findViewById(R.id.text);
        WebView wv = (WebView) findViewById(R.id.webview);
        Intent old = getIntent();

        String name = old.getStringExtra("valueUrl");
        wv.loadUrl(name);
    }
}
