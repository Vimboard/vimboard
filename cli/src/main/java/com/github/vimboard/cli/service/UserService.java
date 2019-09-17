package com.github.vimboard.cli.service;

import com.github.vimboard.cli.domain.User;
import com.github.vimboard.cli.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final SqlSession sqlSession;

    @Autowired
    public UserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public User alter(String username, String password) {
        String psw = new BCryptPasswordEncoder().encode(password);
        return userMapper().alter(username, psw);
    }

    public User create(String username, String password) {
        String psw = new BCryptPasswordEncoder().encode(password);
        return userMapper().create(username, psw);
    }

    public User drop(String username) {
        return userMapper().drop(username);
    }

    public List<User> list() {
        return userMapper().list();
    }

    private UserMapper userMapper() {
        return sqlSession.getMapper(UserMapper.class);
    }
}
