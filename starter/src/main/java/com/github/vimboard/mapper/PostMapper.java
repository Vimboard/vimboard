package com.github.vimboard.mapper;

import com.github.vimboard.domain.CitedPost;
import com.github.vimboard.domain.NumPosts;
import com.github.vimboard.domain.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PostMapper {

    List<CitedPost> listCitedPosts(String uri, Collection<Long> cites);

    List<Post> listPosts(String uri, long id, int limit);

    List<Post> listThreads(String uri, int limit, long offset);

    NumPosts numPosts(String uri, long id);
}
