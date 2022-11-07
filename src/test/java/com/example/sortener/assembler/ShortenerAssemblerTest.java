package com.example.sortener.assembler;

import com.example.sortener.dto.shortener.RequestUrlDto;
import com.example.sortener.dto.shortener.ResponseUrlDto;
import com.example.sortener.entity.Account;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.validator.ApplicationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.sortener.constants.AppConstants.APP_LINK;
import static com.example.sortener.constants.TestConstants.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShortenerAssemblerTest {

    @InjectMocks
    private ShortenerAssembler shortenerAssembler;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private ApplicationValidator validator;
    private RequestUrlDto urlDto;
    private Url url;

    @Test
    public void assembleEntity_whenDtoNotNull_returnAssembledEntity() {
        when(accountRepository.findByAccountId(ACCOUNT_ID)).thenReturn(new Account());
        when(validator.encodedUrl(any())).thenReturn(SHORT_URL);
        Url result = shortenerAssembler.assembleEntity(urlDto, ACCOUNT_ID);

        assertNotNull(result);
        assertEquals(urlDto.getUrl(), result.getOriginalUrl());
        assertNotNull(result.getShortUrl());
        assertEquals(302, result.getRedirectType());
        assertNotNull(result.getAccountId());
    }

    @Test
    public void assembleEntity_whenDtoIsNull_returnNull() {
        assertNull(shortenerAssembler.assembleEntity(null, null));
    }

    @Test
    public void assembleDto_whenEntityNotNull_returnAssembledDto() {
        ResponseUrlDto result = shortenerAssembler.assembleDto(url);

        assertNotNull(result);
        assertEquals(APP_LINK + SHORT_URL, result.getShortUrl());
    }

    @Test
    public void assembleDto_whenEntityIsNull_returnNull() {
        assertNull(shortenerAssembler.assembleDto(null));
    }

    @Before
    public void init() {
        urlDto = new RequestUrlDto();
        urlDto.setUrl(URL);

        url = new Url();
        url.setShortUrl(SHORT_URL);
    }

}
