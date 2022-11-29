package com.example.myinsta.service;

import com.example.myinsta.dao.AccountsDao;
import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.mapper.AccountsMapper;
import com.example.myinsta.utill.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * AccountsService
 * Service object to deal with Accounts related service
 * @Service : To add as service bean
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
public class AccountsService {
    private final AccountsMapper accountsMapper;

    public int signUp(SignUpDto signUpDto){
        AccountsDao accountsDao = AccountsDao.builder()
                .email(signUpDto.getEmail())
                .nickName(signUpDto.getNickName())
                .password( SHA256.encrypt( signUpDto.getPassword()) )
                .build();
        if( accountsMapper.isIdExist(accountsDao) == true ){
            log.debug("accounts already exist");
        }
        return accountsMapper.insertAccount(accountsDao);
    }
}

