package com.example.uasppb.ui.Dashboard;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.uasppb.R;
import com.example.uasppb.databinding.FragmentDashboardBinding;
import com.example.uasppb.model.Articles;
import com.example.uasppb.viewmodel.ArticleViewModel;
import com.example.uasppb.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private NewsViewModel newsViewModel;
    private DashboardListAdapter adapterNews;
    private final ArrayList<Articles> articleArrayList = new ArrayList<>();
    private String category = "general";
    private String country;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        SharedPreferences settings = getActivity().getSharedPreferences("NEWS_SETTINGS", MODE_PRIVATE);
        country = settings.getString("region", "id");

        newsViewModel.init();
        loadNews(country, category);

        binding.inputSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                newsViewModel.init();
                loadNewsSearch(s);
                binding.inputSearch.setQuery("", false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        setTextTitle();
        business_click();
        entertainment_click();
        general_click();
        science_click();
    }

    private void loadNews(String ct, String cty) {
        newsViewModel.getAllNews(ct, cty).observe(getViewLifecycleOwner(), news -> {
            List<Articles> newsArticles = news.getArticles();
            Log.e("data", newsArticles.toString());
            articleArrayList.clear();
            articleArrayList.addAll(newsArticles);
            adapterNews.notifyDataSetChanged();
        });
        setupRecyclerView();
    }

    private void loadNewsSearch(String search) {
        newsViewModel.getAllNewsSearch(search).observe(getViewLifecycleOwner(), news -> {
            List<Articles> newsArticles = news.getArticles();
            Log.d("asyiappp", newsArticles.toString());
            articleArrayList.clear();
            articleArrayList.addAll(newsArticles);
            adapterNews.notifyDataSetChanged();
        });
        setupRecyclerView();
    }

    public void business_click() {
        binding.businessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!category.equals("business")) {
                    binding.businessButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.businessTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    binding.entertaimentButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.entertaimentTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.generalButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.generalTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.scienceButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.scienceTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    category = "business";
                } else {
                    category = "";
                }
                newsViewModel.init();
                loadNews(country, category);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void setTextTitle(){
        if(country.equals("id")){
            binding.txtTitleTheme.setText("Trending In Indonesia");
        } else if(country.equals("us")){
            binding.txtTitleTheme.setText("Trending In United State");
        } else if(country.equals("my")){
            binding.txtTitleTheme.setText("Trending In Malaysia");
        }
    }

    public void entertainment_click() {
        binding.entertaimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!category.equals("entertainment")) {
                    binding.businessButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.businessTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.entertaimentButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.entertaimentTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    binding.generalButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.generalTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.scienceButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.scienceTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    category = "entertainment";
                } else {
                    category = "";
                }
                newsViewModel.init();
                loadNews(country, category);
            }
        });
    }

    public void general_click() {
        binding.generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!category.equals("general")) {
                    binding.businessButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.businessTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.entertaimentButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.entertaimentTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.generalButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.generalTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    binding.scienceButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.scienceTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    category = "general";
                } else {
                    category = "";
                }
                newsViewModel.init();
                loadNews(country, category);
            }
        });
    }

    public void science_click() {
        binding.scienceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!category.equals("science")) {
                    binding.businessButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.businessTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.entertaimentButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.entertaimentTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.generalButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.semi_grey));
                    binding.generalTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.scienceButton.getBackground().setTint(ContextCompat.getColor(getContext(), R.color.mainColor));
                    binding.scienceTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    category = "science";
                } else {
                    category = "";
                }
                newsViewModel.init();
                loadNews(country, category);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerViewTrending.setLayoutManager(linearLayoutManager);
        adapterNews = new DashboardListAdapter(this.getActivity(), articleArrayList);
        binding.recyclerViewTrending.setAdapter(adapterNews);
        adapterNews.notifyDataSetChanged();
    }
}