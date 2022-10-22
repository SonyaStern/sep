package com.kth.mse.sep.controller;

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

import static com.kth.mse.sep.TestUtils.loadResourceAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ActivityTaskTest {

    private final MockMvc mvc;

    @Autowired
    public ActivityTaskTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void createActivityTask() throws Exception {
        String requestBody = loadResourceAsString("requests/create_activity_task.json");
        String expectedJson = loadResourceAsString("responses/activity_task.json");

        var response = mvc.perform(post("/activities")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Activity task response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }


    @Test
    @Sql({"classpath:scripts/insert_activity_task.sql"})
    void getActivityTaskById() throws Exception {
        String expectedJson = loadResourceAsString("responses/activity_task.json");

        var response = mvc.perform(get("/activities/{activityId}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Activity task response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void getNonExistentActivityTaskById() throws Exception {

        var response = mvc.perform(get("/activities/{activityId}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        Assertions.assertEquals("", actualJson, "Client response didn't match");
    }

    @Test
    @Sql({"classpath:scripts/insert_activity_task.sql"})
    void patchClient() throws Exception {
        String requestBody = loadResourceAsString("requests/patch_activity_task.json");
        String expectedJson = loadResourceAsString("responses/patch_activity_task.json");

        var response = mvc.perform(patch("/activities/{activityId}", 2L)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Activity task response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }
}
