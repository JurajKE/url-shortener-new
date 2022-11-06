package com.example.sortener.controller;

import com.example.sortener.Service.UrlService;
import com.example.sortener.dto.UrlDto;
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

import static com.example.sortener.constants.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UrlControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private UrlController controller;

    @Mock
    private UrlService urlService;

    @Mock
    private ApplicationValidator validator;

    @Mock
    HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
    private final UrlDto dto = new UrlDto();

    @Before
    public void init() {
        mockMvc = standaloneSetup(controller).setControllerAdvice(new CustomExceptionHandler()).build();
        when(validator.authenticate(any())).thenReturn(ACCOUNT_ID);
        when(urlService.saveShortUrl(any())).thenReturn(dto);

        dto.setUrl(URL);
    }

    @Test
    public void registerUrl_whenDtoIsNotNull_returnOk() throws Exception {
        mockMvc.perform(post("/register", mockedRequest).contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();

        verify(validator, times(1)).authenticate(any());
        verify(urlService, times(1)).saveShortUrl(any());
    }

    @Test
    public void getStatisticsForUser_whenAccountIdIsValid_returnOk() throws Exception {
        mockMvc.perform(get("/statistics/" + "juraj", mockedRequest).contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();

        verify(validator, times(1)).authenticate(any());
        verify(urlService, times(1)).getStatistics(any());
    }

    @Test
    public void redirectToOriginalUrl_whenShortLinkIsValid() throws Exception {
        mockMvc.perform(get("/" + SHORT_URL).contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();

        verify(urlService, times(1)).redirectToOriginalUrl(any(), any());
    }

}
