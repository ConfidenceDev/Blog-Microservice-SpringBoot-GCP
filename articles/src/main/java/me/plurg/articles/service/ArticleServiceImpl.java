package me.plurg.articles.service;

import lombok.extern.log4j.Log4j2;
import me.plurg.articles.dao.ArticleRepo;
import me.plurg.articles.entity.ArticleEntity;
import me.plurg.articles.exception.ArticleException;
import me.plurg.articles.external.ArticleRequest;
import me.plurg.articles.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepo articleRepo;

    @Transactional(readOnly = true)
    @Override
    public List<Article> getArticles() {
        log.info("Fetching all articles...");
        return articleRepo.findAll()
                .stream()
                .map(articleEntity -> Article.builder()
                        .id(articleEntity.getId())
                        .posterId(articleEntity.getPosterId())
                        .title(articleEntity.getTitle())
                        .content(articleEntity.getContent())
                        .timestamp(articleEntity.getTimestamp())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Article getArticleById(Long id) {
        log.info("Fetching article from Database...");

        ArticleEntity articleFromDB = articleRepo.findById(id)
                .orElseThrow(() -> new ArticleException("Article not found with id: " + id,
                        "NOT_FOUND", 404));

        log.info("Loading article to response");

        return Article.builder()
                .id(articleFromDB.getId())
                .posterId(articleFromDB.getPosterId())
                .title(articleFromDB.getTitle())
                .content(articleFromDB.getContent())
                .timestamp(articleFromDB.getTimestamp())
                .build();
    }

    @Override
    public Article createArticle(ArticleRequest articleRequest) {
        log.info("Adding article to entity object");

        ArticleEntity article = ArticleEntity.builder()
                .posterId(articleRequest.getPosterId())
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .timestamp(Instant.now())
                .build();

        article = articleRepo.save(article);
        log.info("Article created");

        return Article.builder()
                .id(article.getId())
                .posterId(article.getPosterId())
                .title(article.getTitle())
                .content(article.getContent())
                .timestamp(article.getTimestamp())
                .build();
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepo.findById(id)
                .orElseThrow(() -> new ArticleException("Article not found with id: " + id,
                "NOT_FOUND", 404));
        log.info("Article found and ready for deletion");

        articleRepo.deleteById(id);
        log.info("Article Removed");
    }
}
