package com.comerzzia.core.basketcalculator.util.mybatis.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.comerzzia.core.basketcalculator.util.fechas.Fecha;

@MappedTypes(Fecha.class)
@MappedJdbcTypes(JdbcType.DATE)
public class FechaHoraTypeHandler implements TypeHandler<Fecha>{

	@Override
	public void setParameter(PreparedStatement ps, int i, Fecha fecha, JdbcType jdbcType) throws SQLException {
		if (fecha == null || fecha.getDate() == null){
			ps.setNull(i, Types.TIMESTAMP);
		}
		else{
			ps.setTimestamp(i, fecha.getTimestamp());
		}
	}

	@Override
	public Fecha getResult(ResultSet rs, String columnName) throws SQLException {
		Timestamp t = rs.getTimestamp(columnName);
		if (t == null){
			return null;
		}
		return new Fecha(t);
	}

	@Override
	public Fecha getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Timestamp t = cs.getTimestamp(columnIndex);
		if (t == null){
			return null;
		}
		return new Fecha(t);
	}

	@Override
    public Fecha getResult(ResultSet rs, int columnIndex) throws SQLException {
		Timestamp t = rs.getTimestamp(columnIndex);
		if (t == null){
			return null;
		}
		return new Fecha(t);
    }
	
	
}
