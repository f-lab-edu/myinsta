package com.example.myinsta.mapper;

import com.example.myinsta.dao.AccountsDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JwtMapper {
    int signUpAccount(AccountsDao.AccountDetailsDao accountDetailsDao);
    void signUpRole(AccountsDao.RoleDao roleDao);
    void addRole(AccountsDao.RoleDao roleDao);
    AccountsDao.AccountDetailsDao findByEmailPassword(String email, String password);
    AccountsDao.AccountDetailsDao findByEmail(String email);
    boolean isIdExist(AccountsDao.AccountDetailsDao accountDetailsDao);
}
