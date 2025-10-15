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


package com.comerzzia.pos.services.ticket.cupones;

import java.math.BigDecimal;

import com.comerzzia.pos.services.promociones.Promocion;


public class CuponAplicadoTicket {
    protected static final String CODIGO_FIJO = "CODIGO_FIJO";
    protected static final String AUTONUMERADO = "AUTONUMERADO";
	
	protected String codigo;
    protected String tipoCupon;
    protected Long idPromocion;
    protected Long idTipoPromocion;
    protected String textoPromocion;
    protected BigDecimal descuento;
    protected BigDecimal importeTotalAhorrado;

    public CuponAplicadoTicket() {
    }

    public CuponAplicadoTicket(String codigo, Promocion promocion) {
        this.codigo = codigo;
        this.idPromocion = promocion.getIdPromocion();
        this.idTipoPromocion = promocion.getIdTipoPromocion();
        this.textoPromocion = promocion.getTextoPromocion();
        if (promocion.isActivaPorCuponFijo()){
        	this.tipoCupon = CODIGO_FIJO;
        }
        else{
        	this.tipoCupon = AUTONUMERADO;
        }
    }
    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoCupon() {
        return tipoCupon;
    }

    public boolean isTipoCuponAutonumerado(){
    	return tipoCupon.equals(AUTONUMERADO);
    }

    public boolean isTipoCuponCodigoFijo(){
    	return tipoCupon.equals(CODIGO_FIJO);
    }
    
    public void setTipoCupon(String tipoCupon) {
        this.tipoCupon = tipoCupon;
    }

    public Long getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }

    public Long getIdTipoPromocion() {
        return idTipoPromocion;
    }

    public void setIdTipoPromocion(Long idTipoPromocion) {
        this.idTipoPromocion = idTipoPromocion;
    }

	
    public String getTextoPromocion() {
    	return textoPromocion;
    }

	
    public void setTextoPromocion(String textoPromocion) {
    	this.textoPromocion = textoPromocion;
    }

	public BigDecimal getDescuento() {
		return descuento;
	}

	public void setDescuento(BigDecimal bigDecimal) {
		this.descuento = bigDecimal;
	}

	public BigDecimal getImporteTotalAhorrado() {
		return importeTotalAhorrado;
	}

	public void setImporteTotalAhorrado(BigDecimal importeTotalAhorrado) {
		this.importeTotalAhorrado = importeTotalAhorrado;
	}
    
    
}
