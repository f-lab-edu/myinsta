package com.example.myinsta.controller;

import com.example.myinsta.dto.*;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/posts", consumes = "application/json")
public class PostsController {
    private final PostsService postService;
    @PostMapping
    public ResponseEntity<Void> postCreation(@Valid @RequestBody PostCreateDto postCreateDto){
        postService.postCreation(postCreateDto);
        return new ResponseEntity<> (HttpStatus.CREATED);
    }
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> postUpdate(@PathVariable Long postId, @Valid @RequestBody PostUpdateDto postUpdateDto){
        postService.postUpdate(postUpdateDto, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{idPost}")
    public ResponseEntity<Void> postDelete(@PathVariable Long idPost, @Valid @RequestBody PostDeleteDto postDeleteDto){
        postService.postDelete(idPost, postDeleteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable Long postId){
        PostDto postDto = postService.getSinglePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }
    @GetMapping
    public ResponseEntity<PostPageDto> getPostPages(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer postsPerPage){
        if( page <= 0 ){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        if( postsPerPage <= 0 ){
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        PostPageDto postPageDto = postService.getPostPages(page, postsPerPage);
        return ResponseEntity.status(HttpStatus.OK).body(postPageDto);
    }
}