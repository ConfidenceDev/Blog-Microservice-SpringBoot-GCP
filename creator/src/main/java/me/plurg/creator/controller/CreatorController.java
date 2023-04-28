package me.plurg.creator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import me.plurg.creator.external.ArticleResponse;
import me.plurg.creator.model.Article;
import me.plurg.creator.model.Creator;
import me.plurg.creator.model.User;
import me.plurg.creator.service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/creators")
@Log4j2
public class CreatorController {

    @Autowired
    private CreatorService creatorService;

    @GetMapping
    public ResponseEntity<List<Creator>> getAllCreators (){
        log.info("Fetching creators...");
        List<Creator> allCreators = creatorService.getAllCreators();

        log.info("Users retrieved");
        return new ResponseEntity<>(allCreators, HttpStatus.OK);
    }

    @Operation(
            description = "",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "bad_request"),
                    @ApiResponse(
                            responseCode = "200",
                            description = "",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "\"code:\" 200, \"status:\" \"success\", \"message:\" \"registration successful!\""
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<Long> createUser (@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    value = "\"username:\" \"admin\", \"password:\" \"admin\""
                            ),
                    })
    ) @Validated @RequestBody User user){
        log.info("Registering user...");
        Long userId = creatorService.createUser(user);

        log.info("User registered");
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable("id") Long id){
        log.info("Deleting user...");
        creatorService.deleteUser(id);

        log.info("User deleted...");
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/articles")
    public ResponseEntity<Long> createArticle (@RequestBody Article article){
        log.info("Creating article...");
        Long articleId = creatorService.createArticle(article);

        log.info("Article created");
        return new ResponseEntity<>(articleId, HttpStatus.CREATED);
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle (@PathVariable("id") Long id,
                                                          @RequestBody Article article){
        log.info("Updating article...");
        ArticleResponse newArticle = creatorService.updateArticle(id, article);
        return new ResponseEntity<>(newArticle, HttpStatus.OK);
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> deleteArticle (@PathVariable("id") Long id){
        log.info("Deleting article...");
        creatorService.deleteArticle(id);

        log.info("Article deleted...");
        return new ResponseEntity<>("Article Deleted", HttpStatus.OK);
    }
}