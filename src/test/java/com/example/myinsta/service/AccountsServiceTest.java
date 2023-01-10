package com.example.myinsta.service;


import com.example.myinsta.dao.AccountsDao;
import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.mapper.AccountsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
    @InjectMocks
    AccountsService accountsService;
    RequestSignUpDto requestSignUpDto;


    @BeforeEach
    void setUp() {
        requestSignUpDto = RequestSignUpDto.builder().email("ddd@correct.mail").nickName("nickunamu").password("passw@#2").build();
    }

    @Test
    @DisplayName("isIdExist does not find duplication but insertAccount fail")
    void sign_up_throw_exception_when_insertion_fails() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(false);
        given(accountsMapper.insertAccount(any(AccountsDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> accountsService.signUp(requestSignUpDto));
        //then
        assertEquals("Sign-up failed",thrown.getErrorCode().getMessage());
        then(accountsMapper).should(atLeastOnce()).isIdExist(any(AccountsDao.class));
        then(accountsMapper).should(atLeastOnce()).insertAccount(any(AccountsDao.class));
    }

    @Test
    @DisplayName("isIdExist does not find duplication and insertAccount success")
    void sign_up_not_throw_exception_when_insertion_success() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(false);
        given(accountsMapper.insertAccount(any(AccountsDao.class))).willReturn(1);
        //when
        accountsService.signUp(requestSignUpDto);
        //then
        then(accountsMapper).should(atLeastOnce()).isIdExist(any(AccountsDao.class));
        then(accountsMapper).should(atLeastOnce()).insertAccount(any(AccountsDao.class));
    }

    @Test
    @DisplayName("isIdExist query found duplicate and insertAccount will not be executed")
    void sign_up_throw_exception_when_id_already_exist() {
        //given
        given(accountsMapper.isIdExist(any(AccountsDao.class))).willReturn(true);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> accountsService.signUp(requestSignUpDto));
        //then
        assertEquals("Email is already exist", thrown.getErrorCode().getMessage());
        then(accountsMapper).should(atLeastOnce()).isIdExist(any(AccountsDao.class));
        then(accountsMapper).should(times(0)).insertAccount(any(AccountsDao.class));
    }
}