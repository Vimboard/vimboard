package com.github.vimboard.cli.dao;

import com.github.vimboard.cli.mapper.SchemaMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class SchemaDao {

    private final SqlSession sqlSession;

    public SchemaDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void create() {

    }

    public String getVersion() {
        SchemaMapper schemaMapper = sqlSession.getMapper(SchemaMapper.class);
        return "Server version: " + schemaMapper.getVersion();
    }
}
