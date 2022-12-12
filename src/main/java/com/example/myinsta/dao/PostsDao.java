package com.example.myinsta.dao;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Builder
public class PostsDao {
    private long idPost;
    private long idAccount;
    private String title;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
