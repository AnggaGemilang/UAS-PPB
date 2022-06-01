package com.example.uasppb.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.uasppb.R;
import com.example.uasppb.databinding.ActivityDetailBinding;
import com.example.uasppb.model.database.Article;
import com.example.uasppb.viewmodel.ArticleViewModel;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private ArticleViewModel articleViewModel;

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

        Article article = new Article(
                intent.getStringExtra("author"),
                intent.getStringExtra("title"),
                intent.getStringExtra("url"),
                intent.getStringExtra("image"),
                intent.getStringExtra("published"),
                intent.getStringExtra("content"),
                intent.getStringExtra("publisher")
        );

        articleViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ArticleViewModel.class);
        articleViewModel.getArticle(intent.getStringExtra("url")).observe(this, title -> {
            if (title.size() > 0){
                binding.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_24);
                binding.bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleViewModel.delete(article);
                        Toast.makeText(getApplicationContext(),"Berita berhasil dihapus",Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                binding.bookmark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleViewModel.insert(article);
                        Toast.makeText(getApplicationContext(), "Berita berhasil disimpan", Toast.LENGTH_LONG).show();
                    }
                });
                binding.bookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
            }
        });

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