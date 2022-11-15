package com.example.myinsta.dto;

import lombok.Data;

@Data
public class AccountsSelectTestDto extends AccountsInsertTestDto{
    private long id_account;
    private java.sql.Timestamp created_date;
    private java.sql.Timestamp updated_date;
}
