package com.example.myinsta.mapper;

import com.example.myinsta.dao.PostImageDao;
import com.example.myinsta.dao.PostImagesUpdateDao;
import com.example.myinsta.dao.PostsDao;
import com.example.myinsta.dao.PostsUpdateDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsMapper {
    int insertPost(PostsDao postsDao);
    int insertPostImage(PostImageDao postImageDao);
    int updatePost(PostsUpdateDao postsUpdateDao);
    int updatePostImage(PostImagesUpdateDao postImagesUpdateDao);
}
