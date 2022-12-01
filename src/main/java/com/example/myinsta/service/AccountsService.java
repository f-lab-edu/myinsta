package com.example.myinsta.service;

import com.example.myinsta.dao.AccountsDao;
import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.exception.IdExistException;
import com.example.myinsta.exception.InsertFailException;
import com.example.myinsta.mapper.AccountsMapper;
import com.example.myinsta.utill.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

/**
 * AccountsService
 * Service object to deal with Accounts related service
 *
 * @Service : To add as service bean
 * <p>
 * accountsMapper
 * Injected mapper object that executes CRUD SQL query to DB
 */

/**
 * accountsMapper
 * Injected mapper object that executes CRUD SQL query to DB
 */

/**
 * signUp
 * Service method that insert a user into DB with mapper
 *
 * @return ID # of row if the insertion query success
 */

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountsService {

    private final AccountsMapper accountsMapper;

    public void signUp(SignUpDto signUpDto) {
        AccountsDao accountsDao = AccountsDao.builder()
                .email(signUpDto.getEmail())
                .nickName(signUpDto.getNickName())
                .password(SHA256.encrypt(signUpDto.getPassword()))
                .build();
        if (accountsMapper.isIdExist(accountsDao) == true) {
            throw new IdExistException("701");
        } else {
            if (accountsMapper.insertAccount(accountsDao) != 1) {
                throw new InsertFailException("702");
            }
        }
    }
}

