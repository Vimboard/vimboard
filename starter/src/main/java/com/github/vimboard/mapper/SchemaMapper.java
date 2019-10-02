package com.github.vimboard.mapper;

import com.github.vimboard.domain.DBVersion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchemaMapper {

    void create(String version);

    void drop();

    DBVersion version();
}
