package com.example.sortener.controller;

import com.example.sortener.Service.AccountService;
import com.example.sortener.exceptions.CustomExceptionHandler;
import com.example.sortener.validator.ApplicationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService accountService;

    @Mock
    private ApplicationValidator validator;

    @Mock
    HttpServletRequest mockedRequest = mock(HttpServletRequest.class);

    @Before
    public void init() {
        mockMvc = standaloneSetup(controller).setControllerAdvice(new CustomExceptionHandler()).build();
    }

    @Test
    public void createAccount_whenDtoIsNotNull_returnOk() throws Exception {
        mockMvc.perform(post("/account").contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();

        verify(accountService, times(1)).saveAccount(any());
    }

    @Test
    public void getStatisticsForUser_whenAccountIdIsValid_returnOk() throws Exception {
        mockMvc.perform(get("/statistics/" + "juraj", mockedRequest).contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();

        verify(validator, times(1)).authenticate(any());
        verify(accountService, times(1)).getStatistics(any());
    }

}
