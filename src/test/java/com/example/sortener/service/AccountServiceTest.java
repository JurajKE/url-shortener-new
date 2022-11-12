package com.example.sortener.service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.dto.account.ResponseAccountDto;
import com.example.sortener.entity.Account;
import com.example.sortener.entity.Url;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.validator.ApplicationValidator;
import com.example.sortener.validator.ApplicationValidatorTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.sortener.constants.TestConstants.ACCOUNT_ID;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountAssembler accountAssembler;

    @Mock
    private ApplicationValidator validator;

    @InjectMocks
    private AccountService accountService;

    private Account account;
    private RequestAccountDto accountDto;
    private ResponseAccountDto responseAccountDto;

    private List<Url> urlList = new ArrayList<>();


    @Test
    public void saveAccount_shouldCallRepositoryAndAssembler_whenCalled() {
        when(accountRepository.findByAccountId(any())).thenReturn(null);
        when(accountRepository.save(any())).thenReturn(account);
        when(accountAssembler.assembleResponse(any())).thenReturn(responseAccountDto);
        accountService.saveAccount(accountDto);

        verify(validator, times(1)).validateAccount(any());
        verify(accountAssembler, times(1)).assembleResponse(account);
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    public void saveAccount_shouldCallAssemblerToFailedMessage_whenCalled() {
        when(accountRepository.findByAccountId(any())).thenReturn(account);
        ResponseAccountDto response = accountService.saveAccount(accountDto);

        verify(validator, times(1)).validateAccount(any());
        verify(accountAssembler, times(1)).assembleFailedResponse();

    }

    @Test
    public void getStatistics_shouldCalculateToMap_whenCalled() {
        when(accountRepository.findByAccountId(any())).thenReturn(account);
        account.setUrlList(urlList);
        Map<String, Integer> map = accountService.getStatistics(ACCOUNT_ID);

        assertNotNull(map);
    }

    @Test
    public void getStatistics_shouldThrowRecordFoundException_whenAccountDoesNotExist() {
        when(accountRepository.findByAccountId(any())).thenReturn(null);

        RecordFoundException exception = assertThrows(RecordFoundException.class, () -> accountService.getStatistics(ACCOUNT_ID));

        assertEquals("Account with this account id " + ACCOUNT_ID + " does not exist", exception.getMessage());
    }

    @Before
    public void init() {
        account = new Account();
        account.setAccountId(ACCOUNT_ID);

        accountDto = new RequestAccountDto();
        accountDto.setAccountId(ACCOUNT_ID);

        responseAccountDto = new ResponseAccountDto();

        urlList.add(new Url());
    }

}
