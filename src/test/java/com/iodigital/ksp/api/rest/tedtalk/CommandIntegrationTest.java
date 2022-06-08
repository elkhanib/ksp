package com.iodigital.ksp.api.rest.tedtalk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iodigital.ksp.domain.CreateTedTalkRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class CommandIntegrationTest {

    private static final String BASE_URL = "/v1/ted-talks/";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("it adds a new ted-talk")
    void create() throws Exception {
        //Arrange
        var request = new CreateTedTalkRequest(
                "How not to get hired!",
                "Elkhan Ibrahimov",
                LocalDate.of(2022, 6, 5),
                0L,
                0L,
                "https://elkhanib.dev");

        //Act
        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title", equalTo("How not to get hired!")))
                .andExpect(jsonPath("$.author", equalTo("Elkhan Ibrahimov")))
                .andExpect(jsonPath("$.talkDate", equalTo("2022-06-05")))
                .andExpect(jsonPath("$.viewCount", equalTo(0)))
                .andExpect(jsonPath("$.likeCount", equalTo(0)))
                .andExpect(jsonPath("$.link", equalTo("https://elkhanib.dev")));
    }

    @Test
    @DisplayName("it tries to create a new ted-talk with the invalid ted-talk link")
    void createWithEmptyLink() throws Exception {
        //Arrange
        var request = new CreateTedTalkRequest(
                "How not to get hired!",
                "Elkhan Ibrahimov",
                LocalDate.of(2022, 6, 5),
                0L,
                0L,
                "");

        //Act
        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.error", equalTo("Bad Request")))
                .andExpect(jsonPath("$.message", equalTo("Argument validation failed")))
                .andExpect(jsonPath("$.errors.[0].property", equalTo("link")))
                .andExpect(jsonPath("$.errors.[0].message", equalTo("value cannot be a null or an empty")));
    }
}
