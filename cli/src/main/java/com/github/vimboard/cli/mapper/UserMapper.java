package com.github.vimboard.cli.mapper;

import com.github.vimboard.cli.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    void alter(String username, String password);

    void create(String username, String password);

    void drop(String username);

    User findByName(String username);

    // TODO map result
    List<User> list();
}
