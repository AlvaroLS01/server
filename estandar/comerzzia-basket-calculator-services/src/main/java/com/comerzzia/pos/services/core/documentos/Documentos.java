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

package com.comerzzia.pos.services.core.documentos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.core.conceptosalmacen.ConceptoAlmacenBean;
import com.comerzzia.pos.persistence.core.documentos.propiedades.PropiedadDocumentoBean;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.services.core.conceptosalmacen.ConceptoService;
import com.comerzzia.pos.services.core.conceptosalmacen.ConceptoServiceException;
import com.comerzzia.pos.services.core.documentos.propiedades.PropiedadesDocumentoService;
import com.comerzzia.pos.services.core.documentos.tipos.TipoDocumentoService;
import com.comerzzia.pos.util.config.AppConfig;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class Documentos {
    protected final Logger log = Logger.getLogger(getClass());
    
    public static String FACTURA_SIMPLIFICADA = "FS";
    public static String FACTURA_COMPLETA = "FT";
    public static String NOTA_CREDITO = "NC"; //No comparar con NC, usar isDocumentoAbono()
    public static String VENTA_CREDITO = "VC";
    public static String APARTADOS = "AP";
    public static String CIERRE_CAJA = "CJCIERRE";

    protected Map<Long, TipoDocumentoBean> documentos;  // Contiene el Documento asociado a un id de documento
    protected Map<String, Long> tiposDocumentos;        // Contiene el id del documento asociado a un código de tipo de documento
    protected Map<Long, String> documentosAbono;        // Contiene el id del documento asociado al código de su tipo de documento abono
    protected Map<String, String> documentosEstandar;     // Contiene el código del documento asociado al código de su tipo de documento estandar
    
    /**
     * Constructor
     */
    public Documentos() {
        documentos = new HashMap<>();
        tiposDocumentos = new HashMap<>();
        documentosAbono = new LinkedHashMap<>();
        documentosEstandar = new LinkedHashMap<String, String>();
    }

    /**
     * Función que inicializa los tipos de documento.
     * @param uidActividad
     * @param codPais
     * @throws DocumentoException
     * @throws DocumentoNotFoundException 
     */
    public void inicializar(String uidActividad, String codPais) throws DocumentoException, DocumentoNotFoundException, ConceptoServiceException {
        try {
            
        	//Miramos si se ha cambiado el codigo de documento para FACTURA SIMPLIFICADA
        	String doc = AppConfig.documentoVenta;
        	if(doc != null){
        		FACTURA_SIMPLIFICADA = doc;
        	}
        	
            // Cargamos los conceptos de almcén de los documentos
            List<ConceptoAlmacenBean> conceptos = SpringContext.getBean(ConceptoService.class).consultarConceptos(uidActividad);
            Map<String, ConceptoAlmacenBean> conceptosMap = new HashMap<>();            
            for (ConceptoAlmacenBean concepto : conceptos ) {
               conceptosMap.put(concepto.getCodconalm(),concepto);
            }
            
            //Cargamos los tipos de documentos
            List<TipoDocumentoBean> listaTiposDocumentos = SpringContext.getBean(TipoDocumentoService.class).consultarTiposDocumentos(uidActividad, codPais);
            for (TipoDocumentoBean tipo : listaTiposDocumentos) {
                documentos.put(tipo.getIdTipoDocumento(), tipo);
                tiposDocumentos.put(tipo.getCodtipodocumento(), tipo.getIdTipoDocumento());
                if (conceptosMap.containsKey(tipo.getCodconalm())){
                    tipo.setConcepto(conceptosMap.get(tipo.getCodconalm()));
                }
            }

            // Cargamos las propiedades de los documentos
            PropiedadesDocumentoService propiedadesDocumentoService = SpringContext.getBean(PropiedadesDocumentoService.class);
            List<PropiedadDocumentoBean> propiedadesTiposDocumento = propiedadesDocumentoService.consultarPropiedadesDocumentos(uidActividad);
            for (PropiedadDocumentoBean prop : propiedadesTiposDocumento) {
                if (documentos.containsKey(prop.getIdTipoDocumento())) {
                    documentos.get(prop.getIdTipoDocumento()).getPropiedades().put(prop.getPropiedad(), prop);
                }
            }

            //Cargamos las relaciones de tipo de documento abono asociado
            List<PropiedadDocumentoBean> propiedadesDocumentoAbono = propiedadesDocumentoService.consultarPropiedadesDocumentos(uidActividad, TipoDocumentoBean.PROPIEDAD_TIPO_DOCUMENTO_ABONO);
            for (PropiedadDocumentoBean propiedadDocumentoAbono : propiedadesDocumentoAbono) {
                if (documentos.containsKey(propiedadDocumentoAbono.getIdTipoDocumento())) {
                	if(!StringUtils.isEmpty(propiedadDocumentoAbono.getValor())){
                       documentosAbono.put(propiedadDocumentoAbono.getIdTipoDocumento(), propiedadDocumentoAbono.getValor());
                	}
                }
            }
            
          //Cargamos las relaciones de tipo de documento estandar asociado
            List<PropiedadDocumentoBean> propiedadesDocumentoEstandar = propiedadesDocumentoService.consultarPropiedadesDocumentos(uidActividad, TipoDocumentoBean.PROPIEDAD_TIPO_DOCUMENTO_ESTANDAR);
            for (PropiedadDocumentoBean propiedadDocumentoEstandar : propiedadesDocumentoEstandar) {
                if (documentos.containsKey(propiedadDocumentoEstandar.getIdTipoDocumento())) {
                	TipoDocumentoBean tipoDocumento = documentos.get(propiedadDocumentoEstandar.getIdTipoDocumento());
                	if(!StringUtils.isEmpty(propiedadDocumentoEstandar.getValor())){
                      documentosEstandar.put(propiedadDocumentoEstandar.getValor(), tipoDocumento.getCodtipodocumento());
                	}
                }
            }
            
            FACTURA_SIMPLIFICADA = documentosEstandar.get(FACTURA_SIMPLIFICADA) != null ? documentosEstandar.get(FACTURA_SIMPLIFICADA) : FACTURA_SIMPLIFICADA;
            FACTURA_COMPLETA = documentosEstandar.get(FACTURA_COMPLETA) != null ? documentosEstandar.get(FACTURA_COMPLETA) : FACTURA_COMPLETA;
            NOTA_CREDITO = documentosEstandar.get(NOTA_CREDITO) != null ? documentosEstandar.get(NOTA_CREDITO) : NOTA_CREDITO;
            VENTA_CREDITO = documentosEstandar.get(VENTA_CREDITO) != null ? documentosEstandar.get(VENTA_CREDITO) : VENTA_CREDITO;
            APARTADOS = documentosEstandar.get(APARTADOS) != null ? documentosEstandar.get(APARTADOS) : APARTADOS;
            CIERRE_CAJA = documentosEstandar.get(CIERRE_CAJA) != null ? documentosEstandar.get(CIERRE_CAJA) : CIERRE_CAJA;
        }
        catch (DocumentoException | DocumentoNotFoundException | ConceptoServiceException ex) {
            throw ex;
        }
    }

    public Map<Long, TipoDocumentoBean> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Map<Long, TipoDocumentoBean> documentos) {
        this.documentos = documentos;
    }

    /**
     * Obtiene el documento para el código de tipo de documento dado
     * @param codTipo
     * @return
     * @throws DocumentoException 
     */
    public TipoDocumentoBean getDocumento(String codTipo) throws DocumentoException {        
        Long idTipoDoc;
        if (tiposDocumentos.containsKey(codTipo.toUpperCase())) {
            idTipoDoc = tiposDocumentos.get(codTipo.toUpperCase());            
            TipoDocumentoBean ti  = documentos.get(idTipoDoc);
            return ti;
        }
        else{
            log.error("getDocumento() No se ha definido tipo de documento " + codTipo + " para el uidActividad y pais determinado");
            throw new DocumentoException("No se ha definido tipo de documento " + codTipo + " para el uidActividad y pais determinado");
        }
    }
    
    /**
     * Obtiene el documento para el id del documento dado
     * @return
     * @throws DocumentoException 
     */
    public TipoDocumentoBean getDocumento(Long idTipoDoc) throws DocumentoException {
        if (documentos.containsKey(idTipoDoc)) {
            TipoDocumentoBean ti  = documentos.get(idTipoDoc);
            return ti;
        }
        else{
            log.error("getDocumento() No se ha definido tipo de documento " + idTipoDoc + " para el uidActividad y pais determinado");
            throw new DocumentoException("No se ha definido tipo de documento " + idTipoDoc + " para el uidActividad y pais determinado");
        }
    }

    public boolean isDocumentosAbonoConfigurados() {
        return !documentosAbono.isEmpty();
    }

    public List<String> getTiposDocumentoAbonables() {
        List<String> list = new ArrayList<>();

        if (isDocumentosAbonoConfigurados()) {
            //Cada tipo de documento define el tipo de documento que genera al ser devueltos
            for (Long aLong : documentosAbono.keySet()) {
                try {
                    list.add(getDocumento(aLong).getCodtipodocumento());
                } catch (DocumentoException e) {
                }
            }
        } else {
            //Por compatibilidad con la configuración de BD
            //Siempre se usa el tipo de documento NC para las devoluciones.
            try {
                List<String> tiposDocumentosOrigen = getDocumento(Documentos.NOTA_CREDITO).getTiposDocumentosOrigen();
                for (String s : tiposDocumentosOrigen) {
                    try {
                        list.add(getDocumento(s).getCodtipodocumento());
                    } catch (DocumentoException e) {
                    }
                }
            } catch (DocumentoException e) {
            }
        }
        return list;
    }

    /**
     * @param codTipoDocumento codTipo a comprobar
     * @return true si el parámetro corresponde a un documento configurado como de abono de otro documento. O, por compatibilidad, si es "NC"
     */
    public boolean isDocumentoAbono(String codTipoDocumento) {
        if (isDocumentosAbonoConfigurados()) {
            return documentosAbono.containsValue(codTipoDocumento);
        } else {
            return codTipoDocumento.equals(NOTA_CREDITO);
        }
    }

    public TipoDocumentoBean getDocumentoAbono(String codTipoDocumento) throws DocumentoException {
        if (isDocumentosAbonoConfigurados()) {
            String codTipo = documentosAbono.get(getDocumento(codTipoDocumento).getIdTipoDocumento());
			if (codTipo == null) {
            	return getDocumento(NOTA_CREDITO);
            }
            return getDocumento(codTipo);
        } else {
            return getDocumento(NOTA_CREDITO);
        }
    }
    
    /**
     * Obtiene el tipo de documento estándar correspondiente a un código de tipo de documento personalizado
     * @param valor
     * @return el codTipoDocumento estándar o null si no existe un documento que lo estandarice
     */
    public String getCodTipoDocumentoEstandar(String valor){
    	String res = null;
    	if(documentosEstandar.containsValue(valor)){
    		for(String codDocumento:documentosEstandar.keySet()){
    			if(documentosEstandar.get(codDocumento).equals(valor)){
    				res = codDocumento;
    				break;
    			}
    		}
    	}
    	if(res==null){
    		 log.warn("getCodTipoDocumentoEstandar() No existe un documento que estandarice al documento con código "+valor);  		 
    	}
		return res;
    }
    
    public Boolean isTieneDocumentoAbonoConfigurado(String codTipoDocumento) throws DocumentoException{
    	return documentosAbono.get(getDocumento(codTipoDocumento).getIdTipoDocumento()) != null;
    }

}
