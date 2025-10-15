package com.comerzzia.pos.services.ticket.lineas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comerzzia.pos.persistence.articulos.tarifas.TarifaDetalleBean;
import com.comerzzia.pos.persistence.core.usuarios.UsuarioBean;
import com.comerzzia.pos.services.ticket.cabecera.ICabeceraTicket;
import com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket;
import com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaCandidataTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionLineaTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;

public abstract class LineaTicketAbstract implements ILineaTicket, Cloneable {
	protected final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(getClass());

    protected BigDecimal cantidad;
    protected LineaTicketItemData articulo;
    protected String desglose1;
    protected String desglose2;
    protected TarifaDetalleBean tarifa;
    protected TarifaDetalleBean tarifaOriginal;

    // Precios tarifa origen
	protected BigDecimal precioTarifaOrigen;
	protected BigDecimal precioTotalTarifaOrigen;

    // Importes sin descuento
	protected BigDecimal precioSinDto;
	protected BigDecimal precioTotalSinDto;

    // Precio de promoción precio aplicada previa al resto de promociones
	protected BigDecimal precioPromocionSinDto;
	protected BigDecimal precioPromocionTotalSinDto;

	// Importes con descuento línea
	protected BigDecimal precioConDto;
	protected BigDecimal precioTotalConDto;
	protected BigDecimal importeConDto;
	protected BigDecimal importeTotalConDto;

	protected BigDecimal descuentoManual;

	protected BigDecimal descuento;
	
    protected BigDecimal impuestos;
    protected Integer idLinea;
    protected ICabeceraTicket<ISubtotalIvaTicket,ITotalesTicket<IPagoTicket>> cabecera;
    protected BigDecimal cantidadDevuelta;
    protected BigDecimal cantidadADevolver;
    
	protected List<PromocionLineaTicket> promociones;
    protected BigDecimal importeTotalPromociones;
	protected BigDecimal importeTotalPromocionesMenosIngreso;
	protected List<PromocionLineaCandidataTicket> promocionesAplicables;
	protected List<PromocionLineaTicket> promocionesCandidatas;
	protected BigDecimal cantidadPromocion;
	protected BigDecimal cantidadPromocionCandidata;
	protected UsuarioBean vendedor;
	
    protected Boolean editable;
    protected Boolean imprimirTicketRegalo;
    protected Boolean ivaIncluido;
    protected Boolean isAdmitePromociones;

    protected Double puntosADevolver;
    /**
     * Id que relaciona la linea del ticket del abono con el de la venta
     */
    protected Integer lineaDocumentoOrigen;
    
    protected String codigoBarras;
    
    protected Map<String, TarifaDetalleBean> tarifasDisponibles;

    protected Boolean hayCambioTarifaReferencia;    
        
	public LineaTicketAbstract() {
		isAdmitePromociones = true;
		descuento = BigDecimal.ZERO;
		descuentoManual = BigDecimal.ZERO;
		precioSinDto = BigDecimal.ZERO;
		precioTotalSinDto = BigDecimal.ZERO;
		precioConDto = BigDecimal.ZERO;
		precioTotalConDto = BigDecimal.ZERO;
		importeConDto = BigDecimal.ZERO;
		importeTotalConDto = BigDecimal.ZERO;
		precioTotalTarifaOrigen = BigDecimal.ZERO;
		
		impuestos = BigDecimal.ZERO;
		articulo = new LineaTicketItemData();
		cantidadADevolver = BigDecimal.ZERO;
		cantidadDevuelta = BigDecimal.ZERO;
		
		importeTotalPromociones = BigDecimal.ZERO;
		importeTotalPromocionesMenosIngreso = BigDecimal.ZERO;
		promocionesAplicables = new ArrayList<>();
		promocionesCandidatas = new ArrayList<>();
		promociones = new ArrayList<>();
		cantidadPromocion = BigDecimal.ZERO;
		cantidadPromocionCandidata = BigDecimal.ZERO;
		
		ivaIncluido = false;				
		
		tarifasDisponibles = new HashMap<String, TarifaDetalleBean>();
		hayCambioTarifaReferencia = false;
	}
		
    public LineaTicketAbstract(ICabeceraTicket<ISubtotalIvaTicket,ITotalesTicket<IPagoTicket>> cabecera){
    	this();
        this.cabecera = cabecera;
        editable = true;
    }
  
    @Override
    public LineaTicketItemData getArticulo() {
        return articulo;
    }

    public void setArticulo(LineaTicketItemData articulo) {
        this.articulo = articulo;
    }

    public abstract void recalcularPreciosImportes() ;

    
	public abstract void recalcularImporteFinal();   
	
	@Override
	public BigDecimal getPrecioAplicacionPromocion(){
		if (getPrecioPromocionTotalSinDto()!=null){
			return getPrecioPromocionTotalSinDto();
		}
		return getPrecioTotalSinDto();
	}
	
	@Override
	public BigDecimal getImporteAplicacionPromocionConDto(){
		return getImporteTotalConDto();
	}
	
	@Override
	public boolean tieneDescuentoManual(){
		return BigDecimalUtil.isMayorACero(descuentoManual);
	}
	
	@Override
	public boolean tieneCambioPrecioManual(){
		return !BigDecimalUtil.redondear(precioTotalTarifaOrigen).equals(BigDecimalUtil.redondear(precioTotalSinDto));
	}
	
	public BigDecimal getImporteTotalSinDto(){
		return BigDecimalUtil.redondear(getPrecioTotalSinDto().multiply(cantidad));
	}
	
	public BigDecimal getImporteTotalSinDtoSinRedondear(){
		return getPrecioTotalSinDto().multiply(cantidad);
	}
    
    public BigDecimal getImpuestos() {
        return impuestos;
    }


    public void setTarifa(TarifaDetalleBean tarifa) {
    	this.tarifa = tarifa;
        
        addTarifaDisponible(tarifa.getCodTarifa(), tarifa);
    }
    
    @Override
    public TarifaDetalleBean getTarifa() {
        return tarifa;
    }

	public TarifaDetalleBean getTarifaOriginal() {
		return tarifaOriginal;
	}

	public void setTarifaOriginal(TarifaDetalleBean tarifaOriginal) {
		this.tarifaOriginal = tarifaOriginal;
	}

	@Override
	public String getCodArticulo() {
        return articulo.getItemCode();
    }
    
    public void setCodArticulo(String codArticulo) {
        this.articulo.setItemCode(codArticulo);
    }

	public Boolean getGenerico() {
		return articulo.getGenericItem();
	}

	public void setGenerico(Boolean generico) {
		this.articulo.setGenericItem(generico);
	}
    
	@Override
    public String getDesglose1() {
        return desglose1;
    }
    
    public void setDesglose1(String desglose1) {
        this.desglose1 = desglose1!=null?desglose1:"*";
    }

    @Override
    public String getDesglose2() {
        return desglose2;
    }
    
    public void setDesglose2(String desglose2) {
        this.desglose2 = desglose2!=null?desglose2:"*";
    }

    public String getDesArticulo() {
        return articulo.getItemDes();
    }
    
    /**
     * Método para el unmarshall
     * @param desArticulo
     * @deprecated No usar el método. Exclusivo para unmarshal
     */
    public void setDesArticulo(String desArticulo) {
        articulo.setItemDes(desArticulo);
    }

