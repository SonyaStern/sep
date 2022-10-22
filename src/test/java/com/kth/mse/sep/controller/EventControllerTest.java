package com.kth.mse.sep.controller;

import static com.kth.mse.sep.TestUtils.loadResourceAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class EventControllerTest {

    private final MockMvc mvc;

    @Autowired
    public EventControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void createEvent() throws Exception {
        String requestBody = loadResourceAsString("requests/create_event.json");
        String expectedJson = loadResourceAsString("responses/event.json");

        var response = mvc.perform(post("/events")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Event response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @Sql({"classpath:scripts/insert_client.sql", "classpath:scripts/insert_event.sql"})
    void patchEvent() throws Exception {
        String requestBody = loadResourceAsString("requests/patch_event.json");
        String expectedJson = loadResourceAsString("responses/patch_event.json");

        var response = mvc.perform(patch("/events/{eventId}", 2L)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Event response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @Sql({"classpath:scripts/insert_client.sql", "classpath:scripts/insert_event.sql"})
    void getEventById() throws Exception {
        String expectedJson = loadResourceAsString("responses/get_event.json");

        var response = mvc.perform(get("/events/{eventId}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Event response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void getNonExistentEmployeeById() throws Exception {

        var response = mvc.perform(get("/events/{eventId}", 100L))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        Assertions.assertEquals("", actualJson, "Event response didn't match");
    }
}