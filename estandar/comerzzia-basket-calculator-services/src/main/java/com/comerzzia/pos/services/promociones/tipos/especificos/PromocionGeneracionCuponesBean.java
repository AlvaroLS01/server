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
package com.comerzzia.pos.services.promociones.tipos.especificos;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentException;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.cupones.CuponesServiceException;
import com.comerzzia.pos.services.cupones.CuponesServices;
import com.comerzzia.pos.services.cupones.generation.GeneratedCouponDto;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.filtro.FiltroLineasPromocion;
import com.comerzzia.pos.services.promociones.filtro.LineasAplicablesPromoBean;
import com.comerzzia.pos.services.promociones.tipos.PromocionCabecera;
import com.comerzzia.pos.services.promociones.tipos.componente.CondicionPrincipalPromoBean;
import com.comerzzia.pos.services.ticket.cupones.CuponEmitidoTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.util.config.SpringContext;

@Component
@Scope("prototype")
public class PromocionGeneracionCuponesBean extends PromocionCabecera {

    protected static final Logger log = Logger.getLogger(PromocionGeneracionCuponesBean.class);

    protected static final String TIPO_CUPON_NUMERADO = "Numerado";
    protected static final String TIPO_CUPON_FIJO = "Codigo fijo";

    protected boolean impresionManual;
    protected Integer impresionAleatoria;

    protected Integer indiceAleatorio;
    protected CondicionPrincipalPromoBean condiciones;
    
    @Autowired
    Sesion sesion;

    @Override
    public boolean aplicarPromocion(DocumentoPromocionable<IPromocionTicket> documento) {
        try {
        	log.trace("aplicarPromocion() - " + this);
            // Obtenemos las líneas aplicables según el filtro configurado
            FiltroLineasPromocion filtroLineas = createFiltroLineasPromocion(documento);
            LineasAplicablesPromoBean lineasAplicables = filtroLineas.getNumCombosCondicion(condiciones);
            if (lineasAplicables.isEmpty() && !condiciones.isVacio()) {
                log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar por no existir líneas aplicables en el documento .");
                return false;
            }

            for(int i =0;i<lineasAplicables.getNumCombos();i++){
            	// Obtenemos el index actual de cupones para esta promoción
                Integer index = sesion.getSesionPromociones().getIndexCuponPromocion(getIdPromocion());
                
                if (!index.equals(indiceAleatorio)) {
                	iteraIndiceAleatorio(index);
                    log.trace(this + " aplicarPromocion() - La promoción no se puede aplicar porque el índice aleatorio no se cumple: " + indiceAleatorio + " / " + index);
                    continue;
                }
                
                // Si el índice ya ha llegado al calculado de manera aleatoria, generamos cupones
            	CuponEmitidoTicket cupon = generaCupon(documento);
            	if(cupon == null) {
            		continue;
            	}
            	
            	documento.addCuponEmitido(cupon);
            	
            	iteraIndiceAleatorio(index);
            }

            return true;
        }
        catch (CuponesServiceException e) {
            log.error(this + " aplicarPromocion() - No se pudo aplicar promoción por error al intentar obtener contador de cupones: " + e.getMessage(), e);
            return false;
        }
    }
    
    protected void iteraIndiceAleatorio(Integer index) {
    	if(index+1<impresionAleatoria){
    		// Si el índice aún no ha llegado al calculado de manera aleatoria, incrementamos el índice
    		sesion.getSesionPromociones().addCuponPromocion(getIdPromocion());
    	}else{
    		// Borramos histórico de generación de cupones de esta promoción
        	sesion.getSesionPromociones().resetCuponPromocion(getIdPromocion());
        	
        	// Creamos un nuevo índice aleatorio
        	generaIndiceAleatorio();
    	}
	    
    }

