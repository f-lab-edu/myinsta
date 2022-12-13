package com.example.myinsta.mapper;

import com.example.myinsta.dao.PostImageDao;
import com.example.myinsta.dao.PostsDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostsMapper {
    int insertPost(PostsDao postsDao);
    int insertPostImage(PostImageDao postImageDao);
}
