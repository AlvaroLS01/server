package com.comerzzia.api.v2.facturacionmagento.services.documentos;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.api.v2.facturacionmagento.persistence.documentos.PropiedadesTiposDocumentoMapper;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoBean;
import com.comerzzia.core.model.tiposdocumentos.TipoDocumentoExample;
import com.comerzzia.core.model.tiposdocumentos.prop.TipoDocumentoPropBean;
import com.comerzzia.core.servicios.sesion.DatosSesionBean;
import com.comerzzia.core.servicios.tipodocumento.ServicioTiposDocumentosImpl;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoException;
import com.comerzzia.core.servicios.tipodocumento.TipoDocumentoNotFoundException;

@Component
public class TiposDocumentoService {

	protected static Logger log = Logger.getLogger(TiposDocumentoService.class);
	
	@Autowired
	private ServicioTiposDocumentosImpl tipoDocumentoService;
	
	@Autowired
	private PropiedadesTiposDocumentoMapper propiedadesTiposDocumentoMapper; 
	
	public TipoDocumentoBean consultar(DatosSesionBean datosSesion, String invoiceDocumentType, String country) throws Exception {
		log.debug("consultar() - Consultando tipo de documento...");
		
		TipoDocumentoBean tipoDocumento = null;
		try {
			TipoDocumentoExample tipoDocumentoExample = new TipoDocumentoExample();
			tipoDocumentoExample.or().andUidActividadEqualTo(datosSesion.getUidActividad()).andCodTipoDocumentoEqualTo(invoiceDocumentType).andCodPaisEqualTo(country);
			tipoDocumento = tipoDocumentoService.consultar(datosSesion, invoiceDocumentType, country);
			
			/* Ahora consultamos y añadimos las propiedades del tipo de documento */
			List<TipoDocumentoPropBean> propiedades = propiedadesTiposDocumentoMapper.selectById(datosSesion.getUidActividad(), tipoDocumento.getIdTipoDocumento());
			tipoDocumento.setPropiedadesTipoDocumento(propiedades);
			
		}
		catch (TipoDocumentoNotFoundException | TipoDocumentoException e) {
			String msg = "Error consultando tipo de documento: " + e.getMessage();
			log.error(msg);
			throw new Exception(msg, e);
		}
		
		return tipoDocumento;
	}

}
