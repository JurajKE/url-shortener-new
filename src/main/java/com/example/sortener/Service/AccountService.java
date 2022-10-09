package com.example.sortener.Service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.dto.ResponseDto;
import com.example.sortener.entity.Account;
import com.example.sortener.repository.AccountRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountAssembler accountAssembler;

    public AccountService(AccountRepository accountRepository, AccountAssembler accountAssembler) {
        this.accountRepository = accountRepository;
        this.accountAssembler = accountAssembler;
    }

    public ResponseDto saveAccount(String accountId) {
        Account byAccountId = accountRepository.findByAccountId(accountId);
        return isNull(byAccountId) ?
                accountAssembler.assembleResponse(accountRepository.save(accountAssembler.assembleEntity(accountId)))
                : accountAssembler.assembleFailedResponse();
    }

}
