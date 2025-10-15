package com.comerzzia.pos.services.cupones;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.pos.services.core.contadores.ContadorServiceException;
import com.comerzzia.pos.services.core.contadores.ServicioContadores;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.sesion.coupons.types.CouponTypeDTO;
import com.comerzzia.pos.services.core.variables.VariablesServices;
import com.comerzzia.pos.services.cupones.generation.CouponsCodeGeneratorService;
import com.comerzzia.pos.services.cupones.generation.GeneratedCouponDto;
import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.promociones.Promocion;
import com.comerzzia.pos.services.promociones.PromocionesServiceException;
import com.comerzzia.pos.services.promociones.tipos.especificos.PromocionGeneracionCuponesBean;
import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.cupones.CuponEmitidoTicket;
import com.comerzzia.pos.util.i18n.I18N;

@Service
public class CuponesServices {

    protected static final Logger log = Logger.getLogger(CuponesServices.class);
    
    @Autowired
    Sesion sesion;
    
    @Autowired
    protected VariablesServices variablesServices;
    
    @Autowired
    protected ServicioContadores servicioContadores;
    
    @Autowired
    private CouponsCodeGeneratorService couponsCodeGeneratorService;

    @SuppressWarnings("rawtypes")
	public GeneratedCouponDto getCodigoCuponAutogenerado(Long idPromocionGeneracion, Long idPromocionAplicacion, DocumentoPromocionable documento) throws CuponesServiceException {
        try {
            Promocion promocion = sesion.getSesionPromociones().getPromocionActiva(idPromocionGeneracion);
            
            if(promocion.getDatosCupon() == null) {
            	throw new CuponesServiceException(I18N.getTexto("La promoción indicada no tiene los datos del cupón indicado."));
            }
            
            return getCuponDtoFuturo((ITicket) documento, promocion, BigDecimal.ZERO);
        }
        catch (CuponesServiceException e) {
        	throw e;
        }
        catch (Exception e) {
            String msg = "Se ha producido un error generando código de cupón autonumerado: " + e.getMessage();
            log.error("getCodigoCuponAutogenerado() - " + msg, e);
            throw new CuponesServiceException(e);
        }
    }

    public Long getPromocionCodigoCuponAutogenerado(String couponCode) {
        CouponTypeDTO couponType = sesion.getSesionPromociones().getCouponTypeByPrefix(couponCode);
        
        if(couponType != null) {
	        int prefixLength = couponType.getPrefix().length();
			String promotionIdStr = StringUtils.substring(couponCode, prefixLength, prefixLength + 10);
			return new Long(promotionIdStr);
        }
        else {
        	return null;
        }
    }

	public List<CuponEmitidoTicket> getCuponesImpresionManual(Long idPromocion, Integer cantidad) throws CuponesServiceException, PromocionesServiceException {
		List<CuponEmitidoTicket> cupones = new ArrayList<>();

//		// Obtenemos lista de promociones activas para configuración de cupones
		PromocionGeneracionCuponesBean promocion = sesion.getSesionPromociones().getPromocionesActivasEmisionCuponManual().get(idPromocion);
		// Generamos tantos cupones como número de impresiones manuales configuradas
		if (cantidad != null && cantidad > 0) {
			for (int i = 0; i < cantidad; i++) {
				CuponEmitidoTicket cupon = promocion.generaCupon(null);
				cupones.add(cupon);
			}
		}
		return cupones;
	}

	@SuppressWarnings("rawtypes")
    public GeneratedCouponDto getCuponDtoFuturo(ITicket ticket, Promocion promocion, BigDecimal couponAmount) throws CuponesServiceException, ContadorServiceException {
		try {
			Long idPromocionGeneracion = promocion.getIdPromocion();
			
			if (promocion.getDatosCupon() == null) {
				log.warn("getCuponDtoFuturo() - La promoción de generación " + idPromocionGeneracion + " no tiene los datos del cupón para el descuento futuro");
				return null;				
			}
			
			String couponTypeCode = promocion.getDatosCupon().getCouponTypeCode();
			Promocion promocionActiva = sesion.getSesionPromociones().getPromocionActiva(idPromocionGeneracion);
			
			Promocion promocionAplicacion = sesion.getSesionPromociones().getPromocionActiva(promocionActiva.getDatosCupon().getIdPromocionAplicacion());
			if(promocionAplicacion == null) {
				log.warn("getCuponDtoFuturo() - La promoción de aplicación " + promocionActiva.getDatosCupon().getIdPromocionAplicacion() + " no está activa para la promoción de generación " + idPromocionGeneracion);
				return null;
			}
			
			GeneratedCouponDto newCoupon = new GeneratedCouponDto(null, couponAmount);
			newCoupon.setPromotionId(promocionAplicacion.getIdPromocion());
			newCoupon.setStartDate(new Date());
			newCoupon.setEndDate(promocionAplicacion.getFechaFin());
			newCoupon.setCouponTypeCode(couponTypeCode);
			
			String couponCode = couponsCodeGeneratorService.generateCouponCode(newCoupon);
			newCoupon.setCouponCode(couponCode);
			
			return newCoupon;
		}
		catch(Exception e) {
			log.error("getCuponDtoFuturo() - Error: " + e.getMessage(), e);
			throw new CuponesServiceException(e);
		}
    }
	
	public Long getIdPromocionAplicacionDtoFuturo(String codigo) throws CuponesServiceException {
		if (codigo == null || codigo.length() != 30) {
            return null;
        }
        if (!codigo.substring(0, 2).equals("47")) {
            return null;
        }
        return Long.parseLong(codigo.substring(12, 22));
	}

	public BigDecimal getImporteDescuentoCupon(String codigo) {
		CouponTypeDTO couponType = sesion.getSesionPromociones().getCouponTypeByPrefix(codigo);
        int beginIndex = couponType.getPrefix().length() + 10;
		String importe = codigo.substring(beginIndex, beginIndex + 5);
		return new BigDecimal(importe).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }
}
