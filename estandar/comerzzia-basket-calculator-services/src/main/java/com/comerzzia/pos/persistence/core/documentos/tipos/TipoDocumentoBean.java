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
package com.comerzzia.pos.persistence.core.documentos.tipos;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.comerzzia.pos.persistence.core.conceptosalmacen.ConceptoAlmacenBean;
import com.comerzzia.pos.persistence.core.documentos.propiedades.PropiedadDocumentoBean;

public class TipoDocumentoBean extends TipoDocumentoKey {
    
    private String destipodocumento;
    private String idContador;
    private String codtipodocumento;
    private String codpais;
    private String claseProcesamiento;
    private String codaplicacion;
    private String codconalm;
    private String ambitoAplicacion;
    private String proceso;
    private String claseProcesamientoRemoto;
    private Long idAccionEstados;
    private Long idEstadoAlta;
    private Boolean devolucion;
    
    //INICIO ATRIBUTOS PERSONALIZADOS--------------------------------------------    
    public static String PROPIEDAD_FORMATO_IMPRESION = "POS.FORMATO_IMPRESION";
    public static String PROPIEDAD_FORMATO_IMPRESION_NO_CONFIGURADO = "POS.FORMATO_IMPRESION_NO_CONFIGURADO";
    public static String PROPIEDAD_TIPO_DOCUMENTO_FACTURA_DIRECTA = "TIPO_DOCUMENTO_FACTURA_DIRECTA";
    public static String PROPIEDAD_TIPOS_DOCUMENTOS_ORIGEN = "TIPOS_DOCUMENTOS_ORIGEN";
    public static String PROPIEDAD_IMPORTE_MAXIMO_SIN_IMPUESTOS = "IMPORTE_MAXIMO_SIN_IMPUESTOS";
    public static String PROPIEDAD_IMPORTE_MAXIMO = "IMPORTE_MAXIMO";
    public static String PROPIEDAD_FORMATO_IMPRESION_TICKET_REGALO = "POS.FORMATO_IMPRESION_TICKET_REGALO";
    public static String PROPIEDAD_FORMATO_IMPRESION_MOV_TARJ_REGALO = "POS.FORMATO_IMPRESION_GIFTCARD";
    public static String PROPIEDAD_FORMATO_IMPRESION_BOLETA = "POS.FORMATO_IMPRESION_BOLETA";
    public static String PROPIEDAD_FORMA_PAGO_VALE = "POS.FORMA_PAGO_VALE";
    public static String PROPIEDAD_FORMATO_IMPRESION_VALE = "POS.FORMATO_IMPRESION_VALE";
    public static String PROPIEDAD_REQUIERE_COMPLETAR_PAGOS = "POS.REQUIERE_COMPLETAR_PAGOS";
    public static String PROPIEDAD_TIPOS_DOCUMENTO_VENTA_CREDITO = "TIPOS_DOCUMENTO_VENTA_CREDITO";
    public static String PROPIEDAD_PERMITE_TICKET_REGALO = "POS.PERMITE_TICKET_REGALO";
    public static String PROPIEDAD_DATOS_ADICIONALES_PAGOS = "POS.DATOS_ADICIONALES_PAGOS";
    public static String PROPIEDAD_CLASE_DOCUMENTO = "POS.CLASE_DOCUMENTO";
    public static String PROPIEDAD_TIPO_DOCUMENTO_ABONO = "TIPO_DOCUMENTO_ABONO";
	public static String PROPIEDAD_TIPO_DOCUMENTO_ESTANDAR = "TIPO_DOCUMENTO_ESTANDAR";
  	public static String PROPIEDAD_IMPORTE_MAXIMO_EFECTIVO = "IMPORTE_MAXIMO_EFECTIVO";
    public static String PROPIEDAD_IMPORTE_MAXIMO_EFECTIVO_EX = "IMPORTE_MAXIMO_EFECTIVO_EX";
    public static String PROPIEDAD_DIAS_MAXIMO_DEVOLUCION = "DIAS_MAXIMO_DEVOLUCION";
        
