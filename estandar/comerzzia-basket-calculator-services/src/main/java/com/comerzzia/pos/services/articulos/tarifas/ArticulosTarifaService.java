/**
 * ComerZZia 3.0 Copyright (c) 2008-2015 Comerzzia, S.L. All Rights Reserved. THIS WORK IS SUBJECT TO SPAIN AND INTERNATIONAL COPYRIGHT LAWS AND TREATIES. NO PART OF THIS WORK MAY BE USED, PRACTICED, PERFORMED COPIED, DISTRIBUTED, REVISED, MODIFIED, TRANSLATED, ABRIDGED, CONDENSED, EXPANDED, COLLECTED, COMPILED, LINKED, RECAST, TRANSFORMED OR ADAPTED WITHOUT THE PRIOR WRITTEN CONSENT OF COMERZZIA, S.L. ANY USE OR EXPLOITATION OF THIS WORK WITHOUT AUTHORIZATION COULD SUBJECT THE PERPETRATOR TO CRIMINAL AND CIVIL LIABILITY. CONSULT THE END USER LICENSE AGREEMENT FOR INFORMATION ON ADDITIONAL RESTRICTIONS.
 */

package com.comerzzia.pos.services.articulos.tarifas;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleBean;
import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleExample;
import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleMapper;
import com.comerzzia.pos.persistence.tarifas.TarifaBean;
import com.comerzzia.pos.persistence.tarifas.TarifaKey;
import com.comerzzia.pos.persistence.tarifas.TarifaMapper;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.util.config.SpringContext;

@Service
public class ArticulosTarifaService {

	protected static final Logger log = Logger.getLogger(ArticulosTarifaService.class);

	@Autowired
	Sesion sesion;

	@Autowired
	TarifaMapper tarifaMapper;

	@Autowired
	protected TarifaDetalleMapper tarifaDetalleMapper;

	/** Código de la tarifa general */
	public static final String COD_TARIFA_GENERAL = "GENERAL";

	/**
	 * Obtiene la tarifa del artículo indicado
	 *
	 * @param codigoArticulo
	 * @param codTarifa
	 * @return
	 * @throws com.comerzzia.pos.services.articulos.tarifas.ArticuloTarifaNotFoundException
	 * @throws com.comerzzia.pos.services.articulos.tarifas.ArticuloTarifaServiceException
	 */
	public TarifaDetalleBean consultarArticuloTarifa(String codigoArticulo, String codTarifa, String desglose1,
			String desglose2, Date vigencia) throws ArticuloTarifaNotFoundException, ArticuloTarifaServiceException {
		
		LinkedHashSet<String> tarifas = new LinkedHashSet<>();
		tarifas.add(codTarifa);
		
		return consultarArticuloTarifas(codigoArticulo, tarifas, desglose1, desglose2, vigencia).getDetalle();
	}

	public TarifaArticuloDto consultarArticuloTarifas(String codigoArticulo, LinkedHashSet<String> tarifas,
			String desglose1, String desglose2, Date vigencia)
			throws ArticuloTarifaServiceException, ArticuloTarifaNotFoundException {
		try {
			if (desglose1 == null || desglose1.isEmpty()) {
				desglose1 = "*";
			}

			if (desglose2 == null || desglose2.isEmpty()) {
				desglose2 = "*";
			}
			
			for (String codigoTarifa : tarifas) {
				if (StringUtils.isEmpty(codigoTarifa)) {
					continue;
				}
				
				log.debug("consultarArticuloTarifas() - Consultando tarifa de articulo con código : " + codigoArticulo
						+ " para tarifa: " + codigoTarifa);

				TarifaDetalleBean articuloTarifa = obtenerTarifaDetalle(codigoArticulo, codigoTarifa, desglose1,
						desglose2, vigencia);
				
				if (articuloTarifa != null) {
					TarifaArticuloDto tarifaArticuloDto = getTarifaArticuloDtoFromDetalle(articuloTarifa);
					return tarifaArticuloDto;
				}				
			}

			log.warn("consultarArticuloTarifas() - No se encontró la tarifa asociada al artículo : " + codigoArticulo);
			throw new ArticuloTarifaNotFoundException();
		} catch (ArticuloTarifaNotFoundException e) {
			log.warn("consultarArticuloTarifas() - No se encontró la tarifa asociada al artículo : " + codigoArticulo);
			throw e;
		} catch (Exception e) {
			String msg = "Se ha producido un error consultando la tarifa del artículo con código " + codigoArticulo
					+  " : " + e.getMessage();
			
			log.error("consultarArticuloTarifas() - " + msg, e);
			throw new ArticuloTarifaServiceException(e);
		}
	}

