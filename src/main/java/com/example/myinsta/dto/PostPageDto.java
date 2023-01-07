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
public class PostPageDto {
    private List<PostDto> posts;
    private Integer totalNumberOfPages;
    private Integer postPerPage;
    private Integer currentPage;
}
