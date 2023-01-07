package com.example.myinsta.controller;

import com.example.myinsta.dto.*;
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
    public ResponseEntity<PostPageDto> getPostPages(@RequestParam(defaultValue = "1") Integer page){
        log.info("getPostPages");
        PostPageDto postPageDto = postService.getPostPages(page);
        return ResponseEntity.status(HttpStatus.OK).body(postPageDto);
    }
}