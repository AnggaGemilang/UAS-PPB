package com.example.uasppb.ui.Dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uasppb.R;
import com.example.uasppb.model.Articles;
import com.example.uasppb.model.News;
import com.example.uasppb.model.database.Article;
import com.example.uasppb.ui.Bookmark.BookmarkListAdapter;
import com.example.uasppb.ui.DetailActivity;
import com.example.uasppb.viewmodel.ArticleViewModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.DashboardViewHolder> {

    Context context;
    ArrayList<Articles> articles;

    public DashboardListAdapter(Context context, ArrayList<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_template_item, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AtomicReference<Boolean> dataAda = new AtomicReference<>(false);
        Date date1 = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(articles.get(position).getPublishedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.publishedItemView.setText(sdf1.format(date1));
        Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.thumbnailItemView);
        holder.titleItemView.setText(articles.get(position).getTitle());
        holder.publisherItemView.setText(articles.get(position).getSource().getName());
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

    public class DashboardViewHolder extends RecyclerView.ViewHolder {
        private final TextView publishedItemView, publisherItemView, titleItemView;
        private final ImageView thumbnailItemView;
        public ImageButton bookmarkButton;
        public CardView cardView;

        public DashboardViewHolder(View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.txt_title_news);
            publishedItemView = itemView.findViewById(R.id.txt_published_news);
            publisherItemView = itemView.findViewById(R.id.txt_publisher_news);
            thumbnailItemView = itemView.findViewById(R.id.img_thumbnail_news);
            bookmarkButton = itemView.findViewById(R.id.btn_bookmark);
            cardView = itemView.findViewById(R.id.card_view_news);
        }
    }
}