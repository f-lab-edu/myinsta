package com.example.myinsta.service;

import com.example.myinsta.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service : To add as service bean
@Service
@RequiredArgsConstructor
public class AccountsService {

    public String signUp(SignUpDto signUpDto){
        //insert accounts information into DB.accounts table
        return "";
    }
}
