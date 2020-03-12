package com.github.vimboard.mapper;

import com.github.vimboard.domain.ModLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ModLogMapper {

    void create(ModLog modLog);
}
