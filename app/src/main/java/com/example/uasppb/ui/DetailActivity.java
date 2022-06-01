package com.example.uasppb.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.uasppb.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Intent intent = getIntent();
        binding.txtTitle.setText(intent.getStringExtra("title"));
        binding.txtWritten.setText("Written By: " + intent.getStringExtra("author"));
        binding.txtPublisher.setText("Published By: " + intent.getStringExtra("publisher") + ", ");
        binding.txtPublished.setText("Published At: " + intent.getStringExtra("published"));
        binding.txtIsiBerita.setText(intent.getStringExtra("content"));
        Picasso.get().load(intent.getStringExtra("image")).into(binding.imgThumbnail);

        binding.backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("url", intent.getStringExtra("url"));
                Intent intent2 = new Intent(getApplicationContext(), WebViewActivity.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
            }
        });

    }

}