package com.example.sortener.assembler;

import com.example.sortener.dto.UrlDto;
import com.example.sortener.entity.Account;
import com.example.sortener.entity.Url;
import com.example.sortener.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.sortener.constants.TestConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlAssemblerTest {

    @InjectMocks
    private UrlAssembler urlAssembler;
    @Mock
    private AccountRepository accountRepository;
    private UrlDto urlDto;
    private Url url;

    @Test
    public void assembleEntity_whenDtoNotNull_returnAssembledEntity() {
        when(accountRepository.findByAccountId(ACCOUNT_ID)).thenReturn(new Account());
        Url result = urlAssembler.assembleEntity(urlDto);

        assertNotNull(result);
        assertEquals(urlDto.getUrl(), result.getOriginalUrl());
        assertNotNull(result.getShortUrl());
        assertEquals(302, result.getRedirectType());
        assertNotNull(result.getAccountId());
    }

    @Test
    public void assembleEntity_whenDtoIsNull_returnNull() {
        assertNull(urlAssembler.assembleEntity(null));
    }

    @Test
    public void assembleDto_whenEntityNotNull_returnAssembledDto() {
        UrlDto result = urlAssembler.assembleDto(url);

        assertNotNull(result);
        assertEquals(SHORT_URL, result.getShortUrl());
    }

    @Test
    public void assembleDto_whenEntityIsNull_returnNull() {
        assertNull(urlAssembler.assembleDto(null));
    }

    @Before
    public void init() {
        urlDto = new UrlDto();
        urlDto.setUrl(URL);
        urlDto.setAccountId(ACCOUNT_ID);

        url = new Url();
        url.setShortUrl(SHORT_URL);
    }

}
