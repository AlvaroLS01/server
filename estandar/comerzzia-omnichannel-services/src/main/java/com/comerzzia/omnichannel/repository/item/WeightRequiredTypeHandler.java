package com.comerzzia.omnichannel.repository.item;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

@MappedTypes({Boolean.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class WeightRequiredTypeHandler implements TypeHandler<Boolean> {
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter != null && parameter ? "P" : "U");
	}
	
	protected Boolean getResult(String string)  {
		return string != null && (string.equals("P") || string.equals("W"));
	}

	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		return getResult(rs.getString(columnName));
	}

	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		return getResult(rs.getString(columnIndex));
	}	
	
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return getResult(cs.getString(columnIndex));		
	}
}
