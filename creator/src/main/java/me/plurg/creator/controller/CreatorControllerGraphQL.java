package me.plurg.creator.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.extern.log4j.Log4j2;
import me.plurg.creator.model.Creator;
import me.plurg.creator.model.User;
import me.plurg.creator.service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/creatorsql")
@Log4j2
public class CreatorControllerGraphQL {

    @Autowired
    private CreatorService creatorService;

    @QueryMapping(name = "creators")
    public ResponseEntity<List<Creator>> getAllCreators (){
        log.info("Fetching creators...");
        List<Creator> allCreators = creatorService.getAllCreators();

        log.info("Users retrieved");
        return new ResponseEntity<>(allCreators, HttpStatus.OK);
    }

    @MutationMapping
    public ResponseEntity<Long> createUser (@Validated @RequestBody User user){
        log.info("Registering user...");
        Long userId = creatorService.createUser(user);

        log.info("User registered");
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }


}
