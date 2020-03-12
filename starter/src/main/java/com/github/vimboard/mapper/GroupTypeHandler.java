package com.github.vimboard.mapper;

import com.github.vimboard.domain.Group;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupTypeHandler extends BaseTypeHandler<Group> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            Group parameter, JdbcType jdbcType) throws SQLException {
        ps.setShort(i, parameter.getId());
    }

    @Override
    public Group getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return Group.valueOf(rs.getShort(columnName));
    }

    @Override
    public Group getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return Group.valueOf(rs.getShort(columnIndex));
    }

    @Override
    public Group getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return Group.valueOf(cs.getShort(columnIndex));
    }
}
