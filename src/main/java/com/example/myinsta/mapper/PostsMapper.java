package com.example.myinsta.mapper;

import com.example.myinsta.dao.*;
import com.example.myinsta.dto.PostDto;
import com.example.myinsta.dto.PostPageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostsMapper {
    int insertPost(PostsDao postsDao);
    int insertPostImage(PostImageDao postImageDao);
    int updatePost(PostsUpdateDao postsUpdateDao);
    int updatePostImage(PostImagesUpdateDao postImagesUpdateDao);
    boolean isPostExist(Long idPost);
    boolean isOwner(Long idAccount);
    int deletePost(Long idPost);
    PostDto selectSinglePost(GetSinglePostDao getSinglePostDao);
    Integer getTotalNumberOfPosts();
    List<PostDto> selectPostPage();

}
