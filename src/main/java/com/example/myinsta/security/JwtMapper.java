package com.example.myinsta.security;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JwtMapper {
    void signUpAccount(AccountsDao.AccountDetailsDao accountDetailsDao);
    void signUpRole(AccountsDao.RoleDao roleDao);
    void addRole(AccountsDao.RoleDao roleDao);
    AccountsDao.AccountDetailsDao findByEmailPassword(String email, String password);
    AccountsDao.AccountDetailsDao findByEmail(String email);

}
