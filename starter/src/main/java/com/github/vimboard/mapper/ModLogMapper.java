package com.github.vimboard.mapper;

import com.github.vimboard.domain.ModLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModLogMapper {

    void create(ModLog modLog);

    List<ModLog> preview(int mod, long limit);
}
