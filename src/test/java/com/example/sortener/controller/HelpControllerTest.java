package com.example.sortener.controller;

import com.example.sortener.exceptions.CustomExceptionHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class HelpControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private HelpController controller;

    @Before
    public void init() {
        mockMvc = standaloneSetup(controller).setControllerAdvice(new CustomExceptionHandler()).build();
    }

    @Test
    public void createAccosunt_whenDtoIsNotNull_returnOk() throws Exception {
        mockMvc.perform(get("/help").contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();
    }

}
