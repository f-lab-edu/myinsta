package com.example.myinsta.service;

import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.mapper.AccountsMapper;
import com.example.myinsta.utill.SHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//@Service : To add as service bean
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountsService {
    //Inject AccountsMapper to query to DB
    private final AccountsMapper accountsMapper;
    //insert accounts information into DB.accounts table using mapper.
    public int signUp(SignUpDto signUpDto){

        //Show original DTO
        log.debug( "email : {} \n nick_name : {} \n password : {}" ,signUpDto.getEmail() , signUpDto.getNick_name() , signUpDto.getPassword() );
        //Show SHA256 result
        log.debug("hashed password is : {}", SHA256.encrypt( signUpDto.getPassword() ) );
        //Put hashing result into hashedpswd
        String hashedpswd = SHA256.encrypt( signUpDto.getPassword() );
        //Put hashedpswd into signupDTO
        signUpDto.setPassword(hashedpswd);
        //Show the result of updated DTO
        log.debug("After hashing password DTO has : email : {}, nick_name : {}, password : {} ",signUpDto.getEmail() , signUpDto.getNick_name() , signUpDto.getPassword() );
        //Insert the reult into DB
        return accountsMapper.insertAccount(signUpDto);
    }
}