    private Map<String, PropiedadDocumentoBean> propiedades; 
    private ConceptoAlmacenBean concepto;
        
    //FIN ATRIBUTOS PERSONALIZADOS-----------------------------------------------

    public String getDestipodocumento() {
        return destipodocumento;
    }

    public void setDestipodocumento(String destipodocumento) {
        this.destipodocumento = destipodocumento == null ? null : destipodocumento.trim();
    }

    public String getIdContador() {
        return idContador;
    }

    public void setIdContador(String idContador) {
        this.idContador = idContador == null ? null : idContador.trim();
    }

    public String getCodtipodocumento() {
        return codtipodocumento;
    }

    public void setCodtipodocumento(String codtipodocumento) {
        this.codtipodocumento = codtipodocumento == null ? null : codtipodocumento.trim();
    }

    public String getCodpais() {
        return codpais;
    }

    public void setCodpais(String codpais) {
        this.codpais = codpais == null ? null : codpais.trim();
    }

    public String getClaseProcesamiento() {
        return claseProcesamiento;
    }

    public void setClaseProcesamiento(String claseProcesamiento) {
        this.claseProcesamiento = claseProcesamiento == null ? null : claseProcesamiento.trim();
    }

    public String getCodaplicacion() {
        return codaplicacion;
    }

    public void setCodaplicacion(String codaplicacion) {
        this.codaplicacion = codaplicacion == null ? null : codaplicacion.trim();
    }

    public String getCodconalm() {
        return codconalm;
    }

    public void setCodconalm(String codconalm) {
        this.codconalm = codconalm == null ? null : codconalm.trim();
    }

    public String getAmbitoAplicacion() {
        return ambitoAplicacion;
    }

    public void setAmbitoAplicacion(String ambitoAplicacion) {
        this.ambitoAplicacion = ambitoAplicacion == null ? null : ambitoAplicacion.trim();
    }
    
    
    //FIN MÉTODOS PERSONALIZADOS-----------------------------------------------

    public TipoDocumentoBean() {
        propiedades = new HashMap<String, PropiedadDocumentoBean>();
    }
    
