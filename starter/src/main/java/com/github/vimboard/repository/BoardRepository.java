package com.github.vimboard.repository;

import com.github.vimboard.domain.Board;
import com.github.vimboard.mapper.BoardMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BoardRepository {

    private final SqlSession sqlSession;

    @Autowired
    public BoardRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional
    public void create(String uri, String title, String subtitle) {
        boardMapper().create(uri, title, subtitle);
    }

    @Transactional(readOnly = true)
    public List<Board> list() {
        return boardMapper().list();
    }

    private BoardMapper boardMapper() {
        return sqlSession.getMapper(BoardMapper.class);
    }
}
