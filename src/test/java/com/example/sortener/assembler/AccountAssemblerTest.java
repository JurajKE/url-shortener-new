package com.example.sortener.assembler;

import com.example.sortener.dto.ResponseDto;
import com.example.sortener.entity.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.sortener.constants.TestConstants.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountAssemblerTest {

    @InjectMocks
    private AccountAssembler accountAssembler;
    private Account account;

    @Test
    public void assembleResponse_whenEntityNotNull_returnAssembledResponse() {
        ResponseDto result = accountAssembler.assembleResponse(account);

        assertNotNull(result);
        assertEquals(OPENED_ACCOUNT, result.getDescription());
        assertTrue(result.isSuccess());
        assertEquals(ACCOUNT_PASSWORD, result.getPassword());
    }

    @Test
    public void assembleResponse_whenEntityIsNull_returnNull() {
        assertNull(accountAssembler.assembleResponse(null));
    }

    @Test
    public void assemblesFailResponse_returnAssembledFailResponse() {
        ResponseDto result = accountAssembler.assembleFailedResponse();

        assertNotNull(result);
        assertEquals(EXIST_ACCOUNT, result.getDescription());
        assertFalse(result.isSuccess());
    }

    @Before
    public void init() {
        account = new Account();
        account.setPassword(ACCOUNT_PASSWORD);
    }

}