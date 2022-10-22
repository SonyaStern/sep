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
class ClientControllerTest {

    private final MockMvc mvc;

    @Autowired
    public ClientControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void createClient() throws Exception {
        String requestBody = loadResourceAsString("requests/create_client.json");
        String expectedJson = loadResourceAsString("responses/client.json");

        var response = mvc.perform(post("/clients")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Client response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @Sql({"classpath:scripts/insert_client.sql"})
    void patchClient() throws Exception {
        String requestBody = loadResourceAsString("requests/patch_client.json");
        String expectedJson = loadResourceAsString("responses/patch_client.json");

        var response = mvc.perform(patch("/clients/{clientId}", 2L)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Client response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    @Sql({"classpath:scripts/insert_client.sql"})
    void getClientById() throws Exception {
        String expectedJson = loadResourceAsString("responses/client.json");

        var response = mvc.perform(get("/clients/{clientId}", 1L))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        JSONAssert.assertEquals("Client response didn't match",
                expectedJson, actualJson, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void getNonExistentEmployeeById() throws Exception {

        var response = mvc.perform(get("/clients/{clientId}", 100L))
                .andExpect(status().isOk())
                .andReturn();
        var actualJson = response.getResponse().getContentAsString();

        Assertions.assertEquals("", actualJson, "Client response didn't match");
    }
}