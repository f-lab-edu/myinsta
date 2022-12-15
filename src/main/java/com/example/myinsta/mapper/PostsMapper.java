package com.example.myinsta.mapper;

import com.example.myinsta.dao.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsMapper {
    int insertPost(PostsDao postsDao);
    int insertPostImage(PostImageDao postImageDao);
    int updatePost(PostsUpdateDao postsUpdateDao);
    int updatePostImage(PostImagesUpdateDao postImagesUpdateDao);
    boolean isPostExist(PostsUpdateDao postsUpdateDao);

    boolean isOwner(PostsUpdateDao postsUpdateDao);
}
