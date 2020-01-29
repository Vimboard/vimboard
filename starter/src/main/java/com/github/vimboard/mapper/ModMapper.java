package com.github.vimboard.mapper;

import com.github.vimboard.domain.Group;
import com.github.vimboard.domain.Mod;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModMapper {

    void alter(String username, String password, Group type, String[] boards);

    void create(String username, String password, Group type, String[] boards);

    void drop(String username);

    Mod findByName(String username);

    List<Mod> list();
}
