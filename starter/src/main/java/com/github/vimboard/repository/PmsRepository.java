package com.github.vimboard.repository;

import com.github.vimboard.domain.Pms;
import com.github.vimboard.mapper.PmsMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public long countUnreaded(int mod) {
        return pmsMapper().countUnreaded(mod);
    }

    @Transactional(readOnly = true)
    public List<Pms> list(int mod) {
        return pmsMapper().list(mod);
    }

    private PmsMapper pmsMapper() {
        return sqlSession.getMapper(PmsMapper.class);
    }
}
