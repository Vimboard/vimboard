package com.github.vimboard.cli.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchemaMapper {

    void createSchema();

    void dropSchema();

    String getVersion();
}
