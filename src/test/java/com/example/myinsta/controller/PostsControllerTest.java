package com.example.myinsta.controller;


import com.example.myinsta.dto.PostCreateDto;
import com.example.myinsta.dto.PostDeleteDto;
import com.example.myinsta.dto.PostPageDto;
import com.example.myinsta.dto.PostUpdateDto;
import com.example.myinsta.service.PostsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostsController.class)
public class PostsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    PostsService postService;
    private PostCreateDto postCreateDto;
    private PostUpdateDto postUpdateDto;
    private PostDeleteDto postDeleteDto;
    private PostPageDto postPageDto;
    private String errorCode = "$..errorCode";;
    private String errorMessage = "$..errorMessage";;

    @Test
    @DisplayName("Empty title input")
    void invalid_title_empty_string() throws Exception {
        postCreateDto = PostCreateDto.builder()
                .title("")
                .imageUrl("/this/is/some/url")
                .userId(1)
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Title should not null or empty"))
        ;
    }
    @Test
    @DisplayName("Null title input")
    void invalid_title_null() throws Exception {
        postCreateDto = PostCreateDto.builder()
                .title(null)
                .imageUrl("/this/is/some/url")
                .userId(1)
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Title should not null or empty"))
        ;
    }
    @Test
    @DisplayName("Over 50 letters of title input")
    void invalid_title_greater_50() throws Exception {
        postCreateDto = PostCreateDto.builder()
                .title("asdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgadfg")
                .imageUrl("/this/is/some/url")
                .userId(1)
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Title should not over 50 letters"))
        ;
    }
    @Test
    @DisplayName("Null image url input")
    void invalid_imageUrl_null() throws Exception {
        postCreateDto = PostCreateDto.builder()
                .title("this is title")
                .imageUrl(null)
                .userId(1)
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Image url cannot be null or empty"))
        ;
    }
    @Test
    @DisplayName("Empty string image url input")
    void invalid_imageUrl_empty_string() throws Exception {
        postCreateDto = PostCreateDto.builder()
                .title("this is title")
                .imageUrl("")
                .userId(1)
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Image url cannot be null or empty"))
        ;
    }
    @Test
    @DisplayName("userId less than 1 input")
    void invalid_userId_less_than_1() throws Exception {
        postCreateDto = PostCreateDto.builder()
                .title("this is title")
                .imageUrl("/this/is/some/url")
                .userId(0)
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("User Id must greater than or equal to 1"))
        ;
    }
    @Test
    @DisplayName("postUpdate() null title input")
    void postUpdate_invalid_title_null() throws Exception {
        postUpdateDto = PostUpdateDto.builder()
                .title(null)
                .imageUrl("/this/is/some/url")
                .userId(1L)
                .build();
        willDoNothing().given(postService).postUpdate(postUpdateDto,1L);
        mockMvc.perform(patch("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Title should not null or empty"))
        ;
    }
    @Test
    @DisplayName("postUpdate() empty string title input")
    void postUpdate_invalid_title_empty() throws Exception {
        postUpdateDto = PostUpdateDto.builder()
                .title("")
                .imageUrl("/this/is/some/url")
                .userId(1L)
                .build();
        willDoNothing().given(postService).postUpdate(postUpdateDto,1L);
        mockMvc.perform(patch("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Title should not null or empty"))
        ;
    }
    @Test
    @DisplayName("postUpdate() 51 letters title input")
    void postUpdate_invalid_title_more_than_50() throws Exception {
        postUpdateDto = PostUpdateDto.builder()
                .title("asdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfgasdfga")
                .imageUrl("/this/is/some/url")
                .userId(1L)
                .build();
        willDoNothing().given(postService).postUpdate(postUpdateDto,1L);
        mockMvc.perform(patch("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Title should not over 50 letters"))
        ;
    }
    @Test
    @DisplayName("postUpdate() null image url input")
    void postUpdate_invalid_imageUrl_null() throws Exception {
        postUpdateDto = PostUpdateDto.builder()
                .title("this is some correct title")
                .userId(1L)
                .build();
        willDoNothing().given(postService).postUpdate(postUpdateDto,1L);
        mockMvc.perform(patch("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Image url cannot be null or empty"))
        ;
    }
    @Test
    @DisplayName("postUpdate() empty string image url input")
    void postUpdate_invalid_imageUrl_empty() throws Exception {
        postUpdateDto = PostUpdateDto.builder()
                .title("this is some correct title")
                .imageUrl("")
                .userId(1L)
                .build();
        willDoNothing().given(postService).postUpdate(postUpdateDto,1L);
        mockMvc.perform(patch("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Image url cannot be null or empty"))
        ;
    }
    @Test
    @DisplayName("postUpdate() less than 1 userId input")
    void postUpdate_invalid_userId_less_than_1() throws Exception {
        postUpdateDto = PostUpdateDto.builder()
                .title("this is some correct title")
                .imageUrl("/this/is/some/url")
                .userId(0L)
                .build();
        willDoNothing().given(postService).postUpdate(postUpdateDto,1L);
        mockMvc.perform(patch("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("User Id must greater than or equal to 1"))
        ;
    }
    @Test
    @DisplayName("postDelete() less than 1 idAccount input")
    void postDelete_invalid_idAccount_less_than_1() throws Exception {
        postDeleteDto = PostDeleteDto.builder()
                .idAccount(0L)
                .build();
        willDoNothing().given(postService).postDelete(1L, postDeleteDto);

        mockMvc.perform(delete("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDeleteDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Account Id must greater than or equal to 1"))
        ;
    }
    @Test
    @DisplayName("getSinglePost() invalid userId parameter")
    void getSinglePostInvalidId() throws Exception {
        when(postService.getSinglePost(1L)).thenReturn(null);

        mockMvc.perform(get("/posts/ㅁㅁㅁ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    @DisplayName("getPostPages() valid page parameter")
    void getPostPagesValidParameter() throws Exception {
        postPageDto = PostPageDto.builder().postPerPage(1)
                .currentPage(0)
                .totalNumberOfPages(0)
                .posts(null)
                .build();
        given(postService.getPostPages(1,20)).willReturn(postPageDto);
        mockMvc.perform(get("/posts?page=1&postsPerPage=20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
        ;
    }
    @Test
    @DisplayName("getPostPages() invalid page parameter negative number")
    void getPostPagesInvalidNegative() throws Exception {
        when(postService.getPostPages(1,1)).thenReturn(null);
        mockMvc.perform(get("/posts?page=-1&postsPerPage=20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Information is not valid"))
        ;
    }
    @Test
    @DisplayName("getPostPages() invalid page parameter zero")
    void getPostPagesInvalidZero() throws Exception {
        when(postService.getPostPages(1,1)).thenReturn(null);
        mockMvc.perform(get("/posts?page=0&postsPerPage=20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Information is not valid"))
        ;
    }
    @Test
    @DisplayName("getPostPages() invalid page parameter string")
    void getPostPagesInvalidString() throws Exception {
        when(postService.getPostPages(1,1)).thenReturn(null);
        mockMvc.perform(get("/posts?page=wrong&postsPerPage=20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
        ;
    }
    @Test
    @DisplayName("getPostPages() invalid negative postPerPage parameter negative number")
    void getPostPagesNegativePostPerPage() throws Exception {

        when(postService.getPostPages(1,1)).thenReturn(null);
        mockMvc.perform(get("/posts?page=1&postsPerPage=-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Information is not valid"))
        ;
    }
    @Test
    @DisplayName("getPostPages() invalid zero postPerPage parameter zero")
    void getPostPagesZeroPostPerPage() throws Exception {
        when(postService.getPostPages(1,1)).thenReturn(null);
        mockMvc.perform(get("/posts?page=1&postsPerPage=0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Information is not valid"))
        ;
    }
    @Test
    @DisplayName("getPostPages() invalid string postPerPage parameter string")
    void getPostPagesStringPostPerPage() throws Exception {
        when(postService.getPostPages(1,1)).thenReturn(null);
        mockMvc.perform(get("/posts?page=1&postsPerPage=wrong")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isBadRequest())
        ;
    }
}
