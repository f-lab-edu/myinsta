package com.example.myinsta;

import com.example.myinsta.dao.PostImageDao;
import com.example.myinsta.dao.PostsDao;
import com.example.myinsta.dto.PostCreateDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.mapper.PostsMapper;
import com.example.myinsta.service.PostsService;
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
    @BeforeEach
    void setUp() {
        postCreateDto = PostCreateDto.builder().title("This is post title").imageUrl("/this/is/some/url").build();
    }
    @Test
    @DisplayName("insertPost success and insertPostImage never happen and throw exception")
    void postCreation_insertion_fails_yes_exception() {
        //given
        given(postsMapper.insertPost(any(PostsDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postCreation(postCreateDto));
        //then
        assertEquals("Post creation failed",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).insertPost(any(PostsDao.class));
    }
    @Test
    @DisplayName("insertPost success and insertPostIamge fail and yes exception")
    void postCreation_insertion_fails_yes_exception2() {
        //given
        given(postsMapper.insertPost(any(PostsDao.class))).willReturn(1);
        given(postsMapper.insertPostImage(any(PostImageDao.class))).willReturn(0);
        //when
        CustomException thrown = assertThrows(CustomException.class, () -> postsService.postCreation(postCreateDto));
        //then
        assertEquals("Post creation failed",thrown.getErrorCode().getMessage());
        then(postsMapper).should(atLeastOnce()).insertPostImage(any(PostImageDao.class));
    }
    @Test
    @DisplayName("insertPost success and insertPostIamge success and no exception")
    void postCreation_insertion_success_no_exception() {
        //given
        given(postsMapper.insertPost(any(PostsDao.class))).willReturn(1);
        given(postsMapper.insertPostImage(any(PostImageDao.class))).willReturn(1);
        //when
        postsService.postCreation(postCreateDto);
        //then
        then(postsMapper).should(atLeastOnce()).insertPost(any(PostsDao.class));
        then(postsMapper).should(atLeastOnce()).insertPostImage(any(PostImageDao.class));
    }

}