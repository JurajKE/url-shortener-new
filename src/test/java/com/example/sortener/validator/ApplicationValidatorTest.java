package com.example.sortener.validator;

import com.example.sortener.dto.account.RequestAccountDto;
import com.example.sortener.exceptions.RecordFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.sortener.constants.TestConstants.ACCOUNT_ID;
import static com.example.sortener.constants.TestConstants.URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationValidatorTest {

    private RequestAccountDto requestAccountDto;

    @InjectMocks
    private ApplicationValidator validator;

    @Before
    public void init() {
        requestAccountDto = new RequestAccountDto();
        requestAccountDto.setAccountId(ACCOUNT_ID);
    }

    @Test
    public void validateUrl_shouldValidateUrlLink_whenCalled() {
        validator.validateUrl(URL);
    }

    @Test
    public void validateUrl_shouldThrownException_whenUrlIsNotValid() {

        RecordFoundException exception = assertThrows(RecordFoundException.class, () -> validator.validateUrl(null));
        assertEquals("Not valid URL", exception.getMessage());
    }

    @Test
    public void validateAccount_shouldValidateAccountId_whenCalled() {
        validator.validateAccount(requestAccountDto);
    }

    @Test
    public void validateAccount_shouldThrownException_whenAccountIdIsNotValid() {

        requestAccountDto.setAccountId(null);
        RecordFoundException exception = assertThrows(RecordFoundException.class, () -> validator.validateAccount(requestAccountDto));
        assertEquals("Not valid account ID", exception.getMessage());
    }

}
