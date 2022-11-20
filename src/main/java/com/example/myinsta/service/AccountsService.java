package com.example.myinsta.service;

import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.mapper.AccountsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service : To add as service bean
@Service
@RequiredArgsConstructor
public class AccountsService {
    //Inject AccountsMapper to query to DB
    private final AccountsMapper accountsMapper;
    public int signUp(SignUpDto signUpDto){
        //insert accounts information into DB.accounts table
        return accountsMapper.insertAccount(signUpDto);
    }
}
