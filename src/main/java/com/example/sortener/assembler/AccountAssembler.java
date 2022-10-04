package com.example.sortener.assembler;

import com.example.sortener.dto.ResponseDto;
import com.example.sortener.entity.Account;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class AccountAssembler {

    public Account assembleEntity(String accountId) {
        if (isEmpty(accountId)) {
            return null;
        }
        Account url = new Account();
        url.setAccountId(accountId);
        url.setUrlList(null);
        url.setPassword(generatePasword());
        url.setId(12);

        return url;
    }

    public ResponseDto assembleResponse(Account account) {
        if (isNull(account)) {
            return null;
        }
        ResponseDto response = new ResponseDto();
        response.setPassword(account.getPassword());
        response.setDescription("Your account is opened");
        response.setSuccess(true);

        return response;
    }

    public ResponseDto assembleFailedResponse() {
        ResponseDto response = new ResponseDto();
        response.setDescription("Your account is already exist");
        response.setSuccess(false);

        return response;
    }

    private String generatePasword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return random(8, characters);
    }

}
