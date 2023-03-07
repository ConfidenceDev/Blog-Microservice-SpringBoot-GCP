package me.plurg.creator.external.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import me.plurg.creator.exception.CreatorException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "ARTICLE/v1/articles")
public interface ArticleService {

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteArticle(@PathVariable("id") Long id);

    default ResponseEntity<Void> fallback(Exception e) {
        throw new CreatorException("Article Service not available", "UNAVAILABLE", 500);
    }

}
