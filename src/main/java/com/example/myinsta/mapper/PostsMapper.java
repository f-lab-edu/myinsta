package com.example.myinsta.mapper;

import com.example.myinsta.dao.*;
import com.example.myinsta.dto.GetSinglePostDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsMapper {
    int insertPost(PostsDao postsDao);
    int insertPostImage(PostImageDao postImageDao);
    int updatePost(PostsUpdateDao postsUpdateDao);
    int updatePostImage(PostImagesUpdateDao postImagesUpdateDao);
    boolean isPostExist(Long idPost);
    boolean isOwner(Long idAccount);
    int deletePost(Long idPost);
    GetSinglePostDto selectSinglePost(PostDto getSinglePostDao);
}
