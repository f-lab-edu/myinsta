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
public class PostUpdateDto {
    @NotBlank(message = "Title should not null or empty")
    @Size(max=50, message = "Title should not over 50 letters")
    private String title;
    @NotBlank(message = "Image url cannot be null or empty")
    private String imageUrl;
}
