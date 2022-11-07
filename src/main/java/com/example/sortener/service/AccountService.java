package com.example.sortener.service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.dto.account.ResponseAccountDto;
import com.example.sortener.entity.Account;
import com.example.sortener.entity.Url;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.validator.ApplicationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.example.sortener.constants.AppConstants.CHARACTERS;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.RandomStringUtils.random;

@Service
@RequiredArgsConstructor
public class AccountService {

    @NonNull
    private final AccountRepository accountRepository;
    @NonNull
    private final AccountAssembler accountAssembler;
    @NonNull
    private final ApplicationValidator validator;

    public ResponseAccountDto saveAccount(RequestAccountDto requestAccountDto) {
        validator.validateAccount(requestAccountDto);
        Account account = accountRepository.findByAccountId(requestAccountDto.getAccountId());
        return isNull(account) ?
                accountAssembler.assembleResponse(accountRepository.save(new Account(requestAccountDto.getAccountId(), random(8, CHARACTERS))))
                : accountAssembler.assembleFailedResponse();
        //TODO change Status Code
    }

    public Map<String, Integer> getStatistics(String accountId) {
        Account account = accountRepository.findByAccountId(accountId);
        if (account.getUrlList().isEmpty()) {
            throw new RecordFoundException("Account with this account id " + accountId + " does not exist");
        }
        return account.getUrlList().stream().collect(toMap(Url::getOriginalUrl, Url::getCalls, (link, calls) -> calls));
    }

}
