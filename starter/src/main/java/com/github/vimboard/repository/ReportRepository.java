package com.github.vimboard.repository;

import com.github.vimboard.domain.Report;
import com.github.vimboard.mapper.ReportMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ReportRepository {

    private final SqlSession sqlSession;

    @Autowired
    public ReportRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional(readOnly = true)
    public List<Report> list() {
        return reportMapper().list();
    }

    @Transactional(readOnly = true)
    public long count() {
        return reportMapper().count();
    }

    private ReportMapper reportMapper() {
        return sqlSession.getMapper(ReportMapper.class);
    }
}
