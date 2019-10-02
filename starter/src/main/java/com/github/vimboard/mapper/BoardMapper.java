package com.github.vimboard.mapper;

import com.github.vimboard.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void create(String uri, String title, String subtitle);

    List<Board> list();
}
