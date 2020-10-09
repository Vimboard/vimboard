package com.github.vimboard.repository;

import com.github.vimboard.domain.Post;
import com.github.vimboard.mapper.PostMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PostRepository {

    private final SqlSession sqlSession;

    @Autowired
    public PostRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional(readOnly = true)
    public List<Post> listThreads(String uri, int limit, int offset) {
        return postMapper().listThreads(uri, limit, offset);
    }

    private PostMapper postMapper() {
        return sqlSession.getMapper(PostMapper.class);
    }
}
