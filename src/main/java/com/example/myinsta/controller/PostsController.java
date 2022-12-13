package com.example.myinsta.controller;

import com.example.myinsta.dto.PostCreateDto;
import com.example.myinsta.dto.PostUpdateDto;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts", consumes = "application/json")
public class PostsController {
    private final PostsService postService;
    @PostMapping
    public ResponseEntity<Void> postCreation(@Valid @RequestBody PostCreateDto postCreateDto){
        postService.postCreation(postCreateDto);
        return new ResponseEntity<> (HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> postUpdate(@PathVariable Long id, @Valid @RequestBody PostUpdateDto postUpdateDto){
        postService.postUpdate(postUpdateDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
