package com.example.sortener.repository;

import com.example.sortener.configuration.RepositoryConfiguration;
import com.example.sortener.entity.Url;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.sortener.constants.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private UrlRepository urlRepository;

    @Before
    public void init() {
        runSQL("INSERT INTO account(id,account_id,password) VALUES('%s','%s','%s')", 1, ACCOUNT_ID, ACCOUNT_PASSWORD);
        runSQL("INSERT INTO url(id, original_url, short_url, calls, redirect_type, account_id) VALUES('%s','%s','%s','%s','%s','%s')", 1, URL, SHORT_URL, 1, 301, 1);
    }

    @Test
    public void getUrlByShortUrl_shouldReturnUrl_whenGivenPattern() {
        Url returned = urlRepository.findByShortUrl(SHORT_URL);

        assertNotNull(returned);
        assertEquals(URL, returned.getOriginalUrl());
        assertEquals(301, returned.getRedirectType());
        assertEquals(1, returned.getCalls());
    }

}
