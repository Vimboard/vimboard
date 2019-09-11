package com.github.vimboard.cli.dao;

import com.github.vimboard.cli.domain.DBVersion;
import com.github.vimboard.cli.mapper.SchemaMapper;
import com.github.vimboard.version.ApplicationVersion;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SchemaDao {

    private final SqlSession sqlSession;

    @Autowired
    public SchemaDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public void createSchema() {
        schemaMapper().createSchema(ApplicationVersion.get());
    }

    public void dropSchema() {
        schemaMapper().dropSchema();
    }

    public DBVersion getVersion() {
        return schemaMapper().getVersion();
    }

    private SchemaMapper schemaMapper() {
        return sqlSession.getMapper(SchemaMapper.class);
    }
}
