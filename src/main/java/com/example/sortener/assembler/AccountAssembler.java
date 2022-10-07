package com.example.sortener.assembler;

import com.example.sortener.dto.ResponseDto;
import com.example.sortener.entity.Account;
import com.example.sortener.repository.AccountRepository;
import org.springframework.stereotype.Component;

import static com.example.sortener.Constants.AppConstants.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.RandomStringUtils.random;

@Component
public class AccountAssembler {

    public Account assembleEntity(String accountId) {
        if (accountId == null) {
            return null;
        }

        var account = new Account();
        account.setAccountId(accountId);
//        account.setUrlList(null);
        account.setPassword(random(8, CHARACTERS));

        return account;

    }

    public ResponseDto assembleResponse(Account account) {
        if (isNull(account)) {
            return null;
        }
        var response = new ResponseDto();
        response.setPassword(account.getPassword());
        response.setDescription(OPENED_ACCOUNT);
        response.setSuccess(true);

        return response;
    }

    public ResponseDto assembleFailedResponse() {
        var response = new ResponseDto();
        response.setDescription(EXIST_ACCOUNT);
        response.setSuccess(false);

        return response;
    }

}
