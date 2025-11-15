package com.cub.coindesk.currency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired MockMvc mockMvc;
    private final ObjectMapper om = new ObjectMapper();

    @Test
    void list_sorted_by_code() throws Exception {
        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code", notNullValue()));
    }

    @Test
    void create_and_update_and_delete() throws Exception {
        String payload = "{\"code\":\"TWD\",\"nameZh\":\"新台幣\"}";
        MvcResult mvcResult = mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        JsonNode node = om.readTree(body);
        long id = node.get("id").asLong();

        mockMvc.perform(put("/api/currencies/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nameZh\":\"臺幣\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameZh", is("臺幣")));

        mockMvc.perform(delete("/api/currencies/" + id))
                .andExpect(status().isNoContent());
    }
}
