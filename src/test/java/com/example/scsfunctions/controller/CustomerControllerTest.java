package com.example.scsfunctions.controller;

import com.example.scsfunctions.controller.dto.BatchCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockRequestDispatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Disabled
  @Test
  void start() throws Exception {
    BatchCommand batchCommand = new BatchCommand("Bobby", "FRA");
    mockMvc.perform(MockMvcRequestBuilders.post("/batch").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(batchCommand))).andExpect(status().isCreated());
  }
}