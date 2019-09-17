package com.github.vimboard.cli.mapper;

import com.github.vimboard.cli.domain.DBVersion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchemaMapper {

    void create(String version);

    void drop();

    DBVersion version();
}
