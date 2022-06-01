package com.example.uasppb.ui.Bookmark;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uasppb.R;
import com.example.uasppb.model.database.Article;
import com.example.uasppb.viewmodel.ArticleViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookmarkListAdapter extends RecyclerView.Adapter<BookmarkListAdapter.BookmarkViewHolder> {
    private List<Article> articles;
    private Activity activity;
    private ArticleViewModel articleViewModel;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @NotNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.article_template_item, viewGroup, false);
        return new BookmarkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookmarkViewHolder holder, int position) {
        Article article = articles.get(position);
        Date date1 = null;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(articles.get(position).getPublishedAt());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get().load(article.getUrlToImage()).into(holder.thumbnailItemView);
        holder.publishedItemView.setText(sdf1.format(date1));
        holder.titleItemView.setText(article.getTitle());
        holder.publisherItemView.setText(article.getSource_name());

        holder.buttonBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleViewModel = new ViewModelProvider((ViewModelStoreOwner) activity, new ViewModelProvider.AndroidViewModelFactory(activity.getApplication())).get(ArticleViewModel.class);
                articleViewModel.delete(article);
                Toast.makeText(activity.getApplicationContext(),"Berita berhasil dihapus",Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(articles.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (articles.isEmpty()) {
            return 0;
        }
        return articles.size();
    }

    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        private final TextView publishedItemView, publisherItemView, titleItemView;
        private final ImageView thumbnailItemView;
        private final ImageButton buttonBookmark;
        public CardView cardView;

        public BookmarkViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            titleItemView = itemView.findViewById(R.id.txt_title_news);
            publishedItemView = itemView.findViewById(R.id.txt_published_news);
            publisherItemView = itemView.findViewById(R.id.txt_publisher_news);
            thumbnailItemView = itemView.findViewById(R.id.img_thumbnail_news);
            buttonBookmark = itemView.findViewById(R.id.btn_bookmark);
            cardView = itemView.findViewById(R.id.card_view_news);
        }
    }

    public BookmarkListAdapter(List<Article> list, Activity activity) {
        this.articles = list;
        this.activity = activity;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Article article);
    }
}
