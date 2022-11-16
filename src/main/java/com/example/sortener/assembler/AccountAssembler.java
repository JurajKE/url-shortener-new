package com.example.sortener.assembler;

import com.example.sortener.dto.account.ResponseAccountDto;
import com.example.sortener.entity.Account;
import org.springframework.stereotype.Component;

import static com.example.sortener.constants.AppConstants.EXIST_ACCOUNT;
import static com.example.sortener.constants.AppConstants.OPENED_ACCOUNT;
import static java.util.Objects.isNull;

@Component
public class AccountAssembler {

    public ResponseAccountDto assembleResponse(Account account) {
        if (isNull(account)) {
            return null;
        }

        var response = new ResponseAccountDto();
        response.setPassword(account.getPassword());
        response.setDescription(OPENED_ACCOUNT);
        response.setSuccess(true);

        return response;
    }

    public ResponseAccountDto assembleFailedResponse() {
        var response = new ResponseAccountDto();
        response.setDescription(EXIST_ACCOUNT);
        response.setSuccess(false);

        return response;
    }

}
