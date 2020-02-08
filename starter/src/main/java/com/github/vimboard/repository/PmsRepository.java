package com.github.vimboard.repository;

import com.github.vimboard.mapper.PmsMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PmsRepository {

    private final SqlSession sqlSession;

    @Autowired
    public PmsRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional(readOnly = true)
    public long count() {
        return pmsMapper().count();
    }

    private PmsMapper pmsMapper() {
        return sqlSession.getMapper(PmsMapper.class);
    }
}
