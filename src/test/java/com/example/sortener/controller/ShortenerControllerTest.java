package com.example.sortener.controller;

import com.example.sortener.entity.Account;
import com.example.sortener.service.ShortenerService;
import com.example.sortener.dto.shortener.RequestUrlDto;
import com.example.sortener.dto.shortener.ResponseUrlDto;
import com.example.sortener.exceptions.CustomExceptionHandler;
import com.example.sortener.validator.ApplicationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
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
public class ShortenerControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private ShortenerController controller;

    @Mock
    private ShortenerService shortenerService;

//    @Mock
//    HttpServletRequest mockedRequest = mock(HttpServletRequest.class);

    @Mock
    Authentication authentication = mock(Authentication.class);
    private final RequestUrlDto dto = new RequestUrlDto();

    @Before
    public void init() {
        mockMvc = standaloneSetup(controller).setControllerAdvice(new CustomExceptionHandler()).build();
        when(shortenerService.saveShortUrl(any(), any())).thenReturn(new ResponseUrlDto());

        dto.setUrl(URL);
//        mockedRequest.setAttribute(ACCOUNT_ID, new Account());
    }

    @Test
    public void registerUrl_whenDtoIsNotNull_returnOk() throws Exception {
        mockMvc.perform(post("/register", authentication).contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();

        verify(shortenerService, times(1)).saveShortUrl(any(), any());
    }

    @Test
    public void redirectToOriginalUrl_whenShortLinkIsValid() throws Exception {
        mockMvc.perform(get("/" + SHORT_URL).contentType(APPLICATION_JSON_VALUE).content("{}"))
                .andExpect(status().isOk()).andReturn();

        verify(shortenerService, times(1)).redirectToOriginalUrl(any(), any());
    }

}
