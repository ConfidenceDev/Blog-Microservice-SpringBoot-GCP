package me.plurg.articles.service;

import me.plurg.articles.external.ArticleRequest;
import me.plurg.articles.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getArticles();

    Article getArticleById(Long id);

    Article createArticle(ArticleRequest articleRequest);

    void deleteArticle(Long id);
}
