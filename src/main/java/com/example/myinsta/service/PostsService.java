package com.example.myinsta.service;

import com.example.myinsta.dao.*;
import com.example.myinsta.dto.GetSinglePostResponseDto;
import com.example.myinsta.dto.PostCreateDto;
import com.example.myinsta.dto.PostUpdateDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.mapper.PostsMapper;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    private final PostsMapper postsMapper;
    public void postCreation(PostCreateDto postCreateDto) {
        PostsDao postsDao = PostsDao.builder()
                .title(postCreateDto.getTitle())
                .idAccount(postCreateDto.getUserId())
                .build();

        int result = postsMapper.insertPost(postsDao);
        if(result < 1){
            throw new CustomException(ErrorCode.FAILED_TO_INSERT_POST);
        }

        PostImageDao postImageDao = PostImageDao.builder()
                .idPost(postsDao.getIdPost())
                .imagePath(postCreateDto.getImageUrl())
                .build();
        result = postsMapper.insertPostImage(postImageDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_INSERT_POST_IMAGE);
        }
    }
    public void postUpdate(PostUpdateDto postUpdateDto, Long postId){
        PostsUpdateDao postsUpdateDao = PostsUpdateDao.builder()
                .idPost(postId)
                .idAccount(postUpdateDto.getUserId())
                .title(postUpdateDto.getTitle())
                .build();

        if(!postsMapper.isPostExist(postsUpdateDao)){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST_NOT_FOUND);
        }
        if(!postsMapper.isOwner(postsUpdateDao)){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST_NOT_OWNER);
        }
        int result = postsMapper.updatePost(postsUpdateDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST);
        }

        PostImagesUpdateDao postImagesUpdateDao = PostImagesUpdateDao.builder()
                .idPost(postId)
                .imagePath(postUpdateDto.getImageUrl())
                .build();

        result = postsMapper.updatePostImage(postImagesUpdateDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST_IMAGE);
        }
    }

    public GetSinglePostResponseDto getSinglePost(Long postId){
        GetSinglePostDao getSinglePostDao = GetSinglePostDao.builder().idPost(postId).build();
        GetSinglePostResponseDto getSinglePostResponseDto = postsMapper.selectSinglePost( getSinglePostDao );
        if(getSinglePostResponseDto == null){
            throw new CustomException(ErrorCode.FAILED_TO_GET_SINGLE_POST);
        }
        return getSinglePostResponseDto;
    }
}
