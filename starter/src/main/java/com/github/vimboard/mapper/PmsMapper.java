package com.github.vimboard.mapper;

import com.github.vimboard.domain.Pms;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsMapper {

    long count();

    long countUnreaded(int mod);

    List<Pms> list(int mod);
}
