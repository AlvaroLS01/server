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
package com.comerzzia.pos.services.core.perfiles;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class ServicioPerfiles {

	/**
	 * Logger
	 */
	protected static Logger log = Logger.getLogger(ServicioPerfiles.class);
	

//	public PaginaResultados consultar(IDatosSesion datosSesion, ParametrosBuscarPerfilesBean param) throws PerfilException {
//            SqlSession sqlSession = new SqlSession();
//            Connection conn = new Connection();
//
//		try {
//			log.debug("consultar() - Consultando perfiles");
//                        sqlSession.openSession(SessionFactory.openSession());   
//                        String uidInstancia = datosSesion.getUidInstancia();
//                        String uidActividad = datosSesion.getUidActividad();
//                        conn.abrirConexion(sqlSession.getConnection());
//			return PerfilesDao.consultar(conn, param, uidActividad, uidInstancia);
//		}
//		catch (SQLException e) {
//			log.error("consultar() - " + e.getMessage());
//			String mensaje = "Error al consultar perfiles: " + e.getMessage();
//
//			throw new PerfilException(mensaje, e);
//		}
//		finally {
//			conn.cerrarConexion();
//		}
//	}
//
//	public List<PerfilBean> consultar(IDatosSesion datosSesion) throws PerfilException {
//	SqlSession sqlSession = new SqlSession();	
//            Connection conn = new Connection();
//            
//		try {
//			log.debug("consultar() - Consultando todos los perfiles");
//			sqlSession.openSession(SessionFactory.openSession());    
//                        String uidInstancia = datosSesion.getUidInstancia();
//                        String uidActividad = datosSesion.getUidActividad();
//                        conn.abrirConexion(sqlSession.getConnection());
//			return PerfilesDao.consultar(conn, uidActividad, uidInstancia);
//		}
//		catch (SQLException e) {
//			log.error("consultar() - " + e.getMessage());
//			String mensaje = "Error al consultar todos los perfiles: " + e.getMessage();
//
//			throw new PerfilException(mensaje, e);
//		}
//		finally {
//			conn.cerrarConexion();
//		}
//	}
//
//	public PerfilBean consultar(IDatosSesion datosSesion, Long idPerfil) throws PerfilException, PerfilNotFoundException {
//            SqlSession sqlSession = new SqlSession();
//            Connection conn = new Connection();
//
//		try {
//			log.debug("consultar() - Consultando datos del perfil con identificador: " + idPerfil);
//			sqlSession.openSession(SessionFactory.openSession());
//                        String uidInstancia = datosSesion.getUidInstancia();
//                        String uidActividad = datosSesion.getUidActividad();
//                        conn.abrirConexion(sqlSession.getConnection());
//			PerfilBean perfil = PerfilesDao.consultar(conn, idPerfil, uidActividad, uidInstancia);
//
//			if (perfil == null) {
//				String msg = "No se ha encontrado el perfil con identificador: " + idPerfil;
//				log.info("consultar() - " + msg);
//				throw new PerfilNotFoundException(msg);
//			}
//
//			return perfil;
//		}
//		catch (SQLException e) {
//			log.error("consultar() - " + e.getMessage());
//			String mensaje = "Error al consultar datos de un perfil: " + e.getMessage();
//
//			throw new PerfilException(mensaje, e);
//		}
//		finally {
//			conn.cerrarConexion();
//		}
//	}

	
}
