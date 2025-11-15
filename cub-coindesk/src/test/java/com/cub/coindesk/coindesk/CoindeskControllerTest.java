package com.cub.coindesk.coindesk;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoindeskControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void raw_or_mock_available() throws Exception {
        mockMvc.perform(get("/api/coindesk/raw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bpi.USD.code", is("USD")));
    }

    @Test
    void transformed_shape() throws Exception {
        mockMvc.perform(get("/api/coindesk/transform"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updatedTime", not(isEmptyString())))
                .andExpect(jsonPath("$.items", hasSize(greaterThan(0))))
                .andExpect(jsonPath("$.items[0].code", notNullValue()))
                .andExpect(jsonPath("$.items[0].nameZh", notNullValue()))
                .andExpect(jsonPath("$.items[0].rate", notNullValue()));
    }
}
