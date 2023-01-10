package com.example.myinsta.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostPageSelectDao {
    private int start;
    private int end;
}
