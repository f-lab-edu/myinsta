package com.example.myinsta.utils.MyBatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;


@Component
public class MyBatisTest {
    public final SqlSession sqlSession;
    //to test if MyBatis SQLSession has been made successfully
    public MyBatisTest( SqlSession sqlSession ){
        this.sqlSession = sqlSession;
        System.out.println(this.sqlSession);
    }
}
