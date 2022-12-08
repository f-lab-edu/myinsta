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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
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
 *
 * @InjectMocks
 * By default Mock object returns null if there is no stub on methods.
 * When testing service layer we should explicitly inject objects with @Mock and @InjectMocks since there is no spring application around.
 * Note. that we are not using spring-autoconfiguration feature here.
 */
@ExtendWith(MockitoExtension.class)
public class AccountsServiceTest {

    @Mock
    private AccountsMapper accountsMapper;

    @Captor
    private ArgumentCaptor<AccountsDao> accountsDaoArgumentCaptor;

    @InjectMocks
    AccountsService accountsService;
    SignUpDto signUpDto;


    @BeforeEach
    void setUp() {
        signUpDto = SignUpDto.builder().email("ddd@correct.mail").nickName("nickunamu").password("passw@#2").build();
    }

    @Test
    @DisplayName("isIdExist does not find duplication but insertAccount fail")
    void sign_up_throw_exception_when_insertion_fails() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(false);
        given(accountsMapper.insertAccount(any(AccountsDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> accountsService.signUp(signUpDto));
        //then
        assertEquals("Sign-up failed",thrown.getErrorCode().getMessage());
        then(accountsMapper).should(atLeastOnce()).isIdExist(accountsDaoArgumentCaptor.capture());
        then(accountsMapper).should(atLeastOnce()).insertAccount(accountsDaoArgumentCaptor.capture());
    }

    @Test
    @DisplayName("isIdExist does not find duplication and insertAccount success")
    void sign_up_not_throw_exception_when_insertion_fails() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(false);
        given(accountsMapper.insertAccount(any(AccountsDao.class))).willReturn(1);
        //when
        accountsService.signUp(signUpDto);
        //then
        assertDoesNotThrow(() -> accountsService.signUp(signUpDto));
        then(accountsMapper).should(atLeastOnce()).isIdExist(accountsDaoArgumentCaptor.capture());
        then(accountsMapper).should(atLeastOnce()).insertAccount(accountsDaoArgumentCaptor.capture());
    }

    @Test
    @DisplayName("isIdExist query found duplicate and insertAccount will not be executed")
    void sign_up_throw_exception_when_id_already_exist() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(true);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> accountsService.signUp(signUpDto));
        //then
        assertEquals("Email is already exist", thrown.getErrorCode().getMessage());
        then(accountsMapper).should(atLeastOnce()).isIdExist(accountsDaoArgumentCaptor.capture());
        then(accountsMapper).should(times(0)).insertAccount(accountsDaoArgumentCaptor.capture());
    }
}