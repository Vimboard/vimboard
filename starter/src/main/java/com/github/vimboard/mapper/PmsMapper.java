package com.github.vimboard.mapper;

import com.github.vimboard.domain.Pms;
import com.github.vimboard.domain.PmsTo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsMapper {

    long countUnreaded(int mod);

    void create(Pms pms);

    PmsTo find(long id);

    void drop(long id);

    List<Pms> list(int mod);

    void setReaded(long id);
}