    @Override
    public BigDecimal getCantidad() {        
        return cantidad;
    }
    
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }
    
    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }
    
    public BigDecimal getDescuentoManual() {
		return descuentoManual;
	}

	public void setDescuentoManual(BigDecimal descuentoManual) {
		this.descuentoManual = descuentoManual;
	}

	public BigDecimal getPrecioSinDto() {
        return precioSinDto;
    }

    public void setPrecioSinDto(BigDecimal precioSinDto) {
        this.precioSinDto = precioSinDto;
    }

    public BigDecimal getPrecioTotalSinDto() {
        return precioTotalSinDto;
    }

    public void setPrecioTotalSinDto(BigDecimal precioTotalSinDto) {
        this.precioTotalSinDto = precioTotalSinDto;
    }
    
    public BigDecimal getPrecioTotalConDto() {
        return precioTotalConDto;
    }

    public void setPrecioTotalConDto(BigDecimal precioTotalConDto) {
        this.precioTotalConDto = precioTotalConDto;
    }

    public BigDecimal getPrecioConDto() {
        return precioConDto;
    }
    
    public void setPrecioConDto(BigDecimal precioConDto) {
        this.precioConDto = precioConDto;
    }
    
    public String getCodImpuesto() {
        return articulo.getTaxTypeCode();
    }
    
    /**
     * Método para el unmarshall
     * @param codImpuesto
     * @deprecated No usar el método. Exclusivo para unmarshal
     */
    public void setCodImpuesto(String codImpuesto) {
        articulo.setTaxTypeCode(codImpuesto);
    }

    @Override
    public Integer getIdLinea() {
        return idLinea;
    }
    
    public void setIdLinea(Integer idLinea) {
        this.idLinea = idLinea;
    }
    
	public ICabeceraTicket<ISubtotalIvaTicket,ITotalesTicket<IPagoTicket>> getCabecera() {
		return cabecera;
	}

	public void setCabecera(ICabeceraTicket<ISubtotalIvaTicket,ITotalesTicket<IPagoTicket>> cabecera) {
		this.cabecera = cabecera;
	}

    public BigDecimal getCantidadDevuelta() {
        return cantidadDevuelta;
    }

    public void setCantidadDevuelta(BigDecimal cantidadDevuelta) {
        this.cantidadDevuelta = cantidadDevuelta;
    }

    public BigDecimal getCantidadADevolver() {
        return cantidadADevolver;
    }

    public void setCantidadADevolver(BigDecimal cantidadADevolver) {
        this.cantidadADevolver = cantidadADevolver;
    }
        
    public BigDecimal getCantidadDisponibleDevolver(){
        return getCantidad().subtract(cantidadDevuelta.add(cantidadADevolver));
    }
    
    public Integer getLineaDocumentoOrigen() {
        return lineaDocumentoOrigen;
    }
    
    public void setLineaDocumentoOrigen(Integer lineaDocumentoOrigen) {
        this.lineaDocumentoOrigen = lineaDocumentoOrigen;
    }

    @Override
	public List<PromocionLineaTicket> getPromociones() {
		return promociones;
	}

	public void setPromociones(List<PromocionLineaTicket> promociones) {
		this.promociones = promociones;
	}

	public boolean isPromocionesAplicadas() {
		return promociones != null && !promociones.isEmpty();
	}

	@Override
	public void addPromocion(PromocionLineaTicket promocion) {
		this.promociones.add(promocion);
	}

	@Override
	public void addPromocionCandidata(PromocionLineaTicket promocion) {
		this.promocionesCandidatas.add(promocion);
	}

	@Override
	public void addPromocionAplicable(PromocionLineaCandidataTicket promocion) {
		this.promocionesAplicables.add(promocion);
	}

	@Override
	public List<PromocionLineaCandidataTicket> getPromocionesAplicables() {
		return promocionesAplicables;
	}

	@Override
	public boolean tienePromocionesAplicables() {
		return promocionesAplicables != null && !promocionesAplicables.isEmpty();
	}

	@Override
	public void resetPromociones() {
		promociones.clear();
		importeTotalPromociones = BigDecimal.ZERO;
		importeTotalPromocionesMenosIngreso = BigDecimal.ZERO;
		cantidadPromocion = BigDecimal.ZERO;
		cantidadPromocionCandidata = BigDecimal.ZERO;
		precioPromocionSinDto = null;
		precioPromocionTotalSinDto = null;
		
		if(hayCambioTarifaReferencia) {
			cambiarTarifaCalculos(tarifaOriginal.getCodTarifa());
			recalcularPreciosImportes();
		} else {
		   recalcularImporteFinal();
		}
	}

	@Override
	public void resetPromocionesAplicables() {
		promocionesAplicables.clear();
	}

	@Override
	public void resetPromocionesCandidatas() {
		promocionesCandidatas.clear();
		cantidadPromocionCandidata = BigDecimal.ZERO;
	}

	@Override
	public PromocionLineaTicket getPromocion(Long idPromocion) {
		return getPromocion(idPromocion, promociones);
	}

	@Override
	public PromocionLineaTicket getPromocionCandidata(Long idPromocion) {
		return getPromocion(idPromocion, promocionesCandidatas);
	}

	protected PromocionLineaTicket getPromocion(Long idPromocion, List<PromocionLineaTicket> listaPromociones) {
		for (PromocionLineaTicket promocionLineaTicket : listaPromociones) {
			if (promocionLineaTicket.getIdPromocion().equals(idPromocion)) {
				return promocionLineaTicket;
			}
		}
		return null;
	}

	@Override
	public boolean tienePromocionLineaExclusiva() {
		if (!promociones.isEmpty()) { // Sólo nos interesan las promociones de línea. 
			// Como son las primeras que se aplican, si hay una exclusiva tiene que estar en primer lugar
			return promociones.get(0).isExclusiva();
		}
		return false;
	}
	
	public BigDecimal getImporteTotalPromociones() {
		return importeTotalPromociones;
	}
	
	public BigDecimal getImporteTotalPromocionesMenosIngreso() {
		return importeTotalPromocionesMenosIngreso;
	}

	
	public void setImporteTotalPromociones(BigDecimal importeTotalPromociones) {
		this.importeTotalPromociones = importeTotalPromociones;
	}

	@Override
	public BigDecimal getCantidadPromocion() {
		return cantidadPromocion;
	}

	@Override
	public void addCantidadPromocion(BigDecimal aumento) {
		cantidadPromocion = cantidadPromocion.add(aumento);
	}

	@Override
	public BigDecimal getCantidadPromocionCandidata() {
		return cantidadPromocionCandidata.add(cantidadPromocion);
	}

	@Override
	public void addCantidadPromocionCandidata(BigDecimal aumento) {
		cantidadPromocionCandidata = cantidadPromocionCandidata.add(aumento);
	}
	
	public BigDecimal getPrecioTarifaOrigen() {
		return precioTarifaOrigen;
	}

	public void setPrecioTarifaOrigen(BigDecimal precioTarifaOrigen) {
		this.precioTarifaOrigen = precioTarifaOrigen;
	}

	public BigDecimal getPrecioTotalTarifaOrigen() {
		return precioTotalTarifaOrigen;
	}

	public void setPrecioTotalTarifaOrigen(BigDecimal precioTotalTarifaOrigen) {
		this.precioTotalTarifaOrigen = precioTotalTarifaOrigen;
	}

	public BigDecimal getImporteConDto() {
		return importeConDto;
	}

	public void setImporteConDto(BigDecimal importeConDto) {
		this.importeConDto = importeConDto;
	}

	public BigDecimal getImporteTotalConDto() {
		return importeTotalConDto;
	}
	
	public void setImporteTotalConDto(BigDecimal importeTotalConDto) {
		this.importeTotalConDto = importeTotalConDto;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}
        
	public void setImprimirTicketRegalo(boolean imprimir) {
		this.imprimirTicketRegalo = imprimir;
	}

	public UsuarioBean getVendedor() {
		return vendedor;
	}

	public void setVendedor(UsuarioBean vendedor) {
		this.vendedor = vendedor;
	}	
	
	public void setIvaIncluido(Boolean incluyeIva){
		this.ivaIncluido = incluyeIva;
	}
	
	protected List<PromocionLineaTicket> getPromocionesCandidatas() {
		return promocionesCandidatas;
	}

	protected void setPromocionesCandidatas(
			List<PromocionLineaTicket> promocionesCandidatas) {
		this.promocionesCandidatas = promocionesCandidatas;
	}

	protected Boolean isIvaIncluido() {
		return ivaIncluido;
	}

	protected void setImpuestos(BigDecimal impuestos) {
		this.impuestos = impuestos;
	}

	protected void setImporteTotalPromocionesMenosIngreso(
			BigDecimal importeTotalPromocionesMenosIngreso) {
		this.importeTotalPromocionesMenosIngreso = importeTotalPromocionesMenosIngreso;
	}

	protected void setPromocionesAplicables(
			List<PromocionLineaCandidataTicket> promocionesAplicables) {
		this.promocionesAplicables = promocionesAplicables;
	}

	protected void setCantidadPromocion(BigDecimal cantidadPromocion) {
		this.cantidadPromocion = cantidadPromocion;
	}

	protected void setCantidadPromocionCandidata(
			BigDecimal cantidadPromocionCandidata) {
		this.cantidadPromocionCandidata = cantidadPromocionCandidata;
	}

	
    public BigDecimal getPrecioPromocionSinDto() {
    	return precioPromocionSinDto;
    }

	
    @Override
    public void setPrecioPromocionSinDto(BigDecimal precioPromocionSinDto) {
    	this.precioPromocionSinDto = precioPromocionSinDto;
    }

	    
    public BigDecimal getPrecioPromocionTotalSinDto() {
    	return precioPromocionTotalSinDto;
    }

	
    @Override
    public void setPrecioPromocionTotalSinDto(BigDecimal precioPromocionTotalSinDto) {
    	this.precioPromocionTotalSinDto = precioPromocionTotalSinDto;
    }
	
    /**Indica si los precios deben llevar impuestos incluidos o no
     * */
    @Override
    public boolean isPrecioIncluyeImpuestos() {
    	return true;
    }

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Double getPuntosADevolver() {
		return puntosADevolver;
	}

	public void setPuntosADevolver(Double puntosADevolver) {
		this.puntosADevolver = puntosADevolver;
	}
	
	public void setImprimirTicketRegalo(Boolean imprimir) {
		this.imprimirTicketRegalo = imprimir;
	}

	public Boolean isImprimirTicketRegalo() {
		return imprimirTicketRegalo;
	}

	public BigDecimal getImporteDescuento() {
		return getImporteTotalSinDto().subtract(importeTotalConDto);
	}
	
	@Override
	public boolean isAdmitePromociones() {
		return isAdmitePromociones;
	}

	public void setAdmitePromociones(Boolean admitePromociones) {
		isAdmitePromociones = admitePromociones;
	}
	
	public Map<String, TarifaDetalleBean> getTarifasDisponibles() {
		return tarifasDisponibles;
	}

	public void setTarifasDisponibles(Map<String, TarifaDetalleBean> tarifasDisponibles) {
		this.tarifasDisponibles = tarifasDisponibles;
	}
	
	public boolean containsTarifaDisponible(String codtar) {
		return tarifasDisponibles.containsKey(codtar);
	}
	
	public void addTarifaDisponible(String codtar, TarifaDetalleBean tarifa) {
		this.tarifasDisponibles.put(codtar, tarifa);
	}

	
	public void cambiarTarifaCalculos(String codtar) {
		if(tarifasDisponibles.containsKey(codtar)) {
			TarifaDetalleBean tarifaNueva = tarifasDisponibles.get(codtar);
			setTarifa(tarifaNueva);
			precioTarifaOrigen = null;
			recalcularPreciosImportes();
		}
		else {
			log.warn("cambiarTarifa() - No está registrada la tarifa " + codtar + " para la línea " + idLinea);
		}
	}

	@Override
    public boolean hayCambioTarifaReferencia() {
		return hayCambioTarifaReferencia;
    }

	public void setHayCambioTarifaReferencia(boolean hayCambioTarifaReferencia) {
		this.hayCambioTarifaReferencia = hayCambioTarifaReferencia;
	}

	@Override
    public void cambiarTarifaOriginal() {
		cambiarTarifaCalculos(tarifaOriginal.getCodTarifa());
		this.hayCambioTarifaReferencia = false;
    }

	
}