	protected TarifaArticuloDto getTarifaArticuloDtoFromDetalle(TarifaDetalleBean articuloTarifa) {
		TarifaArticuloDto tarifaArticuloDto = SpringContext.getBean(TarifaArticuloDto.class);


		tarifaArticuloDto.setCabecera( consultarTarifa(articuloTarifa.getCodTarifa()));
		tarifaArticuloDto.setDetalle(articuloTarifa);

		return tarifaArticuloDto;
	}

	protected TarifaDetalleBean obtenerTarifaDetalle(String codigoArticulo, String codTar, String desglose1,
			String desglose2, Date vigencia) {
		String uidActividad = sesion.getUidActividad();
		TarifaDetalleExample example = new TarifaDetalleExample();
		example.setOrderByClause(TarifaDetalleExample.ORDER_BY_FECHA_INICIO_DESC + ", "
				+ TarifaDetalleExample.ORDER_BY_DESGLOSE1_DESC + ", " + TarifaDetalleExample.ORDER_BY_DESGLOSE2_DESC);
		example.createCriteria().andCodArticuloEqualTo(codigoArticulo).andBorradoEqualTo("N")
				.andCodTarifaEqualTo(codTar).andUidActividadEqualTo(uidActividad)
				.andFechaInicioLessThanOrEqualTo(vigencia);

		return obtenerArticuloTarifaPorDesgloses(tarifaDetalleMapper.selectByExample(example), desglose1, desglose2);
	}

	/**
	 * Se busca dentro de la lista de objetos <i>TarifaDetalleBean</i> que se pasa
	 * como parámetro, la tarifa de artículo cuyos desgloses coinciden con los que
	 * se pasan como parámetros.
	 * 
	 * @param listArticulosTarifa listado con todas las tarifas asociadas a
	 *                            artículos
	 * @param desglose1           cadena con el valor del desglose1 por el que se
	 *                            hace la comprobación
	 * @param desglose2           cadena con el valor del desglose1 por el que se
	 *                            hace la comprobación
	 * @return el objeto <i>TarifaDetalleBean</i> cuyos desgloses coinciden con los
	 *         pasados como parámetros
	 * @throws ArticuloTarifaNotFoundException se lanza cuando no se encuentra una
	 *                                         tarifa para el artículo con los
	 *                                         desgloses.
	 */
	protected TarifaDetalleBean obtenerArticuloTarifaPorDesgloses(List<TarifaDetalleBean> listArticulosTarifa,
			String desglose1, String desglose2) {
		for (TarifaDetalleBean articuloTarifa : listArticulosTarifa) {
			if (desglose1.equals(articuloTarifa.getDesglose1()) && desglose2.equals(articuloTarifa.getDesglose2())) {
				return articuloTarifa;
			} else if (desglose1.equals(articuloTarifa.getDesglose1()) && "*".equals(articuloTarifa.getDesglose2())) {
				return articuloTarifa;
			} else if ("*".equals(articuloTarifa.getDesglose1()) && desglose2.equals(articuloTarifa.getDesglose2())) {
				return articuloTarifa;
			} else if ("*".equals(articuloTarifa.getDesglose1()) && "*".equals(articuloTarifa.getDesglose2())) {
				return articuloTarifa;
			}
		}
		return null;
	}
	
	public TarifaBean consultarTarifa(String codigoTarifa) {
		log.debug("consultarTarifa() - Buscando tarifa: " + codigoTarifa);
				
		TarifaKey key = new TarifaKey();
		key.setUidActividad(sesion.getUidActividad());
		key.setCodTarifa(codigoTarifa);

		return tarifaMapper.selectByPrimaryKey(key);
	}
	

}
