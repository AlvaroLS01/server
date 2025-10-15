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

package com.comerzzia.pos.services.core.sesion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.persistence.fidelizacion.CustomerCouponDTO;
import com.comerzzia.pos.persistence.promociones.PromocionBean;
import com.comerzzia.pos.services.core.sesion.coupons.application.CouponsApplicationResultDTO;
import com.comerzzia.pos.services.core.sesion.coupons.types.CouponTypeDTO;
import com.comerzzia.pos.services.core.sesion.promotions.PromotionsSessionKey;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.cupones.CuponAplicationException;
import com.comerzzia.pos.services.cupones.CuponUseException;
import com.comerzzia.pos.services.cupones.CuponesServiceException;
import com.comerzzia.pos.services.cupones.CuponesServices;
import com.comerzzia.pos.services.cupones.generation.GeneratedCouponDto;
import com.comerzzia.pos.services.promociones.ComparadorTipoPromocion;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.promociones.Promocion;
import com.comerzzia.pos.services.promociones.PromocionesBuilder;
import com.comerzzia.pos.services.promociones.PromocionesService;
import com.comerzzia.pos.services.promociones.PromocionesServiceException;
import com.comerzzia.pos.services.promociones.tipos.DatosCuponFuturoDTO;
import com.comerzzia.pos.services.promociones.tipos.PromocionLinea;
import com.comerzzia.pos.services.promociones.tipos.especificos.PromocionGeneracionCuponesBean;
import com.comerzzia.pos.services.promociones.tipos.especificos.PromocionPrecioDetalles;
import com.comerzzia.pos.services.promociones.tipos.especificos.detalles.DetallePromocion;
import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.TicketVentaAbono;
import com.comerzzia.pos.services.ticket.cabecera.CabeceraTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponEmitidoTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.format.FormatUtil;
import com.comerzzia.pos.util.i18n.I18N;
import com.comerzzia.pos.util.math.Permutaciones;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import lombok.Synchronized;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SesionPromociones {

    protected static final Logger log = Logger.getLogger(SesionPromociones.class.getName());
    
    /* APIV2 loaded, shows if exists a connection with it */
    protected Boolean loadedLoyaltyModule = false;

    protected final Map<Long, Promocion> promocionesActivas;
    protected final Map<Long, PromocionGeneracionCuponesBean> promocionesActivasEmisionCuponManual;
    protected final Map<String, Promocion> promocionesActivasPorCuponFijo;
    protected final Map<String, Promocion> promocionesActivasPorCuponFijoVariable;
    protected final Map<Long, Promocion> promocionesActivasPorCuponNumerado;
    
    protected final Set<Long> promotionsActiveOnlyByCoupon;
    
    /*Máximo de promociones en conflicto permitidas*/
    private static final Integer MAX_PROMOCIONES_EN_CONFLICTO = 7;
    
    /*Tipos de aplicación*/
    public static final String APLICACION_FINAL = "final";
    public static final String APLICACION_CABECERA = "cabecera";
    public static final String APLICACION_PRECIO = "precio";
    public static final String APLICACION_LINEA = "linea";
    public static final String APLICACION_GENERACION_CUPONES = "cupones";
    /*Maps para clasificar promociones por tipo de aplicación*/
    protected final Map<Long, Promocion> promocionesFinales;
    protected final Map<Long, Promocion> promocionesFinalesExclusivas;
    protected final Map<Long, Promocion> promocionesCabecera;
    protected final Map<Long, Promocion> promocionesCabeceraExclusiva;
    protected final Map<Long, Promocion> promocionesCabeceraImporte;
    protected final Map<Long, Promocion> promocionesCabeceraImporteExclusiva;
    protected final Map<Long, PromocionLinea> promocionesLineas;
    protected final Map<Long, PromocionLinea> promocionesLineasExclusivas;
    protected final Map<Long, PromocionPrecioDetalles> promocionesPrecio;
    protected final Map<Long, PromocionPrecioDetalles> promocionesPrecioExclusivas;
    protected final Map<Long, Promocion> promocionesAplicacionCupon;
    protected final Map<Long, Promocion> promocionesAplicacionCuponExclusiva;
    
    protected final Map<Long, Integer> cuponesPromocion;
    
    protected Map<String, CouponTypeDTO> couponsType;
    
    @Autowired
    protected CuponesServices cuponesServices;
    
    @Autowired
    protected PromocionesService promocionesService;

    @Autowired
    protected VariablesServices variablesService;
    
    @Autowired
    protected PromocionesBuilder promocionesBuilder;
    
//    @Autowired
//    private ComerzziaApiManager apiManager;
    
    protected PromotionsSessionKey promotionsSessionKey;
    
    protected Integer promotionsVersion = 0;
    
    protected Boolean PROMOCIONES_APLICAR_PROMO_LINEAS_SOBRE_PROMO_PRECIO;
    
    public SesionPromociones(PromotionsSessionKey promotionsSessionKey) {
    	this.promotionsSessionKey = promotionsSessionKey;
    	
        promocionesActivas = new TreeMap<Long, Promocion>();
        promocionesActivasPorCuponFijo = new TreeMap<String, Promocion>();
        promocionesActivasPorCuponFijoVariable = new TreeMap<String, Promocion>();
        promocionesActivasPorCuponNumerado = new TreeMap<Long, Promocion>();
        promocionesFinales = new TreeMap<Long, Promocion>();
        promocionesFinalesExclusivas = new TreeMap<Long, Promocion>();
        promocionesCabecera = new TreeMap<Long, Promocion>();
        promocionesCabeceraExclusiva = new TreeMap<Long, Promocion>();
        promocionesCabeceraImporte = new TreeMap<Long, Promocion>();
        promocionesCabeceraImporteExclusiva = new TreeMap<Long, Promocion>();
        promocionesLineas = new TreeMap<Long, PromocionLinea>();
        promocionesLineasExclusivas = new TreeMap<Long, PromocionLinea>();
        cuponesPromocion = new TreeMap<Long, Integer>();
        promocionesActivasEmisionCuponManual = new TreeMap<Long, PromocionGeneracionCuponesBean>();
        promocionesPrecio = new TreeMap<Long, PromocionPrecioDetalles>();
        promocionesPrecioExclusivas = new TreeMap<Long, PromocionPrecioDetalles>();
        promocionesAplicacionCupon = new TreeMap<Long, Promocion>();
        promocionesAplicacionCuponExclusiva = new TreeMap<Long, Promocion>();
        
        promotionsActiveOnlyByCoupon = new HashSet<>();
    }

    /** Cada vez que iniciamos una nueva venta se llama a actualizarPromocionesActivas.
     Esto consulta todas las promociones. Aquellas cuya versión haya variado, son recargadas de 
     nuevo completamente (sus detalles y XML). Las que no, se quedan tal cual.
     */
    @Synchronized
    public void actualizarPromocionesActivas() throws PromocionesServiceException {
        if(couponsType == null) {
        	readCouponsTypeFile();
        }
                        
        // query promotions version changes
    	Integer newPromotionsVersion = promocionesService.checkPromotionsChange(promotionsSessionKey);
    	
    	// no promotions changes
    	if (newPromotionsVersion == null || promotionsVersion.compareTo(newPromotionsVersion) == 0) {
    		log.debug("actualizarPromocionesActivas() - No promotions changes detected");
    		return;
    	}
    		    	
	    promotionsVersion = newPromotionsVersion;
	    
	    log.info("actualizarPromocionesActivas() - Updating active promotions to version: " + promotionsVersion + " for key " + promotionsSessionKey);
	    	        
        // Primero desactivamos todas las promociones que tenemos cargadas
        for (Promocion promocion : promocionesActivas.values()) {
            promocion.desactivar();
        }
        
        // Consultamos todas las promociones activas en base de datos
        List<PromocionBean> promoActivas = promocionesService.consultarPromocionesActivas(promotionsSessionKey);
                
        for (PromocionBean promoActiva : promoActivas) {
            // Vemos si ya teníamos cargada dicha promoción con anterioridad
        	try {
	            Promocion promocion = promocionesActivas.get(promoActiva.getIdPromocion());
	            // Si no teníamos la promoción, o si la promoción ha variado, la recargamos
	            if (promocion == null || promocion.getVersionTarifa() < promoActiva.getVersionTarifa()) {
					promocion = promocionesBuilder.create(promoActiva, promotionsSessionKey.getStoreLanguageCode());
	                promocionesActivas.put(promocion.getIdPromocion(), promocion);
	                addPromocionActiva(promocion.getIdPromocion(), promocion);
	            }
	            // Activamos la promoción
	            promocion.activar();
        	} catch (Exception e) { // Normalmente PromocionBuilderException				
				String msg = null;
				if (promoActiva != null && promoActiva.getIdPromocion() != null) {
					if (!StringUtils.isBlank(promoActiva.getDescripcion())) {
						msg = I18N.getTexto("La promoción {0}-\"{1}\" no ha podido cargarse porque no es está definida correctamente", promoActiva.getIdPromocion(), promoActiva.getDescripcion());
					} else {
						msg = I18N.getTexto("La promoción {0} no ha podido cargarse porque no es está definida correctamente", promoActiva.getIdPromocion());
					}
				} else {
					msg = I18N.getTexto("Una promoción no ha podido cargarse porque no es está definida correctamente");
				}
				
				log.error("actualizarPromocionesActivas() - " + msg);
				log.error("actualizarPromocionesActivas() - " + e.getClass().getName() + " - " + e.getLocalizedMessage(), e);
			}
        }
        log.debug("actualizarPromocionesActivas() - Active promotions (" + promoActivas.size() + ") updated");
    }
    
	protected void readCouponsTypeFile() {
    	try {
    		couponsType = new HashMap<String, CouponTypeDTO>();
    		
    		String filePath = "entities/ly_coupons_types_tbl.json";
    		log.debug("readCouponsTypeFile() - Reading file " + filePath);
    		
	    	Method method = (java.lang.Thread.class).getMethod("getContextClassLoader", (Class<?>[]) null);
			ClassLoader classLoader = (ClassLoader) method.invoke(Thread.currentThread(), (Object[]) null);
			URL url = classLoader.getResource(filePath);
			
			loadedLoyaltyModule = true;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            
            String json = "";
            String line = "";
            while ((line = in.readLine()) != null) {
                json = json + System.lineSeparator() + line;
            }
            in.close();
			
			List<CouponTypeDTO> coupons = readJson(json.getBytes());
			
			for(CouponTypeDTO couponType : coupons) {
				couponsType.put(couponType.getCouponTypeCode(), couponType);
			}
    	}
    	catch (Exception e) {
    		loadedLoyaltyModule = false;
    		log.warn("readCouponsTypeFile() - No se ha encontrado el fichero ly_coupons_types_tbl.json: " + e.getMessage());
    	}
		
	}
    
	public <T> T readJson(byte[] json) {
		if (json == null) {
			return null;
		}
		ObjectMapper objectMapper = new ObjectMapper();
		// jaxb annotations support
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		objectMapper.registerModule(module);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return objectMapper.readValue(new String(json), new TypeReference<List<CouponTypeDTO>>(){});
		}
		catch (JsonParseException | JsonMappingException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected void addPromocionActiva(Long idPromocion, Promocion promocion){
        if (PROMOCIONES_APLICAR_PROMO_LINEAS_SOBRE_PROMO_PRECIO == null) {
            PROMOCIONES_APLICAR_PROMO_LINEAS_SOBRE_PROMO_PRECIO = variablesService.getVariableAsBoolean(VariablesServices.PROMOCIONES_APLICAR_PROMO_LINEAS_SOBRE_PROMO_PRECIO);
        }
        if (promocion.isAplicacionFinal() && promocion.isExclusiva()) {
            promocionesFinalesExclusivas.put(promocion.getIdPromocion(), promocion);
        }
        else if (promocion.isAplicacionFinal() && !promocion.isExclusiva()) {
            promocionesFinales.put(promocion.getIdPromocion(), promocion);
        }

        if (promocion.isAplicacionCupon() && promocion.isExclusiva()) {
            promocionesAplicacionCuponExclusiva.put(promocion.getIdPromocion(), promocion);
        }
        else if (promocion.isAplicacionCupon() && !promocion.isExclusiva()) {
            promocionesAplicacionCupon.put(promocion.getIdPromocion(), promocion);
        }
        else if (promocion.isAplicacionCabecera() && !promocion.isImporte() && promocion.isExclusiva()) {
            promocionesCabeceraExclusiva.put(promocion.getIdPromocion(), promocion);
        }
        else if(promocion.isAplicacionCabecera() && promocion.isImporte() && promocion.isExclusiva()) {
            promocionesCabeceraImporteExclusiva.put(promocion.getIdPromocion(), promocion);
        }
        else if (promocion.isAplicacionCabecera() && !promocion.isImporte() && !promocion.isExclusiva()) {
            promocionesCabecera.put(promocion.getIdPromocion(), promocion);
        }
        else if(promocion.isAplicacionCabecera() && promocion.isImporte() && !promocion.isExclusiva()) {
            promocionesCabeceraImporte.put(promocion.getIdPromocion(), promocion);
        }
        else if ((promocion.isAplicacionPrecio() && PROMOCIONES_APLICAR_PROMO_LINEAS_SOBRE_PROMO_PRECIO && promocion.isExclusiva())) {
            promocionesPrecioExclusivas.put(promocion.getIdPromocion(), (PromocionPrecioDetalles) promocion);
        }
        else if ((promocion.isAplicacionPrecio() && PROMOCIONES_APLICAR_PROMO_LINEAS_SOBRE_PROMO_PRECIO && !promocion.isExclusiva())) {
            promocionesPrecio.put(promocion.getIdPromocion(), (PromocionPrecioDetalles) promocion);
        }
        else if ((promocion.isAplicacionLinea() && promocion.isExclusiva())) {
            promocionesLineasExclusivas.put(promocion.getIdPromocion(), (PromocionLinea) promocion);
        }
        else if (promocion.isAplicacionLinea() && !promocion.isExclusiva()) {
            promocionesLineas.put(promocion.getIdPromocion(), (PromocionLinea) promocion);
        }
        
        if (promocion.isAplicacionGeneracionCupon() && ((PromocionGeneracionCuponesBean) promocion).isImpresionManual()) {
            promocionesActivasEmisionCuponManual.put(promocion.getIdPromocion(), (PromocionGeneracionCuponesBean) promocion);
        }
        
    	//Se clasifican según su Tipo de Aplicación
		if (promocion.isActivaPorCupon()) {
		    promotionsActiveOnlyByCoupon.add(promocion.getIdPromocion());   
	        
		    if (promocion.isActivaPorCuponFijoVariable()) {    		
    			promocionesActivasPorCuponFijoVariable.put(promocion.getCupon().replaceAll("%", "(.*)"), promocion);
    		} else if (promocion.isActivaPorCuponFijo()) {
    			promocionesActivasPorCuponFijo.put(promocion.getCupon(), promocion);
    		} else if (promocion.isActivaPorCuponNumerado()) {
    			promocionesActivasPorCuponNumerado.put(promocion.getIdPromocion(), promocion);
    		} else if(promocion.isAplicacionCupon()) {
    			promocionesActivasPorCuponNumerado.put(promocion.getIdPromocion(), promocion);
    		}
		}
	}
    	
	public void aplicarPromociones(DocumentoPromocionable<IPromocionTicket> documento) {
		
        // Reseteamos promociones
		cleanPromotions(documento);
        
        // Aplicamos promociones
        applyPromotions(documento);
        
        //Añadimos cupones cuya promoción se haya aplicado
        addAppliedCoupons(documento);
  
    }

    public void aplicarPromocionesFinales(DocumentoPromocionable<IPromocionTicket> documento) {
    	documento.getCuponesEmitidos().clear();
    	
    	// build promotions to exclude
        Set<Long> excludedPromotions = buildExcludedPromotions(documento);
        
    	aplicarPromocionesFinales(documento, filterExcludedPromotions(promocionesFinalesExclusivas, excludedPromotions));
    	aplicarPromocionesFinales(documento, filterExcludedPromotions(promocionesFinales, excludedPromotions));
    }


    protected void aplicarPromocionesFinales(DocumentoPromocionable<IPromocionTicket> documento, Collection<Promocion> promoCandidatas) {
        for (Promocion promocion : promoCandidatas) {
            if (!promocion.isAplicable(documento)) {
                log.debug(promocion + " aplicarPromocionesFinales() - Promoción no aplicable por condiciones generales de Activación y Fecha/Hora");
                continue; // La promoción no es aplicable y la descartamos
            }
            try {
               promocion.aplicarPromocion(documento);
            } catch (Exception e) {
            	log.error("aplicarPromocionesFinales() Error aplicando promoción " + promocion.getIdPromocion() + ": " + e.getMessage(), e);
            }
        }
    }
    
    protected void aplicarPromocionesCabecera(DocumentoPromocionable<IPromocionTicket> documento, Collection<Promocion> promoCandidatas) {
        Collection<Promocion> promociones = promoCandidatas;
        List<Promocion> promocionesOrdenadas = new ArrayList<>(promociones);
        Collections.sort(promocionesOrdenadas, new ComparadorTipoPromocion());
		for (Promocion promocion : promociones) {
            if (!promocion.isAplicable(documento)) {
                log.trace(promocion + " aplicarPromociones() - Promoción no aplicable por condiciones generales de Activación y Fecha/Hora");
                continue;
            }
            try {
               promocion.aplicarPromocion(documento);
            } catch (Exception e) {
            	log.error("aplicarPromocionesCabecera() Error aplicando promoción " + promocion.getIdPromocion() + ": " + e.getMessage(), e);
            }
        }
    }
    
    protected void aplicarPromocionesPrecio(DocumentoPromocionable<IPromocionTicket> documento, Collection<PromocionPrecioDetalles> promoCandidatas){
        for (LineaDocumentoPromocionable lineaTicket : documento.getLineasDocumentoPromocionable()) {
	        if (!lineaTicket.isAdmitePromociones()) {
		        continue;
	        }
        	if (lineaTicket.tieneDescuentoManual()){
            	continue;
            }
            if (lineaTicket.tieneCambioPrecioManual()){ 
            	continue;
            }
        	DetallePromocion detallePromocionPrecioOptima = null;
        	for (PromocionPrecioDetalles promocion : promoCandidatas) {
		        if (!promocion.isAplicable(documento)) {
			        log.trace("aplicarPromocionesPrecio() - Promoción no aplicable por condiciones generales de activación y cabecera - " + promocion);
			        continue;
		        }else{
			        log.trace("aplicarPromocionesPrecio() - Promoción aplicable - " + promocion);
		        }

		        try {
	            	DetallePromocion detalleCandidato = promocion.getDetalleAplicable(lineaTicket.getCodArticulo(), lineaTicket.getDesglose1(), lineaTicket.getDesglose2());
	            	if (detalleCandidato == null || !promocion.isAplicable(documento) ||  !detalleCandidato.isAplicable()){ // el artículo no está registrado en esta promoción
	            		continue;
	            	}
	            	if (detallePromocionPrecioOptima == null // por ahora no tenemos otro candidato
	            			|| BigDecimalUtil.isMenor(detalleCandidato.getPrecioVenta(), detallePromocionPrecioOptima.getPrecioVenta())){ // el candidato es mejor que el anterior 
	            		detallePromocionPrecioOptima = detalleCandidato;
	            	}            	
	            } catch (Exception e) {
	            	log.error("aplicarPromocionesPrecio() Error aplicando promoción " + promocion.getIdPromocion() + ": " + e.getMessage(), e);
	            }
            }
        	if (detallePromocionPrecioOptima != null){
        		((PromocionPrecioDetalles)detallePromocionPrecioOptima.getPromocion()).aplicaPromocionPrecioLinea(documento, lineaTicket);
        	}
        }
    }

    protected void aplicarPromocionesLineasOptimas(DocumentoPromocionable<IPromocionTicket> documento, Collection<PromocionLinea> promoCandidatas){
        if (promoCandidatas.isEmpty()) {
            return;
        }
        // Primero realizamos una pasada para obtener todas las promociones candidatas de aplicación en cada línea de documento
        for (PromocionLinea promocion : promoCandidatas) {
            if (!promocion.isAplicable(documento)) {
                log.trace("aplicarPromocionesLineasOptimas() - Promoción no aplicable por condiciones generales de activación y cabecera - " + promocion);
                continue;
            }else{
            	log.trace("aplicarPromocionesLineasOptimas() - Promoción aplicable - " + promocion);
            }
            
            try {
               promocion.analizarLineasAplicables(documento);
            } catch (Exception e) {
            	log.error("aplicarPromocionesLineasOptimas() Error analizando promoción " + promocion.getIdPromocion() + ": " + e.getMessage(), e);
            }            
        }
        
        Set<PromocionLineaCandidataTicket> promoCandidatasSinConflicto = new HashSet<>();
        Set<PromocionLineaCandidataTicket> promoCandidatasConConflicto = new HashSet<>();
        
        // Ahora recorremos cada línea de documento para ver sus promociones candidatas de aplicación
        for (LineaDocumentoPromocionable lineaTicket : documento.getLineasDocumentoPromocionable()) {
        	if (!lineaTicket.isAdmitePromociones()) {
		        continue;
	        }
            // Si la línea no tiene promociones candidatas, la ignoramos
            if (!lineaTicket.tienePromocionesAplicables()){
                continue;
            }
            // Si tiene más de una promoción candidata, las añadimos a promociones en conflicto
            if (lineaTicket.getPromocionesAplicables().size()>1) { 
                for (PromocionLineaCandidataTicket promoCandidata : lineaTicket.getPromocionesAplicables()) {
                    promoCandidatasConConflicto.add(promoCandidata);
                }
            }
            else{ // Si sólo tiene una, la añadimos como promoción candidata sin conflicto
                promoCandidatasSinConflicto.add(lineaTicket.getPromocionesAplicables().get(0));
            }
        }
        // Si tenemos promociones en conflicto, las tratamos para ver cuáles aplicar
        if (!promoCandidatasConConflicto.isEmpty()){
            tratarPromocionesEnConflicto(documento, promoCandidatasConConflicto);
        }
        // Aplicamos promociones sin conflicto
        for (PromocionLineaCandidataTicket candidata : promoCandidatasSinConflicto) {
        	try {
               candidata.aplicarPromocion(documento);
            } catch (Exception e) {
            	log.error("aplicarPromocionesLineasOptimas() Error aplicando promoción " + candidata.getPromocion().getIdPromocion() + ": " + e.getMessage(), e);
            }                  
        }
        
        for (LineaDocumentoPromocionable linea :  documento.getLineasDocumentoPromocionable()){
            linea.resetPromocionesAplicables();
        }
        
    }
    
    protected void tratarPromocionesEnConflicto(DocumentoPromocionable<IPromocionTicket> documento,	Set<PromocionLineaCandidataTicket> promoCandidatasConConflicto) {
    	// Comprobamos si las promociones con conflicto son superiores al máximo permitido
		if (promoCandidatasConConflicto.size() > MAX_PROMOCIONES_EN_CONFLICTO){
			log.error("aplicarPromocionesLineasOptimas() - Las promociones en conflicto de aplicación (" + promoCandidatasConConflicto.size() + ") son superiores a las permitidas: " + MAX_PROMOCIONES_EN_CONFLICTO);
			String infoConflictos = "Promociones en conflicto: ";
		    for (LineaDocumentoPromocionable lineaTicket : documento.getLineasDocumentoPromocionable()) {
		        if (lineaTicket.getPromocionesAplicables().size()>1) { 
		        	infoConflictos += "\n\t Artículo: " + lineaTicket.getCodArticulo() + " // Promociones: ";
		        	for (PromocionLineaCandidataTicket promoCandidata : lineaTicket.getPromocionesAplicables()) {
		            	infoConflictos += promoCandidata.getPromocion().getIdPromocion() + " ";;
		            }
		        }
		    }
			log.error("aplicarPromocionesLineasOptimas() - " + infoConflictos);
			log.error("aplicarPromocionesLineasOptimas() - No se aplicará ninguna promoción en conflicto");
		}
		else{
			List<PromocionLineaCandidataTicket> promoConflictos = new ArrayList<>();
	        promoConflictos.addAll(promoCandidatasConConflicto);
	        // Probamos con todas las permutaciones posibles de promociones en conflicto
	        Permutaciones permutaciones = new Permutaciones(promoConflictos.size());
	        permutaciones.permutar();
	        BigDecimal ahorroMejor = ((CabeceraTicket) documento.getCabecera()).getTotales().getTotalAPagar();
	        List<Integer> permutacionMejor = null;
	        for (List<Integer> permutacion : permutaciones.getPermutaciones()) {
	            BigDecimal ahorroPermutacion = BigDecimal.ZERO;
	            for (Integer index : permutacion) {
	                PromocionLineaCandidataTicket candidata = promoConflictos.get(index);
	                BigDecimal ahorro = candidata.calcularPromocionCandidata();
	                ahorroPermutacion = ahorroPermutacion.add(ahorro);
	            }
	            
	            BigDecimal ahorroPermutacionTotal = ((CabeceraTicket) documento.getCabecera()).getTotales().getTotalAPagar().subtract(ahorroPermutacion);
	            
	            if (BigDecimalUtil.isMenor(ahorroPermutacionTotal, ahorroMejor)){
	                ahorroMejor = ahorroPermutacionTotal;
	                permutacionMejor = permutacion;
	            }
	            
	            for (LineaDocumentoPromocionable linea : documento.getLineasDocumentoPromocionable()){
	                linea.resetPromocionesCandidatas();
	            }
	        }
	        // Aplicamos la mejor permutación si tenemos alguna válida (es posible que ninguna permutación de un ahorro positivo)
	        if (permutacionMejor != null){
	            for (Integer index : permutacionMejor) {
	                PromocionLineaCandidataTicket candidata = promoConflictos.get(index);
	                
	                try {
	                   candidata.aplicarPromocion(documento);
	                } catch (Exception e) {
	                	log.error("aplicarPromocionesLineasOptimas() Error aplicando promoción " + candidata.getPromocion().getIdPromocion() + ": " + e.getMessage(), e);
	                }   
	            }
	        }
		}
    	
    }
    
    public void addCuponPromocion(Long idPromocion) {
        Integer index = getIndexCuponPromocion(idPromocion);
        cuponesPromocion.put(idPromocion, ++index);
    }

    public void resetCuponPromocion(Long idPromocion) {
        cuponesPromocion.remove(idPromocion);
    }

    public Integer getIndexCuponPromocion(Long idPromocion) {
        Integer index = cuponesPromocion.get(idPromocion);
        if (index == null) {
            index = 0;
        }
        return index;
    }
    
    public CouponsApplicationResultDTO applyCoupons(List<CustomerCouponDTO> coupons, DocumentoPromocionable<IPromocionTicket> ticket, boolean validate) {
    	CouponsApplicationResultDTO result = new CouponsApplicationResultDTO();
    	
    	for(CustomerCouponDTO coupon : coupons) {
    		try {
    			log.debug("applyCoupons() - Trying to apply coupon with code " + coupon.getCouponCode());
    			boolean applied = aplicarCupon(coupon, ticket);
    			
    			if(applied) {
    				result.addAppliedCoupon(coupon);
    			}
    			else {
    				result.addErrorCoupon(coupon);
    			}
    		}
    		catch(Exception e) {
    			log.error("applyCoupons() - Error: " + e.getMessage(), e);
    			
    			result.addErrorCoupon(coupon);
    		}
    	}
    	
    	return result;
    }
    
	public boolean aplicarCupon(CustomerCouponDTO coupon, DocumentoPromocionable<IPromocionTicket> ticket) throws CuponUseException, CuponesServiceException, CuponAplicationException {
    	// Comprobamos si el cupón no ha sido utilizado en el documento actual
		String couponCode = coupon.getCouponCode();
        CuponAplicadoTicket cuponAplicado = ticket.getCuponAplicado(couponCode);
        if (cuponAplicado != null){
            log.warn("aplicarCupon()- Ya hay un cupón aplicado");
            throw new CuponUseException();
        }
        
//        Long customerId = ticket.getDatosFidelizado() != null ? ticket.getDatosFidelizado().getIdFidelizado() : null;
//		if (customerId != null && coupon.isValidationRequired()) {
//			CouponDTO couponValidated = validateCoupon(coupon, customerId);
//			
//			if(couponValidated != null) {
//				coupon.setCouponCode(couponValidated.getCouponCode());
//				coupon.setPromotionId(couponValidated.getPromotionId());
//				coupon.setBalance(couponValidated.getBalance());
//			}
//			else {
//				log.warn("aplicarCupon()- The coupon could not be validated");
//				throw new CuponUseException();
//			}
//		}
        
        Promocion promocion = getPromocionAplicacionCupon(coupon);
        if (promocion == null){
        	log.error("aplicarCupon() - The coupon promotion does not exist.");
        	return false;
        }

        if (!promocion.isAplicable(ticket)){
            log.debug("aplicarCupon() - La promoción a la que hace referencia el cupón no ha podido ser aplicada.");
            throw new CuponAplicationException();
        }
        
        // Adding the relations between the promotions and the coupons trying to apply them.
 		ticket.addCouponAppliyingPromotion(promocion.getIdPromocion(),coupon);
 		
        // Volvemos a aplicar todas las promociones
        aplicarPromociones(ticket);
        
        if(ticket.getCuponAplicado(couponCode) == null) {
        	log.debug("aplicarCupon() - Cupón NO aplicado");
            throw new CuponAplicationException();
        }
        
        return true;
    }
    

//	protected CouponDTO validateCoupon(CustomerCouponDTO coupon, Long customerId) throws ApiClientException {
//		try {
//			DatosSesionBean datosSesion = new DatosSesionBean();
//			datosSesion.setUidActividad(promotionsSessionKey.getUidActividad());
//			datosSesion.setUidInstancia(promotionsSessionKey.getUidInstancia());
//			datosSesion.setLocale(new Locale(AppConfig.idioma, AppConfig.pais));
//			CouponsApi api = apiManager.getClient(datosSesion, "CouponsApi");
//			
//			CouponDTO validation = api.validateCoupon(coupon.getCouponCode(), customerId);
//			
//			return validation;
//		}
//		catch (ApiClientException e) {
//			throw e;
//		}
//		catch (Exception e) {
//			log.error("validateCoupon() - Error while validating coupon: " + e.getMessage(), e);
//			
//			return null;
//		}
//	}

	protected Promocion getPromocionAplicacionCupon(CustomerCouponDTO coupon) throws CuponUseException, CuponesServiceException {
		if(coupon.getPromotionId() != null) {
			Promocion promocion = promocionesActivas.get(coupon.getPromotionId());
			return promocion;
		}
		
        // Comprobamos si el código es un cupón fijo
        log.debug("getPromocionAplicacionCupon() - Intentando aplicar cupón con código: " + coupon.getCouponCode());
        Promocion promocion = promocionesActivasPorCuponFijo.get(coupon.getCouponCode());
        if (promocion != null) {
            log.debug("getPromocionAplicacionCupon() - El código pertenece a cupón fijo de promoción activa. Intentando aplicar promoción: " + promocion);
            return promocion;
        }

        // Comprobamos si el código es un cupón fijo con variables
        for (String  expresionRegular : promocionesActivasPorCuponFijoVariable.keySet()) {
	        if (coupon.getCouponCode().matches(expresionRegular)){
	        	promocion = promocionesActivasPorCuponFijoVariable.get(expresionRegular);
	            log.debug("getPromocionAplicacionCupon() - El código pertenece a cupón fijo (con variables) de promoción activa. Intentando aplicar promoción: " + promocion);
	            return promocion;
	        }
        }
        
        
        // Comprobamos si el código tiene formato de código autogenerado por el POS
        Long idPromocion = cuponesServices.getPromocionCodigoCuponAutogenerado(coupon.getCouponCode());
        if (idPromocion == null) {            
            idPromocion = cuponesServices.getIdPromocionAplicacionDtoFuturo(coupon.getCouponCode());
            if(idPromocion == null) {
                log.debug("getPromocionAplicacionCupon() - El código no pertenece a cupón fijo de promoción activa ni tiene formato de cupón generado por el POS.");
            	return null;
            }
        }
        log.debug("getPromocionAplicacionCupon() - El código tiene formato de cupón autogenerado para la promoción con id:  " + idPromocion);
        // Buscamos e intentamos aplicar la promoción
        promocion = promocionesActivasPorCuponNumerado.get(idPromocion);
        if (promocion == null) {
            log.debug("getPromocionAplicacionCupon() - La promoción a la que hace referencia el cupón autogenerado no existe o no está activa. ");
            return null;
        }

        return promocion;
    }

	public Map<Long, PromocionGeneracionCuponesBean> getPromocionesActivasEmisionCuponManual() {
		return promocionesActivasEmisionCuponManual;
	}
	
	public Promocion getPromocionActiva(Long idPromocion) {
		return promocionesActivas.get(idPromocion);
	}
	
	public Map<Long, Promocion> getPromocionesActivas() {
		return promocionesActivas;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
    public void generarCuponesDtoFuturo(ITicket ticket) {
		for(PromocionTicket promocion : (List<PromocionTicket>) ticket.getPromociones()) {
			if(promocion.getTipoDescuento().equals(Promocion.TIPO_DTO_A_FUTURO) && (StringUtils.isBlank(promocion.getFutureDiscountType()) || Promocion.FUTURE_DISCOUNT_COUPON.equals(promocion.getFutureDiscountType()))) {
				DatosCuponFuturoDTO datosCupon = promocion.getPromocion().getDatosCupon();
				GeneratedCouponDto cupon = null;
                try {
	                cupon = cuponesServices.getCuponDtoFuturo(ticket, promocion.getPromocion(), promocion.getImporteTotalAhorro());
                }
                catch (Exception e) {
                	log.error("generarCuponesDtoFuturo() - No se ha podido generar el código de cupón.");
                	break;
                }
				
                if(cupon != null) {
                	String storeLanguageCode = promotionsSessionKey.getStoreLanguageCode();
					CuponEmitidoTicket cuponEmitido = new CuponEmitidoTicket();
					String title = datosCupon.getTitulo();
			        if(StringUtils.isNotBlank(storeLanguageCode)) {
			        	String titleTranslation = datosCupon.getTitleTranslations().get(storeLanguageCode);
			        	if(StringUtils.isNotBlank(titleTranslation)) {
			        		title = titleTranslation;
			        	}
			        }
			        cuponEmitido.setTituloCupon(title);
				        
			        String description = datosCupon.getDescripcion();
			        if(StringUtils.isNotBlank(storeLanguageCode)) {
			        	String descriptionTranslation = datosCupon.getDescriptionTranslations().get(storeLanguageCode);
			        	if(StringUtils.isNotBlank(descriptionTranslation)) {
			        		description = descriptionTranslation;
			        	}
			        }
					
					if(StringUtils.isNotBlank(description)) {
						description = description.replaceAll("#IMPORTE#", FormatUtil.getInstance().formateaImporte(promocion.getImporteTotalAhorro()));
						cuponEmitido.setDescripcionCupon(description);
					}
					
					cuponEmitido.setCodigoCupon(cupon.getCouponCode());
					cuponEmitido.setIdPromocionOrigen(promocion.getIdPromocion());
					cuponEmitido.setIdPromocionAplicacion(datosCupon.getIdPromocionAplicacion());
					cuponEmitido.setImporteCupon(cupon.getCouponAmount());
					cuponEmitido.setFechaInicio(cupon.getStartDate());
					cuponEmitido.setFechaFin(cupon.getEndDate());
					cuponEmitido.setMaximoUsos(datosCupon.getCustomerMaxUses());
					cuponEmitido.setImagenCupon(datosCupon.getUrlImage());
					cuponEmitido.setTipoCupon(datosCupon.getCouponTypeCode());
					
					((TicketVentaAbono) ticket).addCuponEmitido(cuponEmitido);
                }
			}
		}
    }
	
	public CouponTypeDTO getCouponTypeDTO(String couponTypeCode) {		
		return couponsType.get(couponTypeCode);
	}
	
	public CouponTypeDTO getCouponTypeByPrefix(String couponCode) {
		if(couponsType != null) {
			for(CouponTypeDTO couponType : couponsType.values()) {
				if(StringUtils.startsWith(couponCode, couponType.getPrefix())) {
					return couponType;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Indicates if the code is a coupon (according to its prefix) or an access code.
	 * @param code Code to be evaluated
	 * @return true if is a coupon, false if it is not
	 */
	public boolean isCoupon(String code) {
		boolean isCoupon = isCouponWithPrefix(code);
		if(!isCoupon) {
			isCoupon = isAccessCode(code);
		}
		return isCoupon;
	}

	/**
	 * Indicates if the code is a coupon according to its prefix.
	 * @param code Code to be evaluated
	 * @return true if is a coupon, false if it is not
	 */
	public boolean isCouponWithPrefix(String code) {
		boolean isCoupon = false;
		
		if(couponsType != null) {
			for(CouponTypeDTO couponType : couponsType.values()) {
				if(StringUtils.startsWith(code, couponType.getPrefix())) {
					isCoupon = true;
					break;
				}
			}
		}
		
		return isCoupon;
	}
	
	/**
	 * Indicates if the code is a access code.
	 * @param code Code to be evaluated
	 * @return true if is a coupon, false if it is not or there is an error
	 */
	private boolean isAccessCode(String code) {
		try {
			CustomerCouponDTO coupon = new CustomerCouponDTO(code, false);
			return getPromocionAplicacionCupon(coupon) != null;
		}
		catch (Exception e) {
			log.error("isAccessCode() - Error while try to get promotion from coupon: " + e.getMessage(), e);
			return false;
		}
	}

	public Boolean isLoadedLoyaltyModule() {
		return loadedLoyaltyModule;
	}
	
	protected void cleanPromotions(DocumentoPromocionable<IPromocionTicket> documento) {
		documento.getPromociones().clear();
        documento.resetPuntos();
    	documento.getCuponesAplicados().clear();

        for (LineaDocumentoPromocionable lineaTicket : documento.getLineasDocumentoPromocionable()) {
            lineaTicket.resetPromociones();
            lineaTicket.resetPromocionesAplicables();
            lineaTicket.resetPromocionesCandidatas();
        }
        
        documento.getCabecera().getTotales().recalcular();
	}
		
	protected void applyPromotions(DocumentoPromocionable<IPromocionTicket> documento) {
	    // build promotions to exclude
        Set<Long> excludedPromotions = buildExcludedPromotions(documento);
	    
		// Aplicamos primero promociones de precio
        aplicarPromocionesPrecio(documento, filterExcludedPromotions(promocionesPrecioExclusivas, excludedPromotions));
        aplicarPromocionesPrecio(documento, filterExcludedPromotions(promocionesPrecio, excludedPromotions));
        
        // Aplicamos de forma óptima promociones de línea
        aplicarPromocionesLineasOptimas(documento, filterExcludedPromotions(promocionesLineasExclusivas, excludedPromotions));
        aplicarPromocionesLineasOptimas(documento, filterExcludedPromotions(promocionesLineas, excludedPromotions));

        // Aplicamos promociones de cabecera
        aplicarPromocionesCabecera(documento, filterExcludedPromotions(promocionesCabeceraExclusiva, excludedPromotions));
        aplicarPromocionesCabecera(documento, filterExcludedPromotions(promocionesCabecera, excludedPromotions));

        // Aplicamos promociones de cabecera
        aplicarPromocionesCabecera(documento, filterExcludedPromotions(promocionesCabeceraImporteExclusiva, excludedPromotions));
        aplicarPromocionesCabecera(documento, filterExcludedPromotions(promocionesCabeceraImporte, excludedPromotions));

        // Aplicamos promociones de aplicación de cupones
        aplicarPromocionesCabecera(documento, filterExcludedPromotions(promocionesAplicacionCupon, excludedPromotions));
        aplicarPromocionesCabecera(documento, filterExcludedPromotions(promocionesAplicacionCuponExclusiva, excludedPromotions));
	}
	
	protected <T extends Promocion> Collection<T> filterExcludedPromotions(Map<Long, T> promotions, Set<Long> excludedPromotions) {
        if ((promotions.size() == 0) || (excludedPromotions.size() == 0)) return promotions.values();
        
        return promotions.entrySet().stream()
                .filter(e -> !excludedPromotions.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
	
   protected Set<Long> buildExcludedPromotions(DocumentoPromocionable<IPromocionTicket> documento) {
        // initialize with promotions accesible only by coupon
        Set<Long> excludedPromotions = new HashSet<>(promotionsActiveOnlyByCoupon);
        
        // remove the promotions from candidate coupons
        if(documento.getMapCouponsAppliyingPromotion() != null) {
            excludedPromotions.removeAll(documento.getMapCouponsAppliyingPromotion().keySet());
        }
        
        return excludedPromotions;
    }
	
	protected void addAppliedCoupons(DocumentoPromocionable<IPromocionTicket> documento) {
		for(IPromocionTicket appliedPromotion : documento.getPromociones()) {
		    if (promotionsActiveOnlyByCoupon.contains(appliedPromotion.getIdPromocion())) {
                
		        List<CustomerCouponDTO> candidateCoupons = documento.getMapCouponsAppliyingPromotion().get(appliedPromotion.getIdPromocion());
		        
		        if (candidateCoupons != null && candidateCoupons.size() > 0) {
                    CuponAplicadoTicket cupon = new CuponAplicadoTicket(appliedPromotion.getCodAcceso(), getPromocionActiva(appliedPromotion.getIdPromocion()));
                    cupon.setTextoPromocion(appliedPromotion.getTextoPromocion());
                    cupon.setImporteTotalAhorrado(appliedPromotion.getImporteTotalAhorro());
                    
                    documento.addCuponAplicado(cupon);
		        }
        	}
        }
	}	

	
	public void deleteCoupon(String codigoCupon, DocumentoPromocionable<IPromocionTicket> ticket) throws CuponUseException, CuponesServiceException, CuponAplicationException {
        ticket.removeCouponsAppliyingPromotion(codigoCupon);
        Map<Long, List<CustomerCouponDTO>> promotionCouponsMap = ticket.getMapCouponsAppliyingPromotion();
        for (Map.Entry<Long, List<CustomerCouponDTO>> entry : promotionCouponsMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                promotionCouponsMap.remove(entry.getKey());             
            }
        }
    }
	
	public PromocionBean getPromotion(Long promotionId) throws PromocionesServiceException {
		return promocionesService.getPromotion(promotionsSessionKey.getUidActividad(), promotionId);
    }
	
}
