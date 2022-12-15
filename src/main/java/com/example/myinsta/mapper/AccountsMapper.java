package com.example.myinsta.mapper;

import com.example.myinsta.dao.AccountsDao;
import org.apache.ibatis.annotations.Mapper;

//@Mapper : To be scanned by mapper-scanner of mybatis.
@Mapper
public interface AccountsMapper {
    int insertAccount(AccountsDao accountsDao);
    boolean isIdExist(AccountsDao accountsDao);

}
