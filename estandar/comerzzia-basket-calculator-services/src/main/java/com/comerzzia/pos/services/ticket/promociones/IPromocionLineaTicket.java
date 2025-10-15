package com.comerzzia.pos.services.ticket.promociones;

import java.math.BigDecimal;

public interface IPromocionLineaTicket {

	public abstract void addCantidadPromocion(BigDecimal aumento);

	public abstract void addCantidadPromocionAplicada(BigDecimal aumento);

	public abstract void addImporteTotalDto(BigDecimal importe);

	public abstract void setImporteTotalDto(BigDecimal importe);

	public abstract BigDecimal getCantidadPromocion();

	public abstract void setCantidadPromocion(BigDecimal cantidadPromocion);

	public abstract BigDecimal getCantidadPromocionAplicada();

	public abstract void setCantidadPromocionAplicada(BigDecimal cantidadPromocionAplicada);

	public abstract Long getIdPromocion();

	public abstract void setIdPromocion(Long idPromocion);

	public abstract Long getIdTipoPromocion();

	public abstract void setIdTipoPromocion(Long idTipoPromocion);

	public abstract Integer getPuntos();

	public abstract void setPuntos(Integer puntos);
	    
	public abstract String getTextoPromocion();
    
	public abstract void setTextoPromocion(String textoPromocion);

	public abstract String getIdPromocionAsString();

	public abstract BigDecimal getImporteTotalDtoMenosMargen();

	public abstract BigDecimal getImporteTotalDtoMenosMargenSinRedondear();

	public abstract String getImporteTotalDtoMenosMargenAsStringNegate();

	public abstract void setImporteTotalDtoMenosMargen(BigDecimal importeTotalDtoMenosMargen);

	public abstract BigDecimal getImporteTotalDtoMenosIngreso();

	public abstract void setImporteTotalDtoMenosIngreso(BigDecimal importeTotalDtoMenosIngreso);

	public abstract BigDecimal getImporteTotalDtoFuturo();

	public abstract void setImporteTotalDtoFuturo(BigDecimal importeTotalDtoFuturo);

	public abstract String getAcceso();

	public abstract void setAcceso(String acceso);

	public abstract String getCodAcceso();

	public abstract void setCodAcceso(String codAcceso);

	public abstract boolean isExclusiva();

	public abstract void setExclusiva(boolean exclusiva);

	public abstract Long getTipoDescuento();

	public abstract void setTipoDescuento(Long tipoDescuento);

	public abstract boolean isDescuentoMenosMargen();

	public abstract boolean isDescuentoMenosIngreso();

	public abstract boolean isDescuentoFuturo();

}