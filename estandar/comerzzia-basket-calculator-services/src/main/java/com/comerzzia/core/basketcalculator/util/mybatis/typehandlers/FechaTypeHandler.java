package com.comerzzia.core.basketcalculator.util.mybatis.typehandlers;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.comerzzia.core.basketcalculator.util.fechas.Fecha;

@MappedTypes(Fecha.class)
@MappedJdbcTypes(JdbcType.DATE)
public class FechaTypeHandler implements TypeHandler<Fecha>{

	@Override
	public void setParameter(PreparedStatement ps, int i, Fecha fecha, JdbcType jdbcType) throws SQLException {
		if (fecha == null || fecha.getDate() == null){
			ps.setNull(i, Types.DATE);
		}
		else{
			ps.setDate(i, fecha.getSQL());
		}
	}

	@Override
	public Fecha getResult(ResultSet rs, String columnName) throws SQLException {
		Date d = rs.getDate(columnName);
		if (d == null){
			return null;
		}
		return new Fecha(d);
	}

	@Override
	public Fecha getResult(CallableStatement cs, int columnIndex) throws SQLException {
		Date d = cs.getDate(columnIndex);
		if (d == null){
			return null;
		}
		return new Fecha(d);
	}

	@Override
    public Fecha getResult(ResultSet rs, int columnIndex) throws SQLException {
		Date d = rs.getDate(columnIndex);
		if (d == null){
			return null;
		}
		return new Fecha(d);
    }
	
	
}
