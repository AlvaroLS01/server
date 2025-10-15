package com.comerzzia.pos.services.promociones;

import java.math.BigDecimal;
import java.util.List;

import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleBean;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketItemData;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;

public interface LineaDocumentoPromocionable {
	
	public Integer getIdLinea();
	
	public TarifaDetalleBean getTarifa();

	public boolean isAdmitePromociones();

	public LineaTicketItemData getArticulo();

	public void recalcularImporteFinal();

	public boolean tieneDescuentoManual();

	public boolean tieneCambioPrecioManual();

	public String getCodArticulo();
	
	public String getDesglose1();
	
	public String getDesglose2();

	public BigDecimal getCantidad();

	// Promociones
	public List<PromocionLineaTicket> getPromociones();

	public void addPromocion(PromocionLineaTicket promocion);

	public void addPromocionCandidata(PromocionLineaTicket promocion);

	public void addPromocionAplicable(PromocionLineaCandidataTicket promocion);

	public List<PromocionLineaCandidataTicket> getPromocionesAplicables();

	public PromocionLineaTicket getPromocion(Long idPromocion);

	public PromocionLineaTicket getPromocionCandidata(Long idPromocion);
	
	public void resetPromociones();

	public void resetPromocionesAplicables();

	public void resetPromocionesCandidatas();

	public boolean tienePromocionLineaExclusiva();
	
	public boolean tienePromocionesAplicables();

	public BigDecimal getCantidadPromocion();

	public void addCantidadPromocion(BigDecimal aumento);

	public BigDecimal getCantidadPromocionCandidata();

	public void addCantidadPromocionCandidata(BigDecimal aumento);

	public void setPrecioPromocionSinDto(BigDecimal precioPromocionSinDto);

	public void setPrecioPromocionTotalSinDto(BigDecimal precioPromocionTotalSinDto);

	public BigDecimal getImporteAplicacionPromocionConDto();
		
	public BigDecimal getPrecioAplicacionPromocion();
	
	/**Indica si los precios deben llevar impuestos incluidos o no
     * */
    public boolean isPrecioIncluyeImpuestos();
    
    public void cambiarTarifaCalculos(String codtar);
    
    public void cambiarTarifaOriginal();
    
    public boolean containsTarifaDisponible(String codtar);
    
    public void addTarifaDisponible(String codtar, TarifaDetalleBean tarifa);
    
    public boolean hayCambioTarifaReferencia();
    
    public void setHayCambioTarifaReferencia(boolean hayCambioTarifaReferencia);

}