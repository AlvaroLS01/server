package com.comerzzia.pos.services.ticket.cabecera;

import java.math.BigDecimal;
import java.util.List;

import com.comerzzia.pos.services.ticket.ITicket;
import com.comerzzia.pos.services.ticket.TicketVenta;
import com.comerzzia.pos.services.ticket.TicketVentaAbono;
import com.comerzzia.pos.services.ticket.lineas.LineaTicketAbstract;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.util.bigdecimal.BigDecimalUtil;
import com.comerzzia.pos.util.format.FormatUtil;


public abstract class TotalesTicketAbstract<C extends IPagoTicket>  implements ITotalesTicket<C> {
    protected ITicket<?, C, ?, ?, ?> ticket;
    
    protected BigDecimal impuestos;
    
    protected BigDecimal base;
    
    protected BigDecimal total;
    
    protected BigDecimal entregado;
    
    protected BigDecimal totalSinPromociones;
    
    protected BigDecimal totalPromocionesLineas;
    
    protected BigDecimal totalPromocionesCabecera;
    
    protected BigDecimal totalPromociones;
    
    protected BigDecimal totalAPagar;
    
    protected int puntos;
    
    protected C cambio;
    
    protected BigDecimal entregadoACuenta = BigDecimal.ZERO;
    
    public TotalesTicketAbstract(){    
        totalPromocionesCabecera = BigDecimal.ZERO;
        totalPromocionesLineas = BigDecimal.ZERO;
    }
    
    public TotalesTicketAbstract(ITicket<?, C, ?, ?, ?> ticket){
        resetearTotales();
        this.ticket = ticket;
    }
    
