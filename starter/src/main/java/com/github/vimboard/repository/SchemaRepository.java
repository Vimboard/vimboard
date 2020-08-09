package com.github.vimboard.repository;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.domain.DBVersion;
import com.github.vimboard.mapper.SchemaMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class SchemaRepository {

    private final SqlSession sqlSession;
    private final SettingsBean settingsBean;

    @Autowired
    public SchemaRepository(SqlSession sqlSession, SettingsBean settingsBean) {
        this.sqlSession = sqlSession;
        this.settingsBean = settingsBean;
    }

    @Transactional
    public void create() {
        schemaMapper().create(settingsBean.get().getVersion());
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
