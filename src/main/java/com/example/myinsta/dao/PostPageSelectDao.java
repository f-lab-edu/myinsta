package com.example.myinsta.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostPageSelectDao {
    private Integer start;
    private Integer end;
}
