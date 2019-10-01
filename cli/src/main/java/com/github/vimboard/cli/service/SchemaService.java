package com.github.vimboard.cli.service;

import com.github.vimboard.cli.domain.DBVersion;
import com.github.vimboard.cli.mapper.SchemaMapper;
import com.github.vimboard.version.ApplicationVersion;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchemaService {

    private final SqlSession sqlSession;

    @Autowired
    public SchemaService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional
    public void create() {
        schemaMapper().create(ApplicationVersion.get());
    }

    @Transactional
    public void drop() {
        schemaMapper().drop();
    }

    public DBVersion version() {
        return schemaMapper().version();
    }

    private SchemaMapper schemaMapper() {
        return sqlSession.getMapper(SchemaMapper.class);
    }
}