package me.plurg.creator.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import me.plurg.creator.dao.CreatorRepo;
import me.plurg.creator.model.Creator;
import me.plurg.creator.model.User;
import me.plurg.creator.service.CreatorService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.StreamUtils.copyToString;

@SpringBootTest({"server.port=0"})
@EnableConfigurationProperties
@AutoConfigureMockMvc
class CreatorControllerTest {

    @Autowired
    private CreatorService creatorService;

    @Autowired
    private CreatorRepo creatorRepo;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    private MockMvc mockMvc;

    @RegisterExtension
    final static WireMockExtension wireMockServer
            = WireMockExtension.newInstance()
            .options(WireMockConfiguration
                    .wireMockConfig()
                    .port(8080))
            .build();

    private final ObjectMapper objectMapper
            = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @BeforeEach
    void setUp() throws Exception {
        createAUser();
    }

    private void createAUser() throws Exception {
        circuitBreakerRegistry.circuitBreaker("external").reset();
        /* wireMockServer.stubFor(WireMock.post(
                WireMock.urlMatching("/v1/creators/register"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.CREATED.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(
                                CreatorControllerTest.class
                                        .getClassLoader()
                                        .getResourceAsStream("mock/User.json"),
                                defaultCharset()
                        )))); */

        User user = getUser();
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/creators/register")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    private User getUser(){
        return User.builder()
                .name("John")
                .build();
    }

    @DisplayName("Get all creators - Success Scenario")
    @Test
    void testWhenGetAllCreators() throws Exception {
        //Actual
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                                .get("/v1/creators")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        //Verification
        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = getCreatorsResponse();

        //Assert
        assertEquals(expectedResponse, actualResponse);
    }

    private String getCreatorsResponse() throws IOException {
        List<Creator> creators = objectMapper.readValue(
                copyToString(CreatorControllerTest.class.getClassLoader()
                                .getResourceAsStream("mock/Creator.json"),
                        defaultCharset()), new TypeReference<>() {
                }
        );
        return objectMapper.writeValueAsString(creators);
    }
}