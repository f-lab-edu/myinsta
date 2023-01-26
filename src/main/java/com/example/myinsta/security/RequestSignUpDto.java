package com.example.myinsta.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSignUpDto {
    @Email(message = "Invalid Email format", regexp = ("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"))
    @NotNull
    private String email;
    @NotNull(message = "NickName must not null")
    @Size(message = "NickName should fit in range of 1 to 16", min = 1, max = 16)
    private String nickName;
    @NotNull(message = "Passwords must not null")
    @Pattern(regexp = ("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#])[\\da-zA-Z!@#]{8,}$"), message = "Password must have at least 8 characters with maximum 16 characters, one Upper case, one number, one symbol.")
    private String password;
    @NotBlank(message = "choose one among User, Admin")
    private String role;
}
