package com.comerzzia.pos.services.ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.comerzzia.pos.persistence.core.impuestos.porcentajes.PorcentajeImpuestoBean;
import com.comerzzia.pos.persistence.fidelizacion.CustomerCouponDTO;
import com.comerzzia.pos.persistence.fidelizacion.FidelizacionBean;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.promociones.LineaDocumentoPromocionable;
import com.comerzzia.pos.services.ticket.cabecera.CabeceraTicket;
import com.comerzzia.pos.services.ticket.cabecera.ComparadorSubtotalesIvaTicketPorcentaje;
import com.comerzzia.pos.services.ticket.cabecera.SubtotalIvaTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponEmitidoTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.pagos.PagoTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.config.SpringContext;

public abstract class TicketVenta<M extends IPromocionTicket> extends Ticket<LineaTicket, PagoTicket, CabeceraTicket> {
    protected List<M> promociones;
    
    protected List<CuponEmitidoTicket> cuponesEmitidos;

    protected List<CuponAplicadoTicket> cuponesAplicados;
    
    //Map linking the promotions with the coupons triying to apply them.
    protected Map<Long, List<CustomerCouponDTO>> couponsAppliyingPromotions;
    
    protected Boolean admitePromociones = true;
        
    public TicketVenta(){
        super();
        promociones = new ArrayList<>();
        cuponesEmitidos = new ArrayList<>();
        cuponesAplicados = new ArrayList<>();
    }
    
    
    public void recalcularSubtotalesIva(){
        // Construimos mapa con subtotales recorriendo todas las líneas del ticket
        Map<String, SubtotalIvaTicket> subtotales = new HashMap<>();
        Sesion sesion = SpringContext.getBean(Sesion.class);
        for (LineaTicket linea : lineas) {
        	linea.recalcularImporteFinal();
            String codImpuesto = linea.getArticulo().getCodImpuesto();
            SubtotalIvaTicket subtotal = subtotales.get(codImpuesto);
            if (subtotal == null){
                subtotal = crearSubTotalIvaTicket();
				PorcentajeImpuestoBean porcentajeImpuesto = sesion.getImpuestos().getPorcentaje(getCliente().getIdTratImpuestos(), codImpuesto);
                subtotal.setPorcentajeImpuestoBean(porcentajeImpuesto);
                subtotales.put(codImpuesto, subtotal);
            }
            subtotal.addLinea(linea);
        }
               
        // Añadimos cada subtotal a la lista de la cabecera del ticket
        getCabecera().getSubtotalesIva().clear();
        for (SubtotalIvaTicket subtotal : subtotales.values()) {
            // Recalculamos cada subtotal (impuestos, cuotas y totales)
            subtotal.recalcular();
            getCabecera().getSubtotalesIva().add(subtotal);
        }
        
        ComparadorSubtotalesIvaTicketPorcentaje comparador = new ComparadorSubtotalesIvaTicketPorcentaje();
        Collections.sort(getCabecera().getSubtotalesIva(),comparador);
    }
    
    protected SubtotalIvaTicket crearSubTotalIvaTicket() {
    	return new SubtotalIvaTicket();
    }
    
                    
    /**
     * Comprueba que el total pendiente de pagar del ticket es 0
     * @return
     */
    public boolean isPagosCubiertos(){
        return (getTotales().getPendiente()!=null && getTotales().getPendiente().compareTo(BigDecimal.ZERO) == 0);
    }
                  
	public boolean isAdmitePromociones() {
		return admitePromociones;
	}
	
	public void setAdmitePromociones(Boolean admitePromociones) {
		this.admitePromociones = admitePromociones;
	}
    
    public Long getIdTratImpuestos(){
        //Sesion sesion = SpringContext.getBean(Sesion.class);
        return getCabecera().getTienda().getCliente().getIdTratImpuestos();
    }
       
    @Override
    public List<M> getPromociones() {
    	return promociones;
    }
    
    public void setPromociones(List<M> promociones) {
    	this.promociones = promociones;
    }

    public List<CuponEmitidoTicket> getCuponesEmitidos() {
    	return cuponesEmitidos;
    }
    
    public void setCuponesEmitidos(List<CuponEmitidoTicket> cuponesEmitidos) {
    	this.cuponesEmitidos = cuponesEmitidos;
    }
    
    public List<CuponAplicadoTicket> getCuponesAplicados() {
    	return cuponesAplicados;
    }

