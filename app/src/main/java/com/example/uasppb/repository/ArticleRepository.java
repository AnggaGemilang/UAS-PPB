package com.example.uasppb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.uasppb.model.database.Article;
import com.example.uasppb.resource.ArticleDao;
import com.example.uasppb.resource.NeaDatabase;

import java.util.List;

public class ArticleRepository {
    private ArticleDao articleDao;
    private LiveData<List<Article>> mAllArticle;

    public ArticleRepository(Application application) {
        NeaDatabase db = NeaDatabase.getDatabase(application);
        articleDao = db.articleDao();
        mAllArticle = articleDao.getAllArticle();
    }

    public LiveData<List<Article>> getAllArticle() {
        return mAllArticle;
    }

    public void insert(Article article) {
        NeaDatabase.databaseWriteExecutor.execute(() -> {
            articleDao.insert(article);
        });
    }

    public LiveData<List<String>> getArticle(String url){
        return articleDao.getArticle(url);
    }

    public void delete(Article article) {
        NeaDatabase.databaseWriteExecutor.execute(() -> {
            articleDao.delete(article);
        });
    }

    public void deleteAll() {
        NeaDatabase.databaseWriteExecutor.execute(() -> {
            articleDao.deleteAll();
        });
    }
}