    @Override
    public void leerDatosPromocion(byte[] datosPromocion) {
        try {
            XMLDocument xmlPromocion = new XMLDocument(datosPromocion);
            condiciones = (new CondicionPrincipalPromoBean(xmlPromocion.getNodo("condicionLineas")));

            String manual = (xmlPromocion.getNodo("impresionManual").getValue());
            setImpresionManual(manual != null && manual.equals("S"));
            try {
                setImpresionAleatoria(xmlPromocion.getNodo("impresionAleatoria").getValueAsInteger());
            }
            catch (NumberFormatException e) {
                setImpresionAleatoria(0);
            }
            
            leerDatosCupon(datosPromocion);

            generaIndiceAleatorio();
        }
        catch (XMLDocumentException e) {
            log.error("Error al leer los datos de la promoción de tipo generación cupones: " + e.getMessage(), e);
        }
    }

    public CuponEmitidoTicket generaCupon(DocumentoPromocionable<IPromocionTicket> documento) throws CuponesServiceException {
    	String storeLanguageCode = sesion.getAplicacion().getStoreLanguageCode();
    	GeneratedCouponDto cuponGenerado = generaCodigoCupon(documento);
    	if(cuponGenerado == null) {
    		return null;
    	}
    	CuponEmitidoTicket cupon = new CuponEmitidoTicket();
        
        String title = datosCupon.getTitulo();
        if(StringUtils.isNotBlank(storeLanguageCode)) {
        	String titleTranslation = datosCupon.getTitleTranslations().get(storeLanguageCode);
        	if(StringUtils.isNotBlank(titleTranslation)) {
        		title = titleTranslation;
        	}
        }
        cupon.setTituloCupon(title);
        
        String description = datosCupon.getDescripcion();
        if(StringUtils.isNotBlank(storeLanguageCode)) {
        	String descriptionTranslation = datosCupon.getDescriptionTranslations().get(storeLanguageCode);
        	if(StringUtils.isNotBlank(descriptionTranslation)) {
        		description = descriptionTranslation;
        	}
        }
        
        cupon.setDescripcionCupon(description);
        
        cupon.setIdPromocionOrigen(getIdPromocion());
        cupon.setIdPromocionAplicacion(datosCupon.getIdPromocionAplicacion());
        
        
		cupon.setCodigoCupon(cuponGenerado.getCouponCode());
		cupon.setImporteCupon(cuponGenerado.getCouponAmount());
		cupon.setFechaInicio(cuponGenerado.getStartDate());
		cupon.setFechaFin(cuponGenerado.getEndDate());
		cupon.setMaximoUsos(datosCupon.getCustomerMaxUses());
		cupon.setImagenCupon(datosCupon.getUrlImage());
		cupon.setTipoCupon(datosCupon.getCouponTypeCode());
        return cupon;
    }

    protected void generaIndiceAleatorio() {
        if (getImpresionAleatoria() != null && getImpresionAleatoria() > 0) {
            indiceAleatorio = new Random().nextInt(getImpresionAleatoria()); //TODO nextInt devuelve 'exclusive'. ¿está esto correcto?
        }
        else {
            indiceAleatorio = 0;
        }
    }

    protected GeneratedCouponDto generaCodigoCupon(DocumentoPromocionable<IPromocionTicket> documento) throws CuponesServiceException {
    	CuponesServices cuponesServices = SpringContext.getBean(CuponesServices.class);
        return cuponesServices.getCodigoCuponAutogenerado(getIdPromocion(), datosCupon.getIdPromocionAplicacion(), documento);
    }

    public boolean isImpresionManual() {
		return impresionManual;
	}

	public void setImpresionManual(boolean impresionManual) {
		this.impresionManual = impresionManual;
	}

	public Integer getImpresionAleatoria() {
        return impresionAleatoria;
    }

    public void setImpresionAleatoria(Integer impresionAleatoria) {
        this.impresionAleatoria = impresionAleatoria;
    }

    @Override
	public boolean isAplicacionFinal() {
		return true;
	}
    
	@Override
	public boolean isAplicacionCabecera() {
		return false;
	}

	@Override
	public boolean isAplicacionGeneracionCupon() {
		return true;
	}

    
}
