package com.github.vimboard.mapper;

import com.github.vimboard.domain.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {

    long count();

    List<Report> list();
}
