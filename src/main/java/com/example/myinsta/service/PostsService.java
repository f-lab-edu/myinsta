package com.example.myinsta.service;

import com.example.myinsta.dao.PostImageDao;
import com.example.myinsta.dao.PostsDao;
import com.example.myinsta.dto.PostCreateDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.mapper.PostsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostsService {
    private final PostsMapper postsMapper;
    public void postCreation(PostCreateDto postCreateDto) {
        PostsDao postsDao = PostsDao.builder()
                .title(postCreateDto.getTitle())
                .idAccount(postCreateDto.getUserId())
                .build();

        int result = postsMapper.insertPost(postsDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_INSERT_POST);
        }

        PostImageDao postImageDao = PostImageDao.builder()
                .idPost(postsDao.getIdPost())
                .imagePath(postCreateDto.getImageUrl())
                .build();
        result = postsMapper.insertPostImage(postImageDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_INSERT_POST);
        }
    }
}
