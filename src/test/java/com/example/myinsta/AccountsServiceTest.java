package com.example.myinsta;


import com.example.myinsta.dao.AccountsDao;
import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.mapper.AccountsMapper;
import com.example.myinsta.service.AccountsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AccountsServiceTest {
    @Mock
    private AccountsMapper accountsMapper;

    AccountsService accountsService;

    @Test
    void accountsServiceTest(){
        SignUpDto signUpDto = SignUpDto.builder()
                        .email("ddd@correct.mail")
                                .nickName("nickunamu")
                                        .password("passw@#2")
                                                .build();
        AccountsDao accountsDao = AccountsDao.builder()
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .nickName(signUpDto.getNickName())
                .build();
        when(accountsMapper.insertAccount( accountsDao )).thenReturn(1);
        accountsService = new AccountsService( accountsMapper );
//        assertEquals( accountsService.signUp( signUpDto ), 1);
        verify(accountsMapper).insertAccount(accountsDao);
    }
}
