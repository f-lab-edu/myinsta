package com.example.myinsta.controller;

import com.example.myinsta.dto.*;
import com.example.myinsta.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/posts", consumes = "application/json")
public class PostsController {
    private final PostsService postService;
    @PostMapping
    public ResponseEntity<Void> postCreation(@Valid @RequestBody RequestPostCreateDto requestPostCreateDto){
        postService.postCreation(requestPostCreateDto);
        return new ResponseEntity<> (HttpStatus.CREATED);
    }
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> postUpdate(@PathVariable Long postId, @Valid @RequestBody RequestPostUpdateDto requestPostUpdateDto){
        postService.postUpdate(requestPostUpdateDto, postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{idPost}")
    public ResponseEntity<Void> postDelete(@PathVariable Long idPost, @Valid @RequestBody RequestPostDeleteDto requestPostDeleteDto){
        postService.postDelete(idPost, requestPostDeleteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDto> getSinglePost(@PathVariable @Min(value=1,message = "postId parameter must be greater than or equal to 1") Long postId){
        ResponsePostDto responsePostDto = postService.getSinglePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
    }
    @GetMapping
    public ResponseEntity<Object> getPostPages(@RequestParam(defaultValue = "1") @Min(value=1,message = "page parameter must be greater than or equal to 1") int page, @RequestParam(defaultValue = "20") @Min(value=1,message = "postsPerPage parameter must be greater than or equal to 1") int postsPerPage){
        ResponsePostPageDto responsePostPageDto = postService.getPostPages(page, postsPerPage);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostPageDto);
    }
}