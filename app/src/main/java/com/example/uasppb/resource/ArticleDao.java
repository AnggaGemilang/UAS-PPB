package com.example.uasppb.resource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.uasppb.model.database.Article;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    LiveData<List<Article>> getAllArticle();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Article article);

    @Query("DELETE FROM article")
    void deleteAll();

    @Delete
    void delete(Article article);

    @Query("SELECT title FROM article WHERE url = :url")
    LiveData<List<String>> getArticle(String url);

    @Query("SELECT * FROM article WHERE title LIKE '%' || :title || '%'")
    LiveData<List<Article>> getArticleByQuery(String title);

}
