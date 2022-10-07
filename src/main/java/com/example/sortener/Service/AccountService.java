package com.example.sortener.Service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.dto.ResponseDto;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.AccountRepository;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountAssembler accountAssembler;

    public AccountService(AccountRepository accountRepository, AccountAssembler accountAssembler) {
        this.accountRepository = accountRepository;
        this.accountAssembler = accountAssembler;
    }

    public ResponseDto saveAccount(String accountId) {
        ofNullable(accountRepository.findByAccountId(accountId)).ifPresent(account -> {
            throw new RecordFoundException("Account id '" + accountId + " already exist");
        });
        return accountAssembler.assembleResponse(accountRepository.save(accountAssembler.assembleEntity(accountId)));
//        return accountAssembler.assembleFailedResponse();
    }

}
