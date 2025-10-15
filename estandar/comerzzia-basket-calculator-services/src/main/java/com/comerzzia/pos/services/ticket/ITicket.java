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


package com.comerzzia.pos.services.ticket;

import java.util.List;

import com.comerzzia.pos.persistence.clientes.ClienteBean;
import com.comerzzia.pos.persistence.core.usuarios.UsuarioBean;
import com.comerzzia.pos.services.core.tiendas.Tienda;
import com.comerzzia.pos.services.ticket.cabecera.ICabeceraTicket;
import com.comerzzia.pos.services.ticket.cabecera.ISubtotalIvaTicket;
import com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponEmitidoTicket;
import com.comerzzia.pos.services.ticket.lineas.ILineaTicket;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;


public interface ITicket <L extends ILineaTicket, P extends IPagoTicket, C extends ICabeceraTicket<ISubtotalIvaTicket, T>, T extends ITotalesTicket<IPagoTicket>, M extends IPromocionTicket> {
    
    public C getCabecera();
    
    public T getTotales();
    
    public Long getIdTicket();
    
    public void setCajero(UsuarioBean cajero);
    
    public void setCliente(ClienteBean cliente);
    
    public ILineaTicket getLinea(Integer idLinea);
    
    public Tienda getTienda();
    
    public List<L> getLineas();
    
    public String getUidTicket();
    
    public ClienteBean getCliente();
    
    public String getCodTarifa();

    public boolean isEsDevolucion();
    
    public void setEsDevolucion(boolean esDevolucion);
    
    public List<M> getPromociones();
    
    public List<L> getPagos();

	//public void setPagos(List<P> pagos);

	public List<CuponAplicadoTicket> getCuponesAplicados();
	
	public List<CuponEmitidoTicket> getCuponesEmitidos();
	
	public Long getIdTratImpuestos();

	String getSchemaVersion();

	void setSchemaVersion(String schemaVersion);

	String getSoftwareVersion();

	void setSoftwareVersion(String softwareVersion);

	String getLocalCopyVersion();

	void setLocalCopyVersion(String localCopyVersion);
   
}
