package com.example.myinsta.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostImageDao {
    private long idPost;
    private String imagePath;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
