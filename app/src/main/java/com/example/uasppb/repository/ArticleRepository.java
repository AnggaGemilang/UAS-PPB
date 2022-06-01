package com.example.uasppb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.uasppb.model.database.Article;
import com.example.uasppb.resource.ArticleDao;
import com.example.uasppb.resource.RoomDatabase;

import java.util.List;

public class ArticleRepository {
    private ArticleDao articleDao;
    private LiveData<List<Article>> mAllArticle;

    public ArticleRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        articleDao = db.articleDao();
        mAllArticle = articleDao.getAllArticle();
    }

    public LiveData<List<Article>> getArticleByQuery(String title){
        mAllArticle = articleDao.getArticleByQuery(title);
        return mAllArticle;
    }

    public LiveData<List<Article>> getAllArticle() {
        return mAllArticle;
    }

    public void insert(Article article) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            articleDao.insert(article);
        });
    }

    public LiveData<List<String>> getArticle(String url){
        return articleDao.getArticle(url);
    }

    public void delete(Article article) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            articleDao.delete(article);
        });
    }
}
