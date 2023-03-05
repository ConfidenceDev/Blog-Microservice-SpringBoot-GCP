package me.plurg.creator.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import me.plurg.creator.dao.CreatorRepo;
import me.plurg.creator.model.Creator;
import me.plurg.creator.service.CreatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

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

    @Test
    void getAllCreators() throws Exception{
        //Actual
        MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders
                        .get("")
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
                        Charset.defaultCharset()), new TypeReference<>() {
                }
        );
        return objectMapper.writeValueAsString(creators);
    }
}