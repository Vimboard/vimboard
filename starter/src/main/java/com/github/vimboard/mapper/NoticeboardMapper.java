package com.github.vimboard.mapper;

import com.github.vimboard.domain.Noticeboard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeboardMapper {

    List<Noticeboard> preview(int limit);
}
