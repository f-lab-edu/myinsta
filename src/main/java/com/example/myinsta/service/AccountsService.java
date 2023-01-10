package com.example.myinsta.service;

import com.example.myinsta.dao.AccountsDao;
import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.mapper.AccountsMapper;
import com.example.myinsta.utill.SHA256;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccountsService
 * Service object to deal with Accounts related service
 *
 * @Service : To add as service bean
 * <p>
 * accountsMapper
 * Injected mapper object that executes CRUD SQL query to DB
 * <p>
 * accountsMapper
 * Injected mapper object that executes CRUD SQL query to DB
 * <p>
 * accountsMapper
 * Injected mapper object that executes CRUD SQL query to DB
 * <p>
 * accountsMapper
 * Injected mapper object that executes CRUD SQL query to DB
 * <p>
 * signUp
 * Service method that insert a user into DB with mapper
 * @return ID # of row if the insertion query success
 */

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsMapper accountsMapper;
    public void signUp(RequestSignUpDto requestSignUpDto) {
        AccountsDao accountsDao = AccountsDao.builder()
                .email(requestSignUpDto.getEmail())
                .nickName(requestSignUpDto.getNickName())
                .password(SHA256.encrypt(requestSignUpDto.getPassword()))
                .build();
        if (accountsMapper.isIdExist(accountsDao)) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL);
        }
        int result = accountsMapper.insertAccount(accountsDao);
        if (result != 1) {
            throw new CustomException(ErrorCode.FAILED_TO_INSERT);
        }
    }
}

