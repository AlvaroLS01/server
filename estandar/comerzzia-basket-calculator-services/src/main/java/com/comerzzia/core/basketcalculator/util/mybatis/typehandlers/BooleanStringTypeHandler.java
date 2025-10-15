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
@MappedJdbcTypes(JdbcType.VARCHAR)
public class BooleanStringTypeHandler implements TypeHandler<Boolean>{

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter != null && parameter ? "S" : "N");
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		String string = rs.getString(columnName);
		return string != null && string.equals("S");
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		String string = cs.getString(columnIndex);
		return string != null && string.equals("S");
	}

	@Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		String string = rs.getString(columnIndex);
		return string != null && string.equals("S");
    }
}
