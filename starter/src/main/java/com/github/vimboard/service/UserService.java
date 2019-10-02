package com.github.vimboard.service;

import com.github.vimboard.domain.User;
import com.github.vimboard.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final SqlSession sqlSession;

    @Autowired
    public UserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional
    public User alter(String username, String password) {
        String psw = new BCryptPasswordEncoder().encode(password);
        userMapper().alter(username, psw);
        return userMapper().findByName(username);
    }

    @Transactional
    public User create(String username, String password) {
        String psw = new BCryptPasswordEncoder().encode(password);
        userMapper().create(username, psw);
        return userMapper().findByName(username);
    }

    @Transactional
    public User drop(String username) {
        userMapper().drop(username);
        return userMapper().findByName(username);
    }

    @Transactional(readOnly = true)
    public List<User> list() {
        return userMapper().list();
    }

    private UserMapper userMapper() {
        return sqlSession.getMapper(UserMapper.class);
    }
}
