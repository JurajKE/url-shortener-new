package com.example.sortener.service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.dto.account.ResponseAccountDto;
import com.example.sortener.entity.Account;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.validator.ApplicationValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.sortener.constants.TestConstants.ACCOUNT_ID;
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
        accountService.saveAccount(accountDto);

        verify(validator, times(1)).validateAccount(any());
        verify(accountAssembler, times(1)).assembleFailedResponse();
    }

    @Before
    public void init() {
        account = new Account();
        account.setAccountId(ACCOUNT_ID);

        accountDto = new RequestAccountDto();
        accountDto.setAccountId(ACCOUNT_ID);

        responseAccountDto = new ResponseAccountDto();
    }

}
