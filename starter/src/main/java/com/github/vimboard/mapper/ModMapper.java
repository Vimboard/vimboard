package com.github.vimboard.mapper;

import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import com.github.vimboard.domain.dashboard.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModMapper {

    void alter(int id, String username, String[] boards);

    void alterByName(String username, String password, Group type, String[] boards);

    void alterType(int id, Group type);

    void changePassword(int id, String password);

    void create(String username, String password, Group type, String[] boards);

    void drop(int id);

    void dropByName(String username);

    Mod find(int id);

    Mod findByName(String username);

    List<Mod> list();

    List<User> listUsers();
}
