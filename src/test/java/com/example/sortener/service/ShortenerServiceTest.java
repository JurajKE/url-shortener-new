package com.example.sortener.service;

import com.example.sortener.assembler.ShortenerAssembler;
import com.example.sortener.dto.shortener.RequestUrlDto;
import com.example.sortener.dto.shortener.ResponseUrlDto;
import com.example.sortener.entity.Account;
import com.example.sortener.entity.Url;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.UrlRepository;
import com.example.sortener.validator.ApplicationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.sortener.constants.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShortenerServiceTest {

    @InjectMocks
    private ShortenerService shortenerService;
    @Mock
    private ApplicationValidator validator;

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private ShortenerAssembler shortenerAssembler;
    @Mock
    HttpServletResponse mockedResponse = mock(HttpServletResponse.class);

    private RequestUrlDto urlDto;
    private Url url;
    private Account account;

    @Test
    public void saveShortUrl_shouldCallRepositoryAndAssembler_whenCalled() {
        when(shortenerAssembler.assembleDto(any())).thenReturn(new ResponseUrlDto());
        when(shortenerAssembler.assembleEntity(any(), any())).thenReturn(url);
        shortenerService.saveShortUrl(urlDto, ACCOUNT_ID);

        verify(validator, times(1)).validateUrl(any());
        verify(shortenerAssembler, times(1)).assembleEntity(urlDto, ACCOUNT_ID);
        verify(shortenerAssembler, times(1)).assembleDto(any());
    }

    @Test
    public void redirectToOriginalUrl_shouldCallRepositoryAndAssembler_whenCalled() throws IOException {
        when(urlRepository.findByShortUrl(SHORT_URL)).thenReturn(url);
        shortenerService.redirectToOriginalUrl(SHORT_URL, mockedResponse);

        verify(mockedResponse, times(1)).sendRedirect(any());
    }

    @Test
    public void redirectToOriginalUrl_shouldThrowRecordFoundException_whenUrlDoesNotExist() {
        when(urlRepository.findByShortUrl(SHORT_URL)).thenReturn(null);

        RecordFoundException exception = assertThrows(RecordFoundException.class, () -> shortenerService.redirectToOriginalUrl(SHORT_URL, mockedResponse));

        assertEquals("Url with this short link " + SHORT_URL + " does not exist", exception.getMessage());
    }

    @Before
    public void init() {
        urlDto = new RequestUrlDto();
        urlDto.setUrl(URL);

        account = new Account();
        account.setAccountId(ACCOUNT_ID);

        url = new Url();
        url.setOriginalUrl(URL);
        url.setAccountId(account);
        url.setId(1);
    }

}
