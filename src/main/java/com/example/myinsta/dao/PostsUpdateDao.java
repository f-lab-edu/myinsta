package com.example.myinsta.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostsUpdateDao {
    private long idAccount;
    private long idPost;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

