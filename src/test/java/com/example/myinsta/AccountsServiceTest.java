package com.example.myinsta;


import com.example.myinsta.dao.AccountsDao;
import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.mapper.AccountsMapper;
import com.example.myinsta.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 * @Captor Captor verifies passed argument by using equals()
 * That is if the instance of ArgumentCaptor<T> declared. it compares passed arguement with with T.
 * In this way Verification can be ensured to get correct type of argument.
 * Mockito recommends not to use ArgumentCaptor for Stubbing since ArgumentMatcher can be used for ensuring passed argument type.
 * @Any( someOnject.class )
 * This method accept all kinds of types in java.
 * However, if we pass some object as argument any() will only accept
 * passed object type.
 * @BeforeEach
 * JUnit5 annotation to define method that should excuce before each test method
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountsServiceTest {

    @MockBean
    private AccountsMapper accountsMapper;

    @Captor
    private ArgumentCaptor<AccountsDao> accountsDaoArgumentCaptor;

    AccountsService accountsService;
    SignUpDto signUpDto;

    @BeforeEach
    void setUp() {
        signUpDto = SignUpDto.builder().email("ddd@correct.mail").nickName("nickunamu").password("passw@#2").build();
    }

    @Test
    @DisplayName("Sign-up should throw exception when insert query fails, and isIdExist should be called at least once since isIdExist called before insertion")
    void sign_up_throw_exception_when_insertion_fails() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(false);
        given(accountsMapper.insertAccount(any(AccountsDao.class))).willReturn(0);
        //when
        assertThrows(CustomException.class, () -> accountsService.signUp(signUpDto));
        //then
        then(accountsMapper.isIdExist(accountsDaoArgumentCaptor.capture())).should(atLeastOnce());
        then(accountsMapper.insertAccount(accountsDaoArgumentCaptor.capture())).should(atLeastOnce());
    }

    @Test
    @DisplayName("Sign-up should not throw exception when insert query success, and isIdExist should be called at least once since isIdExist called before insertion")
    void sign_up_not_throw_exception_when_insertion_fails() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(false);
        given(accountsMapper.insertAccount(any(AccountsDao.class))).willReturn(0);
        //when
        assertDoesNotThrow(() -> accountsService.signUp(signUpDto));
        //then
        then(accountsMapper.isIdExist(accountsDaoArgumentCaptor.capture())).should(atLeastOnce());
        then(accountsMapper.insertAccount(accountsDaoArgumentCaptor.capture())).should(atLeastOnce());
    }

    @Test
    @DisplayName("Sign-up should throw exception when isIdExist query found duplicate, and insertAccount never happens since methods throws exception")
    void sign_up_throw_exception_when_id_already_exist() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(true);
        //when
        assertThrows(CustomException.class, () -> accountsService.signUp(signUpDto));
        //then
        then(accountsMapper.isIdExist(accountsDaoArgumentCaptor.capture())).should(atLeastOnce());
        then(accountsMapper.insertAccount(accountsDaoArgumentCaptor.capture())).should(times(0));
    }

    @Test

    @DisplayName("Sign-up should not throw exception when isIdExist query does not find duplicate, and insertAccount should happens at least once, this is so called successful sign-up")
    void sign_up_not_throw_exception_when_id_not_exist() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(false);
        //when
        assertDoesNotThrow(() -> accountsService.signUp(signUpDto));
        //then
        then(accountsMapper.isIdExist(accountsDaoArgumentCaptor.capture())).should(atLeastOnce());
        then(accountsMapper.insertAccount(accountsDaoArgumentCaptor.capture())).should(atLeastOnce());
    }
}
