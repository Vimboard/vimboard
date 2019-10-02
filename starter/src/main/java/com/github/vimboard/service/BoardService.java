package com.github.vimboard.service;

import com.github.vimboard.domain.Board;
import com.github.vimboard.mapper.BoardMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Board> list() {
        return boardMapper().list();
    }

    private BoardMapper boardMapper() {
        return sqlSession.getMapper(BoardMapper.class);
    }
}