    protected void resetearTotales(){
    	base = BigDecimal.ZERO;
        impuestos = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
        entregado = BigDecimal.ZERO;        
    	
        totalSinPromociones = BigDecimal.ZERO;
        totalPromocionesLineas = BigDecimal.ZERO;
        totalPromocionesCabecera = BigDecimal.ZERO;
        totalPromociones = BigDecimal.ZERO;
        totalAPagar = BigDecimal.ZERO;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#recalcular()
	 */
    @Override
	public void recalcular(){
    	// Reseteamos los totales
        resetearTotales();
        ((TicketVentaAbono) ticket).recalcularSubtotalesIva();
        
        //calculo de la base y el total a partir de los subtotales
		for (ISubtotalIvaTicket subtotal : (List<ISubtotalIvaTicket>) ticket.getCabecera().getSubtotalesIva()) {
			base = base.add(subtotal.getBase());
			impuestos = impuestos.add(subtotal.getImpuestos());
			total = total.add(subtotal.getTotal());
		}
		
		// Calculamos los totales de las promociones		
		for (LineaTicketAbstract lineaT :  (List<LineaTicketAbstract>)ticket.getLineas()) {
			totalPromocionesCabecera = totalPromocionesCabecera.add(lineaT.getImporteTotalPromocionesMenosIngreso());		
    		//totalPromocionesLineas = BigDecimalUtil.redondear(totalPromocionesLineas.add(lineaT.getImporteTotalPromociones()));
			if(lineaT.getPromociones().isEmpty()) {
				totalSinPromociones = BigDecimalUtil.redondear(totalSinPromociones.add(lineaT.getImporteTotalConDto()));
			} else {
				totalSinPromociones = BigDecimalUtil.redondear(totalSinPromociones.add(lineaT.getImporteTotalSinDto()));
			}
        }
		totalPromocionesLineas = totalSinPromociones.subtract(total);
              
        // Calculamos entregado sumando todos los pagos
        for (Object pagoTicket : ((TicketVentaAbono)ticket).getPagos()) {
            entregado = entregado.add(((IPagoTicket)pagoTicket).getImporte());
        }
        
        // Calculamos totales 
        totalPromociones = totalPromocionesCabecera.add(totalPromocionesLineas);
        totalAPagar = total;
        
        totalSinPromociones = BigDecimalUtil.redondear(totalSinPromociones);
        totalPromocionesLineas = BigDecimalUtil.redondear(totalPromocionesLineas);
        totalPromociones = BigDecimalUtil.redondear(totalPromociones);
        totalPromocionesCabecera = BigDecimalUtil.redondear(totalPromocionesCabecera);
        
        BigDecimal totalEntregado = entregado.add(entregadoACuenta);
		if(BigDecimalUtil.isMenorACero(totalAPagar)){
        	if(BigDecimalUtil.isMayor(totalEntregado, totalAPagar)){
        		cambio.setImporte(BigDecimal.ZERO);
        	}else{
        		cambio.setImporte(totalEntregado.subtract(totalAPagar));
        	}
        }else{
        	if(BigDecimalUtil.isMenor(totalEntregado, totalAPagar)){
        		cambio.setImporte(BigDecimal.ZERO);
        	}else{
        		cambio.setImporte(totalEntregado.subtract(totalAPagar));
        	}
        }
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getPendiente()
	 */
    @Override
    public BigDecimal getPendiente() {
    	BigDecimal totalEntregado = entregado.add(entregadoACuenta);
    	BigDecimal pendiente = BigDecimal.ZERO;
    	
		if(BigDecimalUtil.isMenorACero(totalAPagar)){
        	if(BigDecimalUtil.isMayor(totalEntregado, totalAPagar)){
        		pendiente = totalEntregado.subtract(totalAPagar);
        	}
        }else{
        	if(BigDecimalUtil.isMenor(totalEntregado, totalAPagar)){
        		pendiente = totalAPagar.subtract(totalEntregado);
        	}
        }
		
        return pendiente;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotal()
	 */
    @Override
	public BigDecimal getTotal() {
        return total;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#setTicket(com.comerzzia.pos.services.ticket.TicketVenta)
	 */
    @Override
	public void setTicket(TicketVenta ticket) {
        this.ticket = ticket;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getEntregado()
	 */
    @Override
	public BigDecimal getEntregado() {
        return entregado;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#setImpuestos(java.math.BigDecimal)
	 */
    @Override
	public void setImpuestos(BigDecimal impuestos) {
    	this.impuestos = impuestos;
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#setBase(java.math.BigDecimal)
	 */
    @Override
	public void setBase(BigDecimal base) {
        this.base = base;
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getImpuestos()
	 */
    @Override
	public BigDecimal getImpuestos() {
		return impuestos;
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getBase()
	 */
	@Override
	public BigDecimal getBase() {
		return base;
	}
	
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalSinPromociones()
	 */
    @Override
	public BigDecimal getTotalSinPromociones() {
        return totalSinPromociones;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalPromocionesLineas()
	 */
    @Override
	public BigDecimal getTotalPromocionesLineas() {
        return totalPromocionesLineas;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalPromocionesCabecera()
	 */
    @Override
	public BigDecimal getTotalPromocionesCabecera() {
        return totalPromocionesCabecera;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalPromociones()
	 */
    @Override
	public BigDecimal getTotalPromociones() {
    	return totalPromociones;
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalAPagar()
	 */
    @Override
	public BigDecimal getTotalAPagar() {
        return totalAPagar;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#setTotalAPagar(java.math.BigDecimal)
	 */
    @Override
	public void setTotalAPagar(BigDecimal totalAPagar) {
        this.totalAPagar = totalAPagar;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getTotalAPagarAsString()
	 */
    @Override
	public String getTotalAPagarAsString(){
        return FormatUtil.getInstance().formateaImporte(totalAPagar);
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#hayPromocionesCabecera()
	 */
    @Override
	public boolean hayPromocionesCabecera(){
        return totalPromocionesCabecera.compareTo(BigDecimal.ZERO)>0;
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getPuntos()
	 */
    @Override
	public int getPuntos() {
        return puntos;
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#setPuntos(int)
	 */
    @Override
	public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#addPuntos(java.lang.Integer)
	 */
	@Override
	public void addPuntos(Integer puntos) {
		this.puntos += puntos;
	}
	
	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#resetPuntos()
	 */
	@Override
	public void resetPuntos(){
		this.puntos = 0;
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getEntregadoACuenta()
	 */
	@Override
	public BigDecimal getEntregadoACuenta() {
		return entregadoACuenta;
	}

	/* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#setEntregadoACuenta(java.math.BigDecimal)
	 */
	@Override
	public void setEntregadoACuenta(BigDecimal entregadoACuenta) {
		this.entregadoACuenta = entregadoACuenta;
	}
        
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getEntregadoACuentaAsString()
	 */
    @Override
	public String getEntregadoACuentaAsString(){
        return FormatUtil.getInstance().formateaImporte(entregadoACuenta);
    }
    
    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#isHayEntregaCuenta()
	 */
    @Override
	public boolean isHayEntregaCuenta(){
        return (entregadoACuenta!=null && entregadoACuenta.compareTo(BigDecimal.ZERO)>0);
    }

    /* (non-Javadoc)
	 * @see com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket#getCambio()
	 */
    @Override
	public C getCambio() {
        return cambio;
    }

	@Override
	public void setCambio(C cambio) {
		this.cambio = cambio;			
	}
    
}
