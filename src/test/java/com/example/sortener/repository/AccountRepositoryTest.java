package com.example.sortener.repository;

import com.example.sortener.configuration.RepositoryConfiguration;
import com.example.sortener.entity.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.sortener.constants.TestConstants.ACCOUNT_ID;
import static com.example.sortener.constants.TestConstants.ACCOUNT_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest extends RepositoryConfiguration {

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void init() {
        runSQL("INSERT INTO account(id,account_id,password) VALUES('%s','%s','%s')", 1, ACCOUNT_ID, ACCOUNT_PASSWORD);
    }

    @Test
    public void getAccountByAccountId_shouldReturnAccount_whenGivenPattern() {
        Account returned = accountRepository.findByAccountId(ACCOUNT_ID);

        assertNotNull(returned);
        assertEquals(ACCOUNT_PASSWORD, returned.getPassword());
    }

}
