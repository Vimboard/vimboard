package com.github.vimboard.mapper;

import com.github.vimboard.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void alter(String username, String password);

    void create(String username, String password);

    void drop(String username);

    User findByName(String username);

    List<User> list();
}