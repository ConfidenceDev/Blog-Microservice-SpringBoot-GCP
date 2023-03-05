package me.plurg.articles.controller;

import lombok.extern.log4j.Log4j2;
import me.plurg.articles.external.ArticleRequest;
import me.plurg.articles.model.Article;
import me.plurg.articles.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/articles")
@Log4j2
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public String greet(){
        log.info("Greetings Articles...");
        return "Hello Articles!";
    }

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getArticles (){
        List<Article> allArticles = articleService.getArticles();
        log.info("Retrieved all articles");
        return new ResponseEntity<>(allArticles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById (@PathVariable("id") Long id){
        Article article = articleService.getArticleById(id);
        log.info("Retrieved article with id: " + article.getId());
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Article> createArticle (@RequestBody ArticleRequest articleRequest){
        Article resArticle = articleService.createArticle(articleRequest);
        return new ResponseEntity<>(resArticle, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle (@PathVariable("id") Long id){
        log.info("Deleting article with id: " + id);
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
