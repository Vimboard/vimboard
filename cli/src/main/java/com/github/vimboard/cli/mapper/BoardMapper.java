package com.github.vimboard.cli.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {

    void create(String uri, String title, String subtitle);
}
