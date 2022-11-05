package com.example.sortener.assembler;

import com.example.sortener.dto.ResponseDto;
import com.example.sortener.entity.Account;
import org.springframework.stereotype.Component;

import static com.example.sortener.Constants.AppConstants.EXIST_ACCOUNT;
import static com.example.sortener.Constants.AppConstants.OPENED_ACCOUNT;
import static java.util.Objects.isNull;

@Component
public class AccountAssembler {

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
