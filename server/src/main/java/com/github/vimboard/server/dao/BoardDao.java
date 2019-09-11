package com.github.vimboard.server.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDao {

    private final SqlSession sqlSession;

    @Autowired
    public BoardDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void createBoard() {

    }

    public void dropBoard() {

    }
}