    public void setCuponesAplicados(List<CuponAplicadoTicket> cuponesAplicados) {
    	this.cuponesAplicados = cuponesAplicados;
    }
    
                
    public abstract void inicializarTotales();	
	
	
	public M getPromocion(Long idPromocion){
    	for (M promocion : promociones) {
			if (promocion.getIdPromocion().equals(idPromocion)){
				return promocion;
			}
		}
    	return null;
    }
    
    public M getPromocion(Long idPromocion, String codCupon) {
    	for (M promocion : promociones) {
    		if(promocion.getIdPromocion().equals(idPromocion) && (promocion.getCodAcceso() == null || promocion.getCodAcceso().equals(codCupon))) {
    			return promocion;
    		}
    	}
    	return null;
    }
   
    public void addPromocion(M promocion) {
        promociones.add(promocion);
    }
    
    public boolean tienePromocionesAhorroMayor0(){
    	for (IPromocionTicket promocion : getPromociones()) {
			if(BigDecimalUtil.isMayorACero(promocion.getImporteTotalAhorro()) && !promocion.isDescuentoAFuturo()){
				return true;
			}
		}
    	return false;
    }
    
    
    public void addCuponEmitido(CuponEmitidoTicket cupon) {
        this.cuponesEmitidos.add(cupon);
    }
    
    public CuponAplicadoTicket getCuponAplicado(String codigo){
        for (CuponAplicadoTicket cupon : cuponesAplicados) {
            if (cupon.getCodigo().equals(codigo)){
                return cupon;
            }
        }
        return null;
    }

    public void addCuponAplicado(CuponAplicadoTicket cupon) {
        this.cuponesAplicados.add(cupon);
    }

    public List<LineaDocumentoPromocionable> getLineasDocumentoPromocionable() {
    	List<LineaDocumentoPromocionable> list = new LinkedList<>();
    	list.addAll(lineas);
        return list;
    }
    
	public FidelizacionBean getDatosFidelizado() {
		return getCabecera().getDatosFidelizado();
	}

	public void addPuntos(Integer puntos) {
		getTotales().addPuntos(puntos);
	}

	public void resetPuntos() {
		getTotales().resetPuntos();
	}
	
	public BigDecimal getCantidadTotal() {
		BigDecimal cantidad = BigDecimal.ZERO;

		try {
			for (LineaTicket lineaTicket : (List<LineaTicket>) getLineas()) {
				if (lineaTicket.getArticulo() != null && lineaTicket.getArticulo().getWeightRequired()) {
					cantidad = cantidad.add(BigDecimal.ONE);
				}
				else {
					if (lineaTicket.getCantidad() != null) {
						cantidad = cantidad.add(lineaTicket.getCantidad().abs());
	
					}
				}
			}
		}
		catch(Exception e) {
			log.error("getCantidadTotal() - Ha habido un error al calcular la cantidad total de artículos: " + e.getMessage(), e);
		}
		
		cabecera.setCantidadArticulos(cantidad);

		return cantidad;
	}

    
	public List<CustomerCouponDTO> getCouponsAppliyingPromotion(Long idPromocion) {
		if(couponsAppliyingPromotions == null) {
			couponsAppliyingPromotions = new HashMap<Long,List<CustomerCouponDTO>>();
		}
		if(couponsAppliyingPromotions.get(idPromocion) == null) {
			couponsAppliyingPromotions.put(idPromocion, new ArrayList<CustomerCouponDTO>());
		}
		return couponsAppliyingPromotions.get(idPromocion);
	}

	public void addCouponAppliyingPromotion(Long idPromocion, CustomerCouponDTO customerCouponDTO) {
		getCouponsAppliyingPromotion(idPromocion).add(customerCouponDTO);
	}

	public void removeCouponsAppliyingPromotion(Long idPromocion) {
		couponsAppliyingPromotions.remove(idPromocion);
	}
	
	public void removeCouponsAppliyingPromotion(String couponCode) {
		for(Entry<Long, List<CustomerCouponDTO>> couponsAppliyingPromotionsEntry : couponsAppliyingPromotions.entrySet()) {
			Iterator<CustomerCouponDTO> couponsCustomer = couponsAppliyingPromotionsEntry.getValue().iterator();
			while(couponsCustomer.hasNext()) {
				CustomerCouponDTO customerCoupon = couponsCustomer.next();
				if(couponCode.equals(customerCoupon.getCouponCode())) {
					couponsCustomer.remove();
				}
			}
			if(couponsAppliyingPromotionsEntry.getValue().isEmpty()) {
				couponsAppliyingPromotions.remove(couponsAppliyingPromotionsEntry.getKey());
			}
		}
	}
    
	public Map<Long, List<CustomerCouponDTO>> getMapCouponsAppliyingPromotion() {
		return couponsAppliyingPromotions;
	}
}
