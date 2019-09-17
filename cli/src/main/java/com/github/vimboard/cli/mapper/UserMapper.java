package com.github.vimboard.cli.mapper;

import com.github.vimboard.cli.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    // TODO map result
    User alter(String username, String password);

    // TODO map result
    User create(String username, String password);

    // TODO map result
    User drop(String username);

    // TODO map result
    List<User> list();
}
