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


package com.comerzzia.pos.services.promociones.tipos.especificos.detalles;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.comerzzia.pos.persistence.promociones.detalle.PromocionDetalleBean;
import com.comerzzia.pos.services.promociones.Promocion;


public class DetallePromocion {
	
//	protected Sesion sesion;
	
    protected final PromocionDetalleBean detalle;
    protected final Promocion promocion;
    
    public DetallePromocion(Promocion promocion, PromocionDetalleBean detalle){
        this.promocion = promocion;
        this.detalle = detalle;
        
//        try {
//			this.sesion = (Sesion)ContextHolder.getBean("getSesion");
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException(e);
//		}
    }

    public boolean isAplicable(){
       Calendar calendario = Calendar.getInstance();
       calendario.set(Calendar.HOUR_OF_DAY, 0);
    	 calendario.set(Calendar.MINUTE, 0);
    	 calendario.set(Calendar.SECOND, 0);
    	 calendario.set(Calendar.MILLISECOND, 0);
       return !getFechaInicio().after(calendario.getTime()) && !getFechaFin().before(calendario.getTime());
    }
    
    
    public BigDecimal getPrecioTarifa() {
        return detalle.getPrecioTarifa();
    }

    public BigDecimal getPrecioTarifaTotal() {
        return detalle.getPrecioTarifaTotal();
    }

    public String getTextoPromocion() {
        return detalle.getTextoPromocion();
    }

    public Date getFechaInicio() {
        return detalle.getFechaInicio();
    }

    public Date getFechaFin() {
        return detalle.getFechaFin();
    }

    public String getCodArticulo() {
        return detalle.getCodArticulo();
    }
    
    public String getDesglose1() {
        return detalle.getDesglose1();
    }
    
    public String getDesglose2() {
        return detalle.getDesglose2();
    }

    public BigDecimal getPrecioVenta() {
        return detalle.getPrecioVenta();
    }

    public BigDecimal getPrecioTotal() {
        return detalle.getPrecioTotal();
    }

    public Promocion getPromocion() {
        return promocion;
    }
	
    public PromocionDetalleBean getDetalle() {
    	return detalle;
    }

    
    
}
