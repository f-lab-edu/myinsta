package com.example.myinsta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostPageDto {
    private List<ResponsePostDto> posts;
    private int totalNumberOfPages;
    private int postPerPage;
    private int currentPage;
}
