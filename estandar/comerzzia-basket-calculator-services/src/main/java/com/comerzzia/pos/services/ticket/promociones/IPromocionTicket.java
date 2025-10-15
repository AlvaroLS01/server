package com.comerzzia.pos.services.ticket.promociones;

import java.math.BigDecimal;

public interface IPromocionTicket {
	public static final String COUPON_ACCESS = "CUPON";

	public abstract BigDecimal getImporteTotalAhorro();

	public abstract String getImporteTotalAhorroAsString();

	public abstract void setImporteTotalAhorro(BigDecimal importeTotalAhorro);

	public abstract Long getIdPromocion();

	public abstract void setIdPromocion(Long idPromocion);

	public abstract Long getIdTipoPromocion();

	public abstract void setIdTipoPromocion(Long idTipoPromocion);

	public abstract Long getTipoDescuento();

	public abstract void setTipoDescuento(Long tipoDescuento);

	public abstract Integer getPuntos();

	public abstract void setPuntos(Integer puntos);

	public abstract String getTextoPromocion();

	public abstract void setTextoPromocion(String textoPromocion);

	public abstract String getIdPromocionAsString();

	public abstract String getAcceso();

	public abstract void setAcceso(String acceso);

	public abstract String getCodAcceso();

	public abstract void setCodAcceso(String codAcceso);

	public abstract boolean isExclusiva();

	public abstract void setExclusiva(boolean exclusiva);

	public abstract boolean isDescuentoMenosMargen();

	public abstract boolean isDescuentoMenosIngreso();
	
	public abstract boolean isDescuentoAFuturo();

}