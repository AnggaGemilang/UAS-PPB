package com.example.uasppb.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.example.uasppb.databinding.ActivityWebViewBinding;

public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Versi Webview");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.webview.getSettings().setLoadsImagesAutomatically(true);
        binding.webview.getSettings().setJavaScriptEnabled(true);
        binding.webview.getSettings().setDomStorageEnabled(true);

        Intent intent = getIntent();
        binding.webview.getSettings().setSupportZoom(true);
        binding.webview.getSettings().setBuiltInZoomControls(true);
        binding.webview.getSettings().setDisplayZoomControls(false);
        binding.webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.webview.setWebViewClient(new WebViewClient());
        binding.webview.loadUrl(intent.getStringExtra("url"));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}