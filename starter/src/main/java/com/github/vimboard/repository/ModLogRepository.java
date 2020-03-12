package com.github.vimboard.repository;

import com.github.vimboard.domain.ModLog;
import com.github.vimboard.mapper.ModLogMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ModLogRepository {

    private final SqlSession sqlSession;

    @Autowired
    public ModLogRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional
    public void create(ModLog modLog) {
        modLogMapper().create(modLog);
    }

    private ModLogMapper modLogMapper() {
        return sqlSession.getMapper(ModLogMapper.class);
    }
}
