package com.example.myinsta.dto;

import lombok.*;

import javax.validation.constraints.*;

/**
 * SignUpDto
 * DTO for taking care of sign up information from user side
 *
 * @Getter : DTO has simple Getter and Setter
 * @Setter : DTO has simple Getter and Setter
 * @Builder : For testing purpose, to build test object
 * email
 * data field to save email information of user sign up input
 * @Email, string has to be well-formed email address, null is consisdered as valid
 * @NotNull, email shouldn't be null
 * Used Regular expression : https://velog.io/@fall031/%EC%A0%95%EA%B7%9C%ED%91%9C%ED%98%84%EC%8B%9D
 * nickName
 * Data field to save nickname information of user sign up input
 * @NotBlank, validate whether element is not null and has at least one character
 * @Size, validate the size of element, nick_name shuold be fit in 1 <= nick_name <= 16
 * password
 * Data field to save information of password of user sign up input
 * @NotBlank, validate whether the element is not null and has at least one character
 * @Pattern, validate whether the element is following given regular expression rule
 * Used regular expression : https://velog.io/@abiria/%EC%A0%95%EA%B7%9C%ED%91%9C%ED%98%84%EC%8B%9D%EB%A7%8C%EC%9C%BC%EB%A1%9C-%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EA%B2%80%EC%A6%9D%ED%95%98%EA%B8%B0
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
