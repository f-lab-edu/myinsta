package com.example.myinsta.mapper;

import com.example.myinsta.dao.*;
import com.example.myinsta.dto.ResponsePostDto;
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
    ResponsePostDto selectSinglePost(GetSinglePostDao getSinglePostDao);
    int getTotalNumberOfPosts();
    List<ResponsePostDto> selectPostPage(PostPageSelectDao postPageSelectDao);

}
