package com.example.myinsta.service;

import com.example.myinsta.dao.PostImageDao;
import com.example.myinsta.dao.PostImagesUpdateDao;
import com.example.myinsta.dao.PostsDao;
import com.example.myinsta.dao.PostsUpdateDao;
import com.example.myinsta.dto.GetSinglePostDto;
import com.example.myinsta.dto.PostCreateDto;
import com.example.myinsta.dto.PostDeleteDto;
import com.example.myinsta.dto.PostUpdateDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.mapper.PostsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class PostsServiceTest {
    @Mock
    PostsMapper postsMapper;
    @InjectMocks
    PostsService postsService;
    PostCreateDto postCreateDto;
    PostUpdateDto postUpdateDto;
    PostDeleteDto postDeleteDto;
    @BeforeEach
    void setUp() {
        postCreateDto = PostCreateDto.builder().title("This is post title").imageUrl("/this/is/some/url").build();
        postUpdateDto = PostUpdateDto.builder().title("This is post update title").imageUrl("/THis/Is/Update/Path").build();
        postDeleteDto = PostDeleteDto.builder().idAccount(1L).build();
    }
    @Test
    @DisplayName("insertPost() success insertPostImage() never happen then throw exception")
    void postCreation_fail_with_exception() {
        //given
        given(postsMapper.insertPost(any(PostsDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postCreation(postCreateDto));
        //then
        assertEquals("Post creation failed",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).insertPost(any(PostsDao.class));
    }
    @Test
    @DisplayName("insertPost() success insertPostIamge() fail then throw exception")
    void postCreation_fail_with_exception2() {
        //given
        given(postsMapper.insertPost(any(PostsDao.class))).willReturn(1);
        given(postsMapper.insertPostImage(any(PostImageDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postCreation(postCreateDto));
        //then
        assertEquals("Post image creation failed",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).insertPostImage(any(PostImageDao.class));
    }
    @Test
    @DisplayName("insertPost() success insertPostIamge() success then no exception")
    void postCreation_success_without_exception() {
        //given
        given(postsMapper.insertPost(any(PostsDao.class))).willReturn(1);
        given(postsMapper.insertPostImage(any(PostImageDao.class))).willReturn(1);
        //when
        postsService.postCreation(postCreateDto);
        //then
        then(postsMapper).should(atLeastOnce()).insertPost(any(PostsDao.class));
        then(postsMapper).should(atLeastOnce()).insertPostImage(any(PostImageDao.class));
    }
    @Test
    @DisplayName("isPostExist() fail isOwner() not happen updatePost() not happen updatePostImage() not happen then throw exception")
    void postUpdate_fail_with_exception1() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(false);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postUpdate(postUpdateDto, 1L));
        //then
        assertEquals("Post cannot be found",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
    }
    @Test
    @DisplayName("isPostExist() success isOwner() fail updatePost() not happen updatePostImage() not happen then throw exception")
    void postUpdate_fail_with_exception2() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(true);
        given(postsMapper.isOwner(any())).willReturn(false);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postUpdate(postUpdateDto, 1L));
        //then
        assertEquals("Only post owner can modify post",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
        then(postsMapper).should(atLeastOnce()).isOwner(any());
    }
    @Test
    @DisplayName("isPostExist() success isOwner() success updatePost() fail updatePostImage() not happen then throw exception")
    void postUpdate_fail_with_exception3() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(true);
        given(postsMapper.isOwner(any())).willReturn(true);
        given(postsMapper.updatePost(any(PostsUpdateDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postUpdate(postUpdateDto, 1L));
        //then
        assertEquals("Post update failed",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
        then(postsMapper).should(atLeastOnce()).isOwner(any());
        then(postsMapper).should(atLeastOnce()).updatePost(any(PostsUpdateDao.class));
    }
    @Test
    @DisplayName("isPostExist() success isOwner() success updatePost() success updatePostImage() fail then throw exception")
    void postUpdate_fail_with_exception4() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(true);
        given(postsMapper.isOwner(any())).willReturn(true);
        given(postsMapper.updatePost(any(PostsUpdateDao.class))).willReturn(1);
        given(postsMapper.updatePostImage(any(PostImagesUpdateDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postUpdate(postUpdateDto, 1L));
        //then
        assertEquals("Post image update failed",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
        then(postsMapper).should(atLeastOnce()).isOwner(any());
        then(postsMapper).should(atLeastOnce()).updatePost(any(PostsUpdateDao.class));
        then(postsMapper).should(atLeastOnce()).updatePostImage(any(PostImagesUpdateDao.class));
    }
    @Test
    @DisplayName("isPostExist() success isOwner() success updatePost() success updatePostImage() success without exception")
    void postUpdate_success_without_exception() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(true);
        given(postsMapper.isOwner(any())).willReturn(true);
        given(postsMapper.updatePost(any(PostsUpdateDao.class))).willReturn(1);
        given(postsMapper.updatePostImage(any(PostImagesUpdateDao.class))).willReturn(1);
        //when
        postsService.postUpdate(postUpdateDto, 1L);
        //then
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
        then(postsMapper).should(atLeastOnce()).isOwner(any());
        then(postsMapper).should(atLeastOnce()).updatePost(any(PostsUpdateDao.class));
        then(postsMapper).should(atLeastOnce()).updatePostImage(any(PostImagesUpdateDao.class));
    }
    @Test
    @DisplayName("isPostExist() fail isOwner() not happen deletePost() not happen then throw exception")
    void postDelete_fail_with_exception() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(false);

        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postDelete(1L, postDeleteDto));
        //then
        assertEquals("Post deletion failed post cannot be found",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
    }
    @Test
    @DisplayName("isPostExist() success isOwner() fail deletePost() not happen then throw exception")
    void postDelete_fail_with_exception2() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(true);
        given(postsMapper.isOwner(any())).willReturn(false);

        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postDelete(1L, postDeleteDto));
        //then
        assertEquals("Only post owner can delete post",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
        then(postsMapper).should(atLeastOnce()).isOwner(any());
    }
    @Test
    @DisplayName("isPostExist() success isOwner() success deletePost() fail then throw exception")
    void postDelete_fail_with_exception3() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(true);
        given(postsMapper.isOwner(any())).willReturn(true);
        given(postsMapper.deletePost(any())).willReturn(0);

        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postDelete(1L, postDeleteDto));
        //then
        assertEquals("Post deletion failed",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
        then(postsMapper).should(atLeastOnce()).isOwner(any());
        then(postsMapper).should(atLeastOnce()).deletePost(any());
    }
    @Test
    @DisplayName("isPostExist() success isOwner() success deletePost() success without exception")
    void postDelete_success_without_exception3() {
        //given
        given(postsMapper.isPostExist(any())).willReturn(true);
        given(postsMapper.isOwner(any())).willReturn(true);
        given(postsMapper.deletePost(any())).willReturn(1);

        //when
        postsService.postDelete(1L, postDeleteDto);
        //then
        then(postsMapper).should(atLeastOnce()).isPostExist(any());
        then(postsMapper).should(atLeastOnce()).isOwner(any());
        then(postsMapper).should(atLeastOnce()).deletePost(any());
    }
    @Test
    @DisplayName("selectSinglePost() success without exception")
    void getSinglePost_success_without_exception() {
        //given
        GetSinglePostDto getSinglePostResponseDto = GetSinglePostDto.builder()
                .title("title")
                .imagePath("image/path")
                .idPost(8L)
                .build();
        given(postsMapper.selectSinglePost(any())).willReturn(getSinglePostResponseDto);
        //when
        postsService.getSinglePost(8L);
        //then
        then(postsMapper).should(atLeastOnce()).selectSinglePost(any());
    }
    @Test
    @DisplayName("selectSinglePost() return null throw exception")
    void getSinglePost_fail_with_exception() {
        //given
        given(postsMapper.selectSinglePost(any())).willReturn(null);
        //when

        CustomException thrown = assertThrows(CustomException.class, () -> postsService.getSinglePost(8L));
        //then
        assertEquals("Cannot find post",thrown.getErrorCode().getMessage());
        //then
        then(postsMapper).should(atLeastOnce()).selectSinglePost(any());
    }
}