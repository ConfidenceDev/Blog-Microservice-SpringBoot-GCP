package me.plurg.articles.dao;

import me.plurg.articles.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends JpaRepository<ArticleEntity, Long> {
}
