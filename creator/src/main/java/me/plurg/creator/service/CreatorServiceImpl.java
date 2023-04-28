package me.plurg.creator.service;

import lombok.extern.log4j.Log4j2;
import me.plurg.creator.dao.CreatorRepo;
import me.plurg.creator.entity.CreatorEntity;
import me.plurg.creator.exception.CreatorException;
import me.plurg.creator.external.ArticleResponse;
import me.plurg.creator.external.feign.ArticleService;
import me.plurg.creator.model.Article;
import me.plurg.creator.model.Creator;
import me.plurg.creator.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CreatorServiceImpl implements CreatorService {

    @Autowired
    private CreatorRepo creatorRepo;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Creator> getAllCreators() {
        log.info("Getting all creators...");
        return creatorRepo.findAll()
                .stream()
                .map(creatorEntity -> Creator
                        .builder()
                        .id(creatorEntity.getId())
                        .name(creatorEntity.getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Long createUser(User user) {
        log.info("Adding user to entity object");

        CreatorEntity creator = CreatorEntity.builder()
                .name(user.getName())
                .created(Instant.now())
                .build();

        creator = creatorRepo.save(creator);

        log.info("User created");
        return creator.getId();
    }

    @Override
    public void deleteUser(Long id) {
        creatorRepo.findById(id)
                .orElseThrow(() -> new CreatorException("User not found with id: " + id,
                        "NOT_FOUND", 404));
        log.info("User found and ready to be removed");

        creatorRepo.deleteById(id);
        log.info("User Removed");
    }

    @Override
    public Long createArticle(Article article) {
        log.info("Sending object to article controller");
        ArticleResponse response =
                restTemplate.postForObject("http://ARTICLE/v1/articles",
                        article, ArticleResponse.class);

        log.info("Fetched response");
        return Optional.ofNullable(response).get().getId();
    }

    @Override
    public ArticleResponse updateArticle(Long id, Article article) {
        throw new CreatorException("Oops, this endpoint is not available yet!",
                "INTERNAL_SERVER_ERROR", 500);
    }

    @Override
    public void deleteArticle(Long id) {
        log.info("Sending id to article controller for deletion");
        //restTemplate.delete("http://localhost:8082/v1/articles/" + id);

        articleService.deleteArticle(id);
        log.info("Article deleted");
    }
}
