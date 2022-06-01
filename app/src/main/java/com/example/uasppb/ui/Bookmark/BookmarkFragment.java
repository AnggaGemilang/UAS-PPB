package com.example.uasppb.ui.Bookmark;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uasppb.databinding.FragmentBookmarkBinding;
import com.example.uasppb.databinding.FragmentDashboardBinding;
import com.example.uasppb.model.Articles;
import com.example.uasppb.model.database.Article;
import com.example.uasppb.resource.ArticlesData;
import com.example.uasppb.ui.DetailActivity;
import com.example.uasppb.viewmodel.ArticleViewModel;
import com.example.uasppb.viewmodel.BookmarkViewModel;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment { ;

    private FragmentBookmarkBinding binding;
    ArrayList<Articles> listArticle;
    private ArticleViewModel articleViewModel;
    private BookmarkViewModel bookmarkViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
        articleViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ArticleViewModel.class);
        listArticle = ArticlesData.getListData();
        showRecyclerListView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showRecyclerListView(){
        articleViewModel.getmAllArticle().observe(getViewLifecycleOwner(), news -> {
            BookmarkListAdapter bookmarkListAdapter = new BookmarkListAdapter(news, getActivity());
            binding.counter.setText("Bookmarked - " + news.size() + " News");
            binding.recyclerViewBookmark.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            binding.recyclerViewBookmark.setAdapter(bookmarkListAdapter);
            bookmarkListAdapter.setOnItemClickCallback(new BookmarkListAdapter.OnItemClickCallback() {
                @Override
                public void onItemClicked(Article article) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title", article.getTitle());
                    bundle.putString("author", article.getAuthor());
                    bundle.putString("publisher", article.getSource_name());
                    bundle.putString("published", article.getPublishedAt());
                    bundle.putString("image", article.getUrlToImage());
                    bundle.putString("content", article.getContent());
                    bundle.putString("url", article.getUrl());
                    Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        });
    }
}