package com.example.myinsta.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
public class PostCreateDto {
    @NotBlank(message = "Title should not null or empty")
    @Size(max=50, message = "Title should not over 50 letters")
    private String title;
    @NotBlank(message = "Image url cannot be null or empty")
    private String imageUrl;
    @Min(value = 1, message = "User Id must greater than or equal to 1")
    private long userId;
}