    public Map<String, PropiedadDocumentoBean> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Map<String, PropiedadDocumentoBean> propiedades) {
        this.propiedades = propiedades;
    }
    
    // Operaciones de consula de signo del documento
    public boolean isSignoPositivo(){
        return (concepto!=null && concepto.getSigno()!= null && concepto.getSigno().equals("+"));
    }
    
    public boolean isSignoNegativo(){
        return (concepto!=null && concepto.getSigno()!= null && concepto.getSigno().equals("-"));
    }
    
    public boolean isSignoLibre(){
        // Concepto es nulo, o signo es nulo o signo no es ni + ni -
        return concepto==null || concepto.getSigno() == null || (!concepto.getSigno().equals("+") && !concepto.getSigno().equals("-"));
    }
    
    public String getFormatoImpresion(){
        if (propiedades.containsKey(PROPIEDAD_FORMATO_IMPRESION)){
            return propiedades.get(PROPIEDAD_FORMATO_IMPRESION).getValor();
        }
        return PROPIEDAD_FORMATO_IMPRESION_NO_CONFIGURADO;
    }
    
    public String getFormatoImpresionTicketRegalo(){
        if (propiedades.containsKey(PROPIEDAD_FORMATO_IMPRESION_TICKET_REGALO)){
            return propiedades.get(PROPIEDAD_FORMATO_IMPRESION_TICKET_REGALO).getValor();
        }
        return PROPIEDAD_FORMATO_IMPRESION_TICKET_REGALO + " NO CONFIGURADO";
    }
    
    public String getFormatoImpresionVale(){
        if (propiedades.containsKey(PROPIEDAD_FORMATO_IMPRESION_VALE)){
            return propiedades.get(PROPIEDAD_FORMATO_IMPRESION_VALE).getValor();
        }
        return PROPIEDAD_FORMATO_IMPRESION_VALE + " NO CONFIGURADO";
    }
    
    public boolean getRequiereCompletarPagos(){
        if (propiedades.containsKey(PROPIEDAD_REQUIERE_COMPLETAR_PAGOS)){
            return Boolean.parseBoolean(propiedades.get(PROPIEDAD_REQUIERE_COMPLETAR_PAGOS).getValor());
        }
        return true;
    }
    
    public boolean getPermiteTicketRegalo(){
        if (propiedades.containsKey(PROPIEDAD_PERMITE_TICKET_REGALO)){
            return propiedades.get(PROPIEDAD_PERMITE_TICKET_REGALO).getValor().equals("S");
        }
        return false;
    }
    
    
    public String getFormaPagoVale(){
        if (propiedades.containsKey(PROPIEDAD_FORMA_PAGO_VALE)){
            return propiedades.get(PROPIEDAD_FORMA_PAGO_VALE).getValor();
        }
        return PROPIEDAD_FORMA_PAGO_VALE + " NO CONFIGURADO";
    }
    
    public String getFormatoImpresionBoleta(){
        if (propiedades.containsKey(PROPIEDAD_FORMATO_IMPRESION_BOLETA)){
            return propiedades.get(PROPIEDAD_FORMATO_IMPRESION_BOLETA).getValor();
        }
        return PROPIEDAD_FORMATO_IMPRESION_BOLETA + " NO CONFIGURADO";
    }
    
    public String getFormatoImpresionMovTarjRegalo(){
        if (propiedades.containsKey(PROPIEDAD_FORMATO_IMPRESION_MOV_TARJ_REGALO)){
            return propiedades.get(PROPIEDAD_FORMATO_IMPRESION_MOV_TARJ_REGALO).getValor();
        }
        return PROPIEDAD_FORMATO_IMPRESION_MOV_TARJ_REGALO + " NO CONFIGURADO";
    }
    
    public String getTipoDocumentoFacturaDirecta(){
        if (propiedades.containsKey(PROPIEDAD_TIPO_DOCUMENTO_FACTURA_DIRECTA)){
            return propiedades.get(PROPIEDAD_TIPO_DOCUMENTO_FACTURA_DIRECTA).getValor();
        }
        return null;
    }
    
    public String getTipoDocumentoEstandar(){
        if (propiedades.containsKey(PROPIEDAD_TIPO_DOCUMENTO_ESTANDAR)){
            return propiedades.get(PROPIEDAD_TIPO_DOCUMENTO_ESTANDAR).getValor();
        }
        return null;
    }
    
    public BigDecimal getImporteMaximo(){
        if (propiedades.containsKey(PROPIEDAD_IMPORTE_MAXIMO)){
            String importeMax = propiedades.get(PROPIEDAD_IMPORTE_MAXIMO).getValor();
            if (importeMax!=null && !importeMax.isEmpty()){
                return new BigDecimal(importeMax);
            }
        }
        return null;
    }
    
    public BigDecimal getImporteMaximoSinImpuestos(){
        if (propiedades.containsKey(PROPIEDAD_IMPORTE_MAXIMO_SIN_IMPUESTOS)){
            String importeMax = propiedades.get(PROPIEDAD_IMPORTE_MAXIMO_SIN_IMPUESTOS).getValor();
            if (importeMax!=null && !importeMax.isEmpty()){
                return new BigDecimal(importeMax);
            }
        }
        return null;
    }
    
    public BigDecimal getImporteMaximoEfectivo(){
    	if (propiedades.containsKey(PROPIEDAD_IMPORTE_MAXIMO_EFECTIVO)){
            String importeMax = propiedades.get(PROPIEDAD_IMPORTE_MAXIMO_EFECTIVO).getValor();
            if (importeMax!=null && !importeMax.isEmpty()){
                return new BigDecimal(importeMax);
            }
        }
        return null;
    }
    
    public BigDecimal getImporteMaximoEfectivoEx(){
    	if (propiedades.containsKey(PROPIEDAD_IMPORTE_MAXIMO_EFECTIVO_EX)){
            String importeMax = propiedades.get(PROPIEDAD_IMPORTE_MAXIMO_EFECTIVO_EX).getValor();
            if (importeMax!=null && !importeMax.isEmpty()){
                return new BigDecimal(importeMax);
            }
        }
        return null;
    }
    
     public List<String> getTiposDocumentosOrigen(){
        List<String> resultados = new LinkedList<String>();
         if (propiedades.containsKey(PROPIEDAD_TIPOS_DOCUMENTOS_ORIGEN)){
             String tiposDoc = propiedades.get(PROPIEDAD_TIPOS_DOCUMENTOS_ORIGEN).getValor();
             if (tiposDoc !=null){
                 if(tiposDoc.contains("|")){
                     String[] splitTiposDoc = tiposDoc.split("\\|");
                     resultados.addAll(Arrays.asList(splitTiposDoc));
                 }
                 else{
                     resultados.add(tiposDoc);
                 }
             }
        }
        return resultados;
    }
     
     public List<String> getTiposDocumentosVentaCredito(){
         List<String> resultados = new LinkedList<String>();
          if (propiedades.containsKey(PROPIEDAD_TIPOS_DOCUMENTO_VENTA_CREDITO)){
              String tiposDoc = propiedades.get(PROPIEDAD_TIPOS_DOCUMENTO_VENTA_CREDITO).getValor();
              if (tiposDoc !=null){
                  if(tiposDoc.contains("|")){
                      String[] splitTiposDoc = tiposDoc.split("\\|");
                      resultados.addAll(Arrays.asList(splitTiposDoc));
                  }
                  else{
                      resultados.add(tiposDoc);
                  }
              }
         }
         return resultados;
     }
     
     public String getOpcionesDatosAdicionales(){
    	 if (propiedades.containsKey(PROPIEDAD_DATOS_ADICIONALES_PAGOS)){
             return propiedades.get(PROPIEDAD_DATOS_ADICIONALES_PAGOS).getValor();
         }
         return null;
     }
     
    public String getClaseDocumento(){
    	PropiedadDocumentoBean propiedadDocumentoBean = propiedades.get(PROPIEDAD_CLASE_DOCUMENTO);
    	if(propiedadDocumentoBean != null){
    		return propiedadDocumentoBean.getValor();
    	}else{
    		return null;
    	}
    }
    
    public Integer getDiasMaximoDevolucion() {
    	PropiedadDocumentoBean propiedadDocumentoBean = propiedades.get(PROPIEDAD_DIAS_MAXIMO_DEVOLUCION);
    	if(propiedadDocumentoBean != null){
    		return new Integer(propiedadDocumentoBean.getValor());
    	}else{
    		return null;
    	}
    }
    
    public ConceptoAlmacenBean getConcepto() {
        return concepto;
    }

    public void setConcepto(ConceptoAlmacenBean concepto) {
        this.concepto = concepto;
    }
    
    public Boolean isDevolucion(){
    	return devolucion;
    }
    
    public void setDevolucion(Boolean devolucion){
    	this.devolucion = devolucion;
    }

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getClaseProcesamientoRemoto() {
		return claseProcesamientoRemoto;
	}

	public void setClaseProcesamientoRemoto(String claseProcesamientoRemoto) {
		this.claseProcesamientoRemoto = claseProcesamientoRemoto;
	}

	public Long getIdAccionEstados() {
		return idAccionEstados;
	}

	public void setIdAccionEstados(Long idAccionEstados) {
		this.idAccionEstados = idAccionEstados;
	}

	public Long getIdEstadoAlta() {
		return idEstadoAlta;
	}

	public void setIdEstadoAlta(Long idEstadoAlta) {
		this.idEstadoAlta = idEstadoAlta;
	}
    
    //INICIO MÉTODOS PERSONALIZADOS--------------------------------------------




}