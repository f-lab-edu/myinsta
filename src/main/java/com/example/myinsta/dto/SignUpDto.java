package com.example.myinsta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignUpDto {
    @Email(message = "invalid Email format", regexp = ( "(.){0,30}@(.){0,20}" ) )
    @NotNull
    private String email;
    @NotBlank
    private String nick_name;
    @NotBlank
    private String password;
}
