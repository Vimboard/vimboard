package com.github.vimboard.mapper;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModBoardsTypeHandler extends BaseTypeHandler<String[]> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
            String[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, String.join(",", parameter));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String result = rs.getString(columnName);
        return result.isEmpty()
                ? new String[] {}
                : result.split(",");
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String result = rs.getString(columnIndex);
        return result.isEmpty()
                ? new String[] {}
                : result.split(",");
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String result = cs.getString(columnIndex);
        return result.isEmpty()
                ? new String[] {}
                : result.split(",");
    }
}
