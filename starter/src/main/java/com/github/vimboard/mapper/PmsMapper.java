package com.github.vimboard.mapper;

import com.github.vimboard.domain.Pms;
import com.github.vimboard.domain.PmsTo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsMapper {

    long count();

    long countUnreaded(int mod);

    PmsTo find(long id);

    void drop(long id);

    List<Pms> list(int mod);

    void setReaded(long id);
}
