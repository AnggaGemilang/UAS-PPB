package com.example.uasppb.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uasppb.model.News;
import com.example.uasppb.repository.NewsRepository;

import java.util.HashMap;
import java.util.Map;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private NewsRepository mRepository;
    private MutableLiveData<News> mAllDataNews;
    private Map<String, String> parameters;

    public NewsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("All News");
        parameters = new HashMap<>();
        mRepository = new NewsRepository();
    }

    public void init() {
        if (mAllDataNews != null) {
            return;
        }
        parameters.clear();
        mRepository = NewsRepository.getInstance();
        parameters.put("apiKey", "2d17333093df49ee9e753e2f737547ff");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<News> getAllNews(String country, String category) {
        if (mAllDataNews == null)
            Log.e("MyListActivity", parameters.toString());
        parameters.put("country", country);
        parameters.put("category", category);
        Log.e("MyListActivity", parameters.toString());
        mAllDataNews = mRepository.getAllNews(parameters);
        return mAllDataNews;
    }

    public LiveData<News> getAllNewsSearch(String search) {
        if (mAllDataNews == null)
            Log.e("MyListActivity", parameters.toString());
        parameters.put("q", search);
        mAllDataNews = mRepository.getAllNews(parameters);
        Log.e("MyListActivity", parameters.toString());
        return mAllDataNews;
    }
}