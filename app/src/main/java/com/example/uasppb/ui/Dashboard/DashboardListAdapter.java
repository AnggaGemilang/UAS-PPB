package com.example.uasppb.ui.Dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.uasppb.model.Articles;
import com.example.uasppb.model.News;
import com.example.uasppb.model.database.Article;
import com.example.uasppb.ui.DetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DashboardListAdapter extends ListAdapter<News, DashboardViewHolder> {

    Context context;
    ArrayList<Articles> articles;
    private ArticleListener articleListener;

    public DashboardListAdapter(Context context, ArrayList<Articles> articles, NewsDiff diffCallback, ArticleListener articleListener) {
        super(diffCallback);
        this.context = context;
        this.articles = articles;
        this.articleListener = articleListener;
    }

    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DashboardViewHolder.create(parent);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Date date1 = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(articles.get(position).getPublishedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.bind(sdf1.format(date1), articles.get(position).getUrlToImage(), articles.get(position).getTitle(), articles.get(position).getSource().getName(), articles.get(position).getUrl(), context);
        holder.bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleListener.statusClick(view, articles.get(position));
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", articles.get(position).getTitle());
                bundle.putString("author", articles.get(position).getAuthor());
                bundle.putString("publisher", articles.get(position).getSource().getName());
                bundle.putString("published", articles.get(position).getPublishedAt());
                bundle.putString("image", articles.get(position).getUrlToImage());
                bundle.putString("content", articles.get(position).getContent());
                bundle.putString("url", articles.get(position).getUrl());
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    static class NewsDiff extends DiffUtil.ItemCallback<News> {
        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.getArticles().equals(newItem.getArticles());
        }
    }

    public interface ArticleListener {
        void statusClick(View view, Articles article);
    }
}
