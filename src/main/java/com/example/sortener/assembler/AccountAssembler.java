package com.example.sortener.assembler;

import com.example.sortener.dto.ResponseDto;
import com.example.sortener.entity.Account;
import com.example.sortener.repository.AccountRepository;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.RandomStringUtils.random;

@Component
public class AccountAssembler {

    public Account assembleEntity(String accountId) {
        if (accountId == null ) {
            return null;
        }

            Account account = new Account();
            account.setAccountId(accountId);
            account.setUrlList(null);
            account.setPassword(generatePasword());

            return account;

    }

    public ResponseDto assembleResponse(Account account) {
        if (isNull(account)) {
            return null;
        }
        ResponseDto response = new ResponseDto();
        response.setPassword(account.getPassword());
        response.setDescription("Your account is opened.");
        response.setSuccess(true);

        return response;
    }

    public ResponseDto assembleFailedResponse() {
        ResponseDto response = new ResponseDto();
        response.setDescription("Your account already exist.");
        response.setSuccess(false);

        return response;
    }

    private String generatePasword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return random(8, characters);
    }

}
