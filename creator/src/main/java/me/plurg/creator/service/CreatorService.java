package me.plurg.creator.service;

import me.plurg.creator.external.ArticleResponse;
import me.plurg.creator.model.Article;
import me.plurg.creator.model.Creator;
import me.plurg.creator.model.User;

import java.util.List;

public interface CreatorService {
    Long createUser(User user);

    void deleteUser(Long id);

    Long createArticle(Article article);

    ArticleResponse updateArticle(Long id, Article article);

    void deleteArticle(Long id);

    List<Creator> getAllCreators();
}
