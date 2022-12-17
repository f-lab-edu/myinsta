package com.example.myinsta.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDeleteDto {
    @Min(value = 1, message = "Account Id must greater than or equal to 1")
    private long idAccount;
}
