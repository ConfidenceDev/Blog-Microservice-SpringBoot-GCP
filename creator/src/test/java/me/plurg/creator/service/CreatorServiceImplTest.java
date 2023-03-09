package me.plurg.creator.service;

import me.plurg.creator.dao.CreatorRepo;
import me.plurg.creator.entity.CreatorEntity;
import me.plurg.creator.model.Article;
import me.plurg.creator.model.Creator;
import me.plurg.creator.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreatorServiceImplTest {

    @Mock
    private CreatorRepo creatorRepo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    CreatorService creatorService = new CreatorServiceImpl();

    @DisplayName("Retrieving all creators - Success Scenario")
    @Test
    void getAllCreators() {
        //Mocking
        CreatorEntity creatorEntity = getMockCreator();
        when(creatorRepo.findAll())
                .thenReturn(List.of(creatorEntity));

        //Actual
        List<Creator> creators = creatorService.getAllCreators();

        //Verification
        verify(creatorRepo, times(1)).findAll();

        //Assert
        assertNotNull(creators);
        assertEquals(creatorEntity.getId(), creators.get(0).getId());
    }

    @DisplayName("Creating a new user - Success Scenario")
    @Test
    void createUserSuccess() {
        //Mocking
        CreatorEntity creator = getMockCreator();
        User user = getUser();

        //Actual
        when(creatorRepo.save(any(CreatorEntity.class)))
                .thenReturn(creator);

        //Verification
        long userId = creatorService.createUser(user);
        verify(creatorRepo, times(1))
                .save(any());

        //Assert
        assertEquals(creator.getId(), userId);
    }

    @DisplayName("Removing a user - Failure Scenario")
    @Test
    @Disabled
    void deleteUserFailure() {
        //Actual
        when(creatorRepo.findById(2L))
                .thenThrow(new RuntimeException());

        //Verification
        creatorService.deleteUser(1L);
        verify(creatorService, times(1))
                .deleteUser(anyLong());
    }

    @DisplayName("Creating an article - Success scenario")
    @Test
    @Disabled
    void createArticle() {
        //Mocking
        Article article = getMockArticle();

        //Actual
        when(creatorService.createArticle(any(Article.class)))
                .thenReturn(1L);

        //Verification
        long articleId = creatorService.createArticle(article);

        //Assert
        assertNotEquals(articleId, 0);
    }

    private CreatorEntity getMockCreator(){
        return CreatorEntity.builder()
                .id(1)
                .name("John")
                .created(Instant.now())
                .build();
    }

    private User getUser(){
        return User.builder()
                .name("John")
                .build();
    }

    private Creator getCreator(){
        return Creator.builder()
                .id(1)
                .name("John")
                .build();
    }

    private Article getMockArticle (){
        return Article.builder()
                .posterId(1)
                .title("Test")
                .content("This is a test")
                .build();
    }
}