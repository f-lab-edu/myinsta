package com.example.myinsta.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetSinglePostDto {
    private Long idPost;
    private String title;
    private String imagePath;
}
