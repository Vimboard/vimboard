package com.github.vimboard.repository;

import com.github.vimboard.config.SettingsBean;
import com.github.vimboard.domain.Noticeboard;
import com.github.vimboard.mapper.NoticeboardMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NoticeboardRepository {

    private final SqlSession sqlSession;
    private final SettingsBean settingsBean;

    @Autowired
    public NoticeboardRepository(
            SqlSession sqlSession,
            SettingsBean settingsBean) {
        this.sqlSession = sqlSession;
        this.settingsBean = settingsBean;
    }

    @Transactional(readOnly = true)
    public List<Noticeboard> preview() {
        return noticeboardMapper().preview(
                settingsBean.getAll().getMod().getNoticeboardDashboard());
    }

    private NoticeboardMapper noticeboardMapper() {
        return sqlSession.getMapper(NoticeboardMapper.class);
    }
}
