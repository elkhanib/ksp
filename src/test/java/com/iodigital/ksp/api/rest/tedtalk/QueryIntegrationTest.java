package com.iodigital.ksp.api.rest.tedtalk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class QueryIntegrationTest {

    private static final String BASE_URL = "/v1/ted-talks";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("it gets ted-talk by id")
    void findById() throws Exception {
        //Act
        mockMvc.perform(get(BASE_URL + "/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(200)))
                .andExpect(jsonPath("$.title", equalTo("A more accurate way to calculate emissions")))
                .andExpect(jsonPath("$.author", equalTo("Charlotte Degot")))
                .andExpect(jsonPath("$.talkDate", equalTo("2021-09-01")))
                .andExpect(jsonPath("$.viewCount", equalTo(1400000)))
                .andExpect(jsonPath("$.likeCount", equalTo(43000)))
                .andExpect(jsonPath("$.link", equalTo("https://ted.com/talks/charlotte_degot_a_more_accurate_way_to_calculate_emissions")));
    }

    @Test
    @DisplayName("it finds ted-talk by title contains with case-insensitivity")
    void findByTitle() throws Exception {
        //Act
        mockMvc.perform(get(BASE_URL + "/by-title?title=ClEaN"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0].id", equalTo(6)))
                .andExpect(jsonPath("$.[0].title", equalTo("The tragedy of air pollution — and an urgent demand for clean air")))
                .andExpect(jsonPath("$.[0].author", equalTo("Rosamund Adoo-Kissi-Debrah")))
                .andExpect(jsonPath("$.[0].talkDate", equalTo("2021-10-01")))
                .andExpect(jsonPath("$.[0].viewCount", equalTo(422000)))
                .andExpect(jsonPath("$.[0].likeCount", equalTo(12000)))
                .andExpect(jsonPath("$.[0].link", equalTo("https://ted.com/talks/rosamund_adoo_kissi_debrah_the_tragedy_of_air_pollution_and_an_urgent_demand_for_clean_air")))
                .andExpect(jsonPath("$.[1].id", equalTo(179)))
                .andExpect(jsonPath("$.[1].title", equalTo("How much clean electricity do we really need?")))
                .andExpect(jsonPath("$.[1].author", equalTo("Solomon Goldstein-Rose")))
                .andExpect(jsonPath("$.[1].talkDate", equalTo("2021-10-01")))
                .andExpect(jsonPath("$.[1].viewCount", equalTo(1400000)))
                .andExpect(jsonPath("$.[1].likeCount", equalTo(42000)))
                .andExpect(jsonPath("$.[1].link", equalTo("https://ted.com/talks/solomon_goldstein_rose_how_much_clean_electricity_do_we_really_need")))
                .andExpect(jsonPath("$.[2].id", equalTo(275)))
                .andExpect(jsonPath("$.[2].title", equalTo("A cleanse won't detox your body — but here's what will")))
                .andExpect(jsonPath("$.[2].author", equalTo("Jen Gunter")))
                .andExpect(jsonPath("$.[2].talkDate", equalTo("2021-07-01")))
                .andExpect(jsonPath("$.[2].viewCount", equalTo(2700000)))
                .andExpect(jsonPath("$.[2].likeCount", equalTo(82000)))
                .andExpect(jsonPath("$.[2].link", equalTo("https://ted.com/talks/jen_gunter_a_cleanse_won_t_detox_your_body_but_here_s_what_will")));
    }
}
