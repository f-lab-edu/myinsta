package com.example.myinsta.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
public class AccountsDao {
    private long idAccount;
    private String email;
    private String nickName;
    private String password;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
