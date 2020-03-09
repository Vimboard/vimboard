package com.github.vimboard.repository;

import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.domain.dashboard.User;
import com.github.vimboard.mapper.ModMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ModRepository {

    private final SqlSession sqlSession;

    @Autowired
    public ModRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional
    public Mod alter(String username, String password,
            Group type, String[] boards) {
        String psw = new BCryptPasswordEncoder().encode(password);
        modMapper().alter(username, psw, type, boards);
        return modMapper().findByName(username);
    }

    @Transactional
    public Mod create(String username, String password,
            Group type, String[] boards) {
        String psw = new BCryptPasswordEncoder().encode(password);
        modMapper().create(username, psw, type, boards);
        return modMapper().findByName(username);
    }

    @Transactional
    public Mod drop(String username) {
        modMapper().drop(username);
        return modMapper().findByName(username); // TODO: is null ??
    }

    @Transactional(readOnly = true)
    public Mod findByName(String username) {
        return modMapper().findByName(username);
    }

    @Transactional(readOnly = true)
    public List<Mod> list() {
        return modMapper().list();
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return modMapper().listUsers();
    }

    private ModMapper modMapper() {
        return sqlSession.getMapper(ModMapper.class);
    }
}
