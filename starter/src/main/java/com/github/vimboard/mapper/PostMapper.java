package com.github.vimboard.mapper;

import com.github.vimboard.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    List<Post> listThreads(String uri, int limit, int offset);
}
