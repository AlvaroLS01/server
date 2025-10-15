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
public class BooleanTypeHandler implements TypeHandler<Boolean>{

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter ? "S" : "N");
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getString(columnName).equals("S");
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getString(columnIndex).equals("S");
	}

	@Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getString(columnIndex).equals("S");
    }
}
