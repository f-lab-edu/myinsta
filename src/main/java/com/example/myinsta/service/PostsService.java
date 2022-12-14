package com.example.myinsta.service;

import com.example.myinsta.dao.*;
import com.example.myinsta.dto.*;
import com.example.myinsta.exception.CustomException;
import com.example.myinsta.exception.ErrorCode;
import com.example.myinsta.mapper.PostsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostsService {
    private final PostsMapper postsMapper;
    public void postCreation(RequestPostCreateDto requestPostCreateDto) {
        PostsDao postsDao = PostsDao.builder()
                .title(requestPostCreateDto.getTitle())
                .idAccount(requestPostCreateDto.getUserId())
                .build();

        int result = postsMapper.insertPost(postsDao);
        if(result < 1){
            throw new CustomException(ErrorCode.FAILED_TO_INSERT_POST);
        }

        PostImageDao postImageDao = PostImageDao.builder()
                .idPost(postsDao.getIdPost())
                .imagePath(requestPostCreateDto.getImageUrl())
                .build();
        result = postsMapper.insertPostImage(postImageDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_INSERT_POST_IMAGE);
        }
    }
    public void postUpdate(RequestPostUpdateDto requestPostUpdateDto, Long postId){
        PostsUpdateDao postsUpdateDao = PostsUpdateDao.builder()
                .idPost(postId)
                .idAccount(requestPostUpdateDto.getUserId())
                .title(requestPostUpdateDto.getTitle())
                .build();

        if(!postsMapper.isPostExist(postId)){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST_NOT_FOUND);
        }
        if(!postsMapper.isOwner(postsUpdateDao.getIdAccount())){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST_NOT_OWNER);
        }
        int result = postsMapper.updatePost(postsUpdateDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST);
        }

        PostImagesUpdateDao postImagesUpdateDao = PostImagesUpdateDao.builder()
                .idPost(postId)
                .imagePath(requestPostUpdateDto.getImageUrl())
                .build();

        result = postsMapper.updatePostImage(postImagesUpdateDao);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_UPDATE_POST_IMAGE);
        }
    }
    public void postDelete(Long idPost, RequestPostDeleteDto requestPostDeleteDto) {
        if(!postsMapper.isPostExist(idPost)){
            throw new CustomException(ErrorCode.FAILED_TO_DELETE_POST_NOT_FOUND);
        }
        if(!postsMapper.isOwner(requestPostDeleteDto.getIdAccount())){
            throw new CustomException(ErrorCode.FAILED_TO_DELETE_POST_NOT_OWNER);
        }
        int result = postsMapper.deletePost(idPost);
        if(result != 1){
            throw new CustomException(ErrorCode.FAILED_TO_DELETE_POST);
        }
    }
    @Transactional(readOnly = true)
    public ResponsePostDto getSinglePost(Long postId){
        GetSinglePostDao getSinglePostDao = GetSinglePostDao.builder().idPost(postId).build();
        ResponsePostDto responsePostDto = postsMapper.selectSinglePost( getSinglePostDao );
        if(responsePostDto == null){
            throw new CustomException(ErrorCode.FAILED_TO_GET_SINGLE_POST);
        }
        return responsePostDto;
    }
    @Transactional(readOnly = true)
    public ResponsePostPageDto getPostPages(int page, int postsPerPage){
        int totalNumberOfPosts = postsMapper.getTotalNumberOfPosts();
        int totalNumberOfPages = (int)Math.ceil((float)totalNumberOfPosts/postsPerPage);
        ResponsePostPageDto responsePostPageDto = ResponsePostPageDto.builder()
                .currentPage(page)
                .postPerPage(postsPerPage)
                .totalNumberOfPages(totalNumberOfPages)
                .build();
        PostPageSelectDao postPageSelectDao = PostPageSelectDao.builder().start((page-1)*postsPerPage).end(page*postsPerPage).build();
        responsePostPageDto.setPosts(postsMapper.selectPostPage(postPageSelectDao));
        return responsePostPageDto;
    }
}
