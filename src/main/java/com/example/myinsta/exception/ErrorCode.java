package com.example.myinsta.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT(700, "Information is not valid"),
    ALREADY_EXIST_EMAIL(701, "Email is already exist"),
    FAILED_TO_INSERT(702, "Sign-up failed"),
    FAILED_TO_INSERT_POST(801, "Post creation failed"),
    FAILED_TO_INSERT_POST_IMAGE(802, "Post image creation failed"),
    FAILED_TO_UPDATE_POST(803,"Post update failed"),
    FAILED_TO_UPDATE_POST_IMAGE( 804,"Post image update failed" ),
    FAILED_TO_UPDATE_POST_NOT_FOUND( 804,"Post cannot be found" ),
    FAILED_TO_UPDATE_POST_NOT_OWNER(805, "Only post owner can modify post" ),
    FAILED_TO_DELETE_POST(806,"Post deletion failed" ),
    FAILED_TO_DELETE_POST_NOT_FOUND(807, "Post deletion failed post cannot be found"),
    FAILED_TO_DELETE_POST_NOT_OWNER(808,"Only post owner can delete post" ),
    FAILED_TO_GET_SINGLE_POST(806,"Cannot find post" ),
    NOT_FOUND_ACCOUNT(703,"Cannot find account" );

    private final int status;
    private final String message;
}
