package com.github.vimboard.cli.service;

import com.github.vimboard.cli.mapper.BoardMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    private final SqlSession sqlSession;

    @Autowired
    public BoardService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional
    public void create(String uri, String title, String subtitle) {
        boardMapper().create(uri, title, subtitle);
    }

    private BoardMapper boardMapper() {
        return sqlSession.getMapper(BoardMapper.class);
    }
}
