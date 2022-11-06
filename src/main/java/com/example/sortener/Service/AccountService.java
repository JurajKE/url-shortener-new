package com.example.sortener.Service;

import com.example.sortener.assembler.AccountAssembler;
import com.example.sortener.dto.AccountBaseDto;
import com.example.sortener.dto.ResponseDto;
import com.example.sortener.entity.Account;
import com.example.sortener.repository.AccountRepository;
import com.example.sortener.validator.ApplicationValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.sortener.Constants.AppConstants.CHARACTERS;
import static java.util.Objects.isNull;
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

    public ResponseDto saveAccount(AccountBaseDto accountBaseDto) {
        validator.validateAccount(accountBaseDto);
        Account account = accountRepository.findByAccountId(accountBaseDto.getAccountId());
        return isNull(account) ?
                accountAssembler.assembleResponse(accountRepository.save(new Account(accountBaseDto.getAccountId(), random(8, CHARACTERS))))
                : accountAssembler.assembleFailedResponse();
        //TODO change Status Code
    }

}
