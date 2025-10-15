/**
 * ComerZZia 3.0
 *
 * Copyright (c) 2008-2015 Comerzzia, S.L.  All Rights Reserved.
 *
 * THIS WORK IS  SUBJECT  TO  SPAIN  AND  INTERNATIONAL  COPYRIGHT  LAWS  AND
 * TREATIES.   NO  PART  OF  THIS  WORK MAY BE  USED,  PRACTICED,  PERFORMED
 * COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED,  ABRIDGED, CONDENSED,
 * EXPANDED,  COLLECTED,  COMPILED,  LINKED,  RECAST, TRANSFORMED OR ADAPTED
 * WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION
 * OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO
 * CRIMINAL AND CIVIL LIABILITY.
 *
 * CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL
 * RESTRICTIONS.
 */
package com.comerzzia.pos.persistence.core.perfiles;

import org.apache.log4j.Logger;

public class PerfilesDao {


	private static final String TABLA = "D_PERFILES_TBL";
	private static final String VISTA = "D_USUARIOS_PERFILES_TBL";

	private static Logger log = Logger.getLogger(PerfilesDao.class);

//	public static PaginaResultados consultar(Connection conn, ParametrosBuscarPerfilesBean param, String uidActividad, String uidInstancia) throws SQLException {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		// Inicializamos la página de resultados
//		List<PerfilBean> resultados = new ArrayList<PerfilBean>();
//		PaginaResultados paginaResultados = new PaginaResultados(param, resultados);
//
//		// Consultas
//		String sql = obtenerConsulta(param, uidActividad, uidInstancia);
//		String sqlCount = obtenerConsultaCount(param, uidActividad, uidInstancia);
//
//		try {
//			pstmt = new PreparedStatement(conn, sqlCount);
//			if(param.getIdUsuario() != null){
//				pstmt.setLong(1, param.getIdUsuario());
//			}
//			// Obtenemos número de resultados
//			log.debug("consultar() - " + pstmt);
//			rs = pstmt.executeQuery();
//			if (rs.next()) {
//				paginaResultados.setTotalResultados(rs.getInt(1));
//			}
//
//			try {
//				rs.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//			try {
//				pstmt.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//
//			// Si tenemos resultados, obtenemos la pagina requerida
//			if (paginaResultados.getTotalResultados() > 0) {
//				pstmt = new PreparedStatement(conn, sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//				if(param.getIdUsuario() != null){
//					pstmt.setLong(1, param.getIdUsuario());
//				}
//				log.debug("consultar() - " + pstmt);
//				rs = pstmt.executeQuery();
//
//				int contador = 0;
//				if (rs.absolute(paginaResultados.getInicio())) {
//					do {
//						PerfilBean perfil = new PerfilBean();
//						perfil.setIdPerfil(rs.getLong("ID_PERFIL"));
//						perfil.setDesPerfil(rs.getString("DESPERFIL"));
//
//						resultados.add(perfil);
//						contador++;
//					}
//					while (rs.next() && contador < paginaResultados.getTamañoPagina());
//				}
//			}
//
//			return paginaResultados;
//		}
//		finally {
//			try {
//				rs.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//			try {
//				pstmt.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//		}
//	}
//
//	public static String obtenerConsulta(ParametrosBuscarPerfilesBean param, String uidActividad, String uidInstancia) {
//		String sql = null;
//
//		sql = "SELECT P.ID_PERFIL, P.DESPERFIL " + "FROM " + getNombreElemento(TABLA) + "P "; 
//				
//		if(param.getIdUsuario() != null){
//			sql += "LEFT JOIN " + getNombreElemento(VISTA) + "UP ON (UP.ID_PERFIL = P.ID_PERFIL) ";
//		}
//				
//		sql += "WHERE P.UID_INSTANCIA = '" + uidInstancia + "' ";
//
//		// Clausula WHERE
//		String where = getClausulaWhere(param);
//		if (where.length() > 0) {
//			sql += where;
//		}
//
//		// Clausula ORDER BY
//		if (!param.getOrden().isEmpty()) {
//			sql += "ORDER BY " + param.getOrden();
//		}
//
//		return sql;
//	}
//
//	public static String obtenerConsultaCount(ParametrosBuscarPerfilesBean param, String uidActividad, String uidInstancia) {
//		String sql = null;
//
//		sql = "SELECT COUNT(P.ID_PERFIL) " + "FROM " + getNombreElemento(TABLA) + "P "; 
//		
//		if(param.getIdUsuario() != null){
//			sql += "LEFT JOIN " + getNombreElemento(VISTA) + "UP ON (UP.ID_PERFIL = P.ID_PERFIL) ";
//		}
//		
//		sql += "WHERE P.UID_INSTANCIA = '" + uidInstancia + "' ";
//
//		// Clausula WHERE
//		String where = getClausulaWhere(param);
//		if (where.length() > 0) {
//			sql += where;
//		}
//
//		return sql;
//	}
//
//	private static String getClausulaWhere(ParametrosBuscarPerfilesBean param) {
//		String where = "";
//
//		// DESPERFIL
//		if (!param.getDesPerfil().isEmpty()) {
//			where = "AND UPPER(DESPERFIL) LIKE UPPER('" + param.getDesPerfil() + "%') ";
//		}
//		
//		//ID_USUARIO
//		if (param.getIdUsuario() != null){
//			where += " AND ID_USUARIO = ? ";
//		}
//
//		return where;
//	}
//
//	public static List<PerfilBean> consultar(Connection conn, String uidActividad, String uidInstancia) throws SQLException {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql = null;
//		List<PerfilBean> resultados = new ArrayList<PerfilBean>();
//
//		sql = "SELECT ID_PERFIL, DESPERFIL " + "FROM " + getNombreElemento(TABLA) + "WHERE UID_INSTANCIA = ? ";
//
//		try {
//			pstmt = new PreparedStatement(conn, sql);
//			pstmt.setString(1, uidInstancia);
//			log.debug("consultar() - " + pstmt);
//
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				PerfilBean perfil = new PerfilBean();
//				perfil.setIdPerfil(rs.getLong("ID_PERFIL"));
//				perfil.setDesPerfil(rs.getString("DESPERFIL"));
//
//				resultados.add(perfil);
//			}
//
//			return resultados;
//		}
//		finally {
//			try {
//				rs.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//			try {
//				pstmt.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//		}
//	}
//
//	public static PerfilBean consultar(Connection conn, Long idPerfil, String uidActividad, String uidInstancia) throws SQLException {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		PerfilBean perfil = null;
//		String sql = null;
//
//		sql = "SELECT ID_PERFIL, DESPERFIL " + "FROM " + getNombreElemento(TABLA) + "WHERE UID_INSTANCIA = ? " + "AND ID_PERFIL = ?";
//
//		try {
//			pstmt = new PreparedStatement(conn, sql);
//			pstmt.setString(1, uidInstancia);
//			pstmt.setLong(2, idPerfil);
//			log.debug("consultar() - " + pstmt);
//
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				perfil = new PerfilBean();
//				perfil.setIdPerfil(rs.getLong("ID_PERFIL"));
//				perfil.setDesPerfil(rs.getString("DESPERFIL"));
//			}
//
//			return perfil;
//		}
//		finally {
//			try {
//				rs.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//			try {
//				pstmt.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//		}
//	}
//
//	public static void insert(Connection conn, PerfilBean perfil, String uidActividad, String uidInstancia) throws SQLException {
//		PreparedStatement pstmt = null;
//		String sql = null;
//
//		sql = "INSERT INTO " + getNombreElemento(TABLA) + "(UID_INSTANCIA, ID_PERFIL, DESPERFIL) " + "VALUES (?, ?, ?)";
//
//		try {
//			pstmt = new PreparedStatement(conn, sql);
//			pstmt.setString(1, uidInstancia);
//			pstmt.setLong(2, perfil.getIdPerfil());
//			pstmt.setString(3, perfil.getDesPerfil());
//
//			log.debug("insert() - " + pstmt);
//
//			pstmt.execute();
//		}
//		catch (SQLException e) {
//			throw getDaoException(e);
//		}
//		finally {
//			try {
//				pstmt.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//		}
//	}
//
//	public static void update(Connection conn, PerfilBean perfil, String uidActividad, String uidInstancia) throws SQLException {
//		PreparedStatement pstmt = null;
//		String sql = null;
//
//		sql = "UPDATE " + getNombreElemento(TABLA) + "SET DESPERFIL = ? " + "WHERE UID_INSTANCIA = ? " + "AND ID_PERFIL = ?";
//
//		try {
//			pstmt = new PreparedStatement(conn, sql);
//			pstmt.setString(1, perfil.getDesPerfil());
//			pstmt.setString(2, uidInstancia);
//			pstmt.setLong(3, perfil.getIdPerfil());
//
//			log.debug("update() - " + pstmt);
//
//			pstmt.execute();
//		}
//		catch (SQLException e) {
//			throw getDaoException(e);
//		}
//		finally {
//			try {
//				pstmt.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//		}
//	}
//
//	public static void delete(Connection conn, String uidActividad, String uidInstancia, UsuarioBean usuario) throws SQLException {
//		delete(conn, uidActividad, uidInstancia, usuario.getIdUsuario());
//	}
//
//	public static void delete(Connection conn, String uidActividad, String uidInstancia, Long idPerfil) throws SQLException {
//		PreparedStatement pstmt = null;
//		String sql = null;
//
//		sql = "DELETE FROM " + getNombreElemento(TABLA) + "WHERE UID_INSTANCIA = ? " + "AND ID_PERFIL = ?";
//
//		try {
//			pstmt = new PreparedStatement(conn, sql);
//			pstmt.setString(1, uidInstancia);
//			pstmt.setLong(2, idPerfil);
//
//			log.debug("delete() - " + pstmt);
//
//			pstmt.execute();
//		}
//		catch (SQLException e) {
//			throw getDaoException(e);
//		}
//		finally {
//			try {
//				pstmt.close();
//			}
//			catch (Exception ignore) {
//				;
//			}
//		}
//	}
}
