package com.example.myinsta.service;

import com.example.myinsta.dao.AccountsDao;
import com.example.myinsta.dto.LoginDto;
import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.mapper.JwtMapper;
import com.example.myinsta.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    JwtService jwtService;
    @Mock
    private JwtMapper jwtMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    RequestSignUpDto requestSignUpDto;
    LoginDto loginDto;
    AccountsDao.AccountDetailsDao accountDetailsDao;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenProvider jwtTokenProvider;


    @BeforeEach
    void setUp() {
        requestSignUpDto = RequestSignUpDto.builder().email("ddd@correct.mail").nickName("nickunamu").password("passw@#2").build();
        loginDto = LoginDto.builder().email("ddd@correct.mail").password("passw@#2").build();
    }

    @Test
    @DisplayName("signUpAccount fail")
    void sign_up_throw_exception_when_insertion_fails() {
        //given
        given(jwtMapper.signUpAccount(any(AccountsDao.AccountDetailsDao.class))).willReturn(0);
        //when
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> jwtService.signUp(requestSignUpDto));
        //then
        assertEquals("checkout sign up method\n" +
                "AccountsDao.AccountDetailsDao(idAccount=null, email=ddd@correct.mail, nickName=nickunamu, password=null, createdDate=null, updatedDate=null, role=null)",thrown.getMessage());
        then(jwtMapper).should(atLeastOnce()).signUpAccount(any(AccountsDao.AccountDetailsDao.class));
    }
    @Test
    @DisplayName("signUpAccount success")
    void sign_up_success() {
        //given
        given(jwtMapper.signUpAccount(any(AccountsDao.AccountDetailsDao.class))).willReturn(1);
        //when
        jwtService.signUp(requestSignUpDto);
        //then
        then(jwtMapper).should(atLeastOnce()).signUpAccount(any(AccountsDao.AccountDetailsDao.class));
    }
    @Test
    @DisplayName("login fails ")
    void login_fails() {
        loginDto = LoginDto.builder().email("ddd@correct.mail").password("pdididiw@#2").build();
        accountDetailsDao = AccountsDao.AccountDetailsDao.builder().email("ddd@correct.mail").nickName("nickunamu").password("passw@#2").build();
        //given
        given(jwtMapper.findByEmail(any())).willReturn(accountDetailsDao);
        given(bCryptPasswordEncoder.matches(loginDto.getPassword(), accountDetailsDao.getPassword())).willReturn(false);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> jwtService.login(loginDto));
        //then
        assertEquals("Cannot find account",thrown.getErrorCode().getMessage());
        then(jwtMapper).should(atLeastOnce()).findByEmail(any());
        then(bCryptPasswordEncoder).should(atLeastOnce()).matches(any(), any());
    }
    @Test
    @DisplayName("login success")
    void login_success() {
        loginDto = LoginDto.builder().email("ddd@correct.mail").password("pdididiw@#2").build();
        accountDetailsDao = AccountsDao.AccountDetailsDao.builder().email("ddd@correct.mail").nickName("nickunamu").password("pdididiw@#2").build();
        //given
        given(jwtMapper.findByEmail(loginDto.getEmail())).willReturn(accountDetailsDao);
        given(bCryptPasswordEncoder.matches(loginDto.getPassword(), accountDetailsDao.getPassword())).willReturn(true);
        //when
        jwtService.login(loginDto);
        //then
        then(jwtMapper).should(atLeastOnce()).findByEmail(any());
        then(bCryptPasswordEncoder).should(atLeastOnce()).matches(any(), any());
    }
}