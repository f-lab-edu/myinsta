package com.example.myinsta;


import com.example.myinsta.controller.PostsController;
import com.example.myinsta.dto.PostCreateDto;
import com.example.myinsta.service.PostsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private String errorCode;
    private String errorMessage;

    @BeforeEach
    void setUp() {
        errorCode = "$..errorCode";
        errorMessage = "$..errorMessage";
    }

    @Test
    @DisplayName("Empty title input")
    void invalid_title_empty_string() throws Exception {
        postCreateDto = PostCreateDto.builder()
                .title("")
                .imageUrl("/this/is/some/url")
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andDo(print())
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
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andDo(print())
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
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andDo(print())
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
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andDo(print())
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
                .build();
        willDoNothing().given(postService).postCreation(any());
        mockMvc.perform(post("/post/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(errorCode).value(700))
                .andExpect(jsonPath(errorMessage).value("Image url cannot be null or empty"))
        ;
    }

}
