package com.comerzzia.pos.services.ticket;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comerzzia.pos.services.promociones.DocumentoPromocionable;
import com.comerzzia.pos.services.ticket.cabecera.CabeceraTicket;
import com.comerzzia.pos.services.ticket.cabecera.TotalesTicket;
import com.comerzzia.pos.services.ticket.lineas.LineaTicket;
import com.comerzzia.pos.services.ticket.pagos.EntregaCuentaTicket;
import com.comerzzia.pos.services.ticket.pagos.EntregasCuentaTicket;
import com.comerzzia.pos.services.ticket.promociones.PromocionTicket;
import com.comerzzia.pos.util.config.SpringContext;


@Component
@Scope("prototype")
public class TicketVentaAbono extends TicketVenta<PromocionTicket> implements DocumentoPromocionable<PromocionTicket> {	
    protected EntregasCuentaTicket entregasCuenta;
        
    protected boolean esDevolucion;
    protected boolean esApartado;
        
    public TicketVentaAbono(){    	
        super();
        
        schemaVersion = "1.1";

        cabecera = SpringContext.getBean(CabeceraTicket.class);
    }    
    
                      
    public EntregasCuentaTicket getEntregasCuenta() {
        return entregasCuenta;
    }
    
    public void setEntregasCuenta(EntregasCuentaTicket entregasCuenta) {
		this.entregasCuenta = entregasCuenta;
	}

	public void addEntregaCuenta(EntregaCuentaTicket entregaCuenta) {
    	if (entregasCuenta == null) {
    		entregasCuenta = new EntregasCuentaTicket();
    	}
    	entregasCuenta.getEntregasCuenta().add(entregaCuenta);
    }
        
    
    public void setEsDevolucion(boolean esDevolucion){
    	this.esDevolucion = esDevolucion;
    }
    
    @Override
    public boolean isEsDevolucion() {
    	return esDevolucion;
    }
    
    public void setEsApartado(boolean esApartado){
        this.esApartado = esApartado;
    }

    public boolean isEsApartado() {
    	return esApartado;
    }

    @SuppressWarnings("unchecked")
	public void inicializarTotales(){
    	getCabecera().setTotales(SpringContext.getBean(TotalesTicket.class, this));
    }
			
	public Integer getPuntosDevueltos(){
		Integer puntosDevueltos = 0;
		if(this.getLineas()!=null){    			
			for(LineaTicket lineaTicket: (List<LineaTicket>) this.getLineas()){
				Double puntosLineaDevueltos = lineaTicket.getPuntosADevolver();
				if(puntosLineaDevueltos != null){
					puntosDevueltos += puntosLineaDevueltos.intValue();
				}
			}
		}
		
		return puntosDevueltos;
	}

}
