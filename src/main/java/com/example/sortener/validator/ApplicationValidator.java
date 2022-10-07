package com.example.sortener.validator;

import com.example.sortener.entity.Account;
import com.example.sortener.exceptions.RecordFoundException;
import com.example.sortener.repository.AccountRepository;
import lombok.Data;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Data
@Component
public class ApplicationValidator {

    private final AccountRepository accountRepository;
    public String accountId;

    public ApplicationValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void authenticate(HttpServletRequest request) {
        String upd = request.getHeader("authorization");
        String credentials = new String(Base64.decodeBase64(upd.substring(6)));

        String userName = credentials.split(":")[0];
        String password = credentials.split(":")[1];
        Account byAccountId = accountRepository.findByAccountId(userName);
        accountId = userName;
        if (!byAccountId.getPassword().equals(password)) {
            throw new RuntimeException("Bad credentials");
        }
    }

    public void validateUrl(String url) {
        UrlValidator validator = new UrlValidator();
        if (!validator.isValid(url)) {
            throw new RecordFoundException("Not valid URL");
        }
    }

}
