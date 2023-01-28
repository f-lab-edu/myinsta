package com.example.myinsta.security;

import com.example.myinsta.exception.CustomException;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.mapper.JwtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {
    private final JwtMapper jwtMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    public void signUp(RequestSignUpDto requestSignUpDto) {
        AccountsDao.AccountDetailsDao accountDetailsDao = AccountsDao.AccountDetailsDao.builder()
            .email(requestSignUpDto.getEmail())
            .password(bCryptPasswordEncoder.encode(requestSignUpDto.getPassword()))
            .nickName(requestSignUpDto.getNickName())
            .build();
//        jwtMapper.signUpAccount(accountDetailsDao);
        int signUpCount = jwtMapper.signUpAccount(accountDetailsDao);

        if(signUpCount != 1){
            throw new RuntimeException("checkout sign up method\n" + accountDetailsDao);
        }

        AccountsDao.RoleDao memberRole = AccountsDao.RoleDao.builder()
                .idAccount(accountDetailsDao.getIdAccount())
                .role(requestSignUpDto.getRole())
                .build();

        if(Objects.equals(requestSignUpDto.getRole(), "ADMIN")) {
            jwtMapper.signUpRole(memberRole);
            memberRole.setRole("USER");
            jwtMapper.addRole(memberRole);
        }
        else jwtMapper.signUpRole(memberRole);
    }
    public SignInResponseDto login(LoginDto loginDto){
        AccountsDao.AccountDetailsDao memberInfo = jwtMapper.findByEmail(loginDto.getEmail());

        if(!bCryptPasswordEncoder.matches(loginDto.getPassword(), memberInfo.getPassword())){
            throw new CustomException(ErrorCode.NOT_FOUND_ACCOUNT);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        return jwtTokenProvider.generateToken(authentication);
    }
}
