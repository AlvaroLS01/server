package com.comerzzia.core.basketcalculator.util.mybatis.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes(Boolean.class)
@MappedJdbcTypes(JdbcType.NUMERIC)
public class BooleanNumberTypeHandler implements TypeHandler<Boolean>{

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter != null && parameter ? 1 : 0);
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		Integer num = rs.getInt(columnName);
		return num != null && num.equals(1);
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer num = cs.getInt(columnIndex);
		return num != null && num.equals(1);
	}

	@Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer num = rs.getInt(columnIndex);
		return num != null && num.equals(1);
    }
}
