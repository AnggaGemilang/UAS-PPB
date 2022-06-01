package com.example.uasppb.ui.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uasppb.R;
import com.squareup.picasso.Picasso;

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

    public void bind(String published, String image, String title, String publisher, String url, Context context) {
        publishedItemView.setText(published);
        Picasso.get().load(image).into(thumbnailItemView);
        titleItemView.setText(title);
        publisherItemView.setText(publisher);
    }

    static DashboardViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_template_item, parent, false);
        return new DashboardViewHolder(view);
    }
}
