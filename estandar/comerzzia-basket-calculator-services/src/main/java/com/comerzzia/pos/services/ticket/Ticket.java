package com.comerzzia.pos.services.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.comerzzia.pos.persistence.clientes.ClienteBean;
import com.comerzzia.pos.persistence.core.empresas.EmpresaBean;
import com.comerzzia.pos.persistence.core.usuarios.UsuarioBean;
import com.comerzzia.pos.persistence.tickets.datosfactura.DatosFactura;
import com.comerzzia.pos.services.articulos.tarifas.ArticulosTarifaService;
import com.comerzzia.pos.services.core.tiendas.Tienda;
import com.comerzzia.pos.services.ticket.cabecera.ICabeceraTicket;
import com.comerzzia.pos.services.ticket.cabecera.ITotalesTicket;
import com.comerzzia.pos.services.ticket.lineas.ILineaTicket;
import com.comerzzia.pos.services.ticket.pagos.IPagoTicket;

@SuppressWarnings("rawtypes")
public abstract class Ticket<L extends ILineaTicket, P extends IPagoTicket, C extends ICabeceraTicket> implements ITicket {
	protected Logger log = Logger.getLogger(getClass());
	
	protected String schemaVersion;	
	protected String softwareVersion;	
	protected String localCopyVersion;
	
	protected C cabecera;
	protected List<L> lineas;
	protected List<P> pagos;
	
	protected LinkedHashSet<String> tarifas;
	
	protected Map<String, Object> extensiones;
	
	public Ticket() {
		lineas = new ArrayList<>();
		pagos = new ArrayList<>();
        tarifas = new LinkedHashSet<String>();
	}
	
	@Override
    public String getSchemaVersion() {
		return schemaVersion;
	}

	@Override
	public void setSchemaVersion(String schemaVersion) {
		this.schemaVersion = schemaVersion;
	}	
	
	@Override
    public String getSoftwareVersion() {
		return softwareVersion;
	}

	@Override
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	@Override
	public String getLocalCopyVersion() {
		return localCopyVersion;
	}

	@Override
	public void setLocalCopyVersion(String localCopyVersion) {
		this.localCopyVersion = localCopyVersion;
	}

	public String getUidTicket() {
		return getCabecera().getUidTicket();
	}

	public String getCodCaja() {
		return getCabecera().getCodCaja();
	}

	public Tienda getTienda() {
		return getCabecera().getTienda();
	}

	public String getUidActividad() {
		return getCabecera().getUidActividad();
	}

	public String getUidDiarioCaja() {
		return getCabecera().getUidDiarioCaja();
	}

	public EmpresaBean getEmpresa() {
		return getCabecera().getEmpresa();
	}

	public ITotalesTicket getTotales() {
		return getCabecera().getTotales();
	}

	public Date getFecha() {
		return getCabecera().getFecha();
	}

	public Long getIdTicket() {
		return getCabecera().getIdTicket();
	}

	public ClienteBean getCliente() {
		return getCabecera().getCliente();
	}

	public UsuarioBean getCajero() {
		return getCabecera().getCajero();
	}

	public void setFecha(Date date) {
		getCabecera().setFecha(date);
	}

	public void setFechaContable(Date fechaContable) {
		getCabecera().setFechaContable(fechaContable);
	}

	public void setIdTicket(Long idTicket) {
		getCabecera().setIdTicket(idTicket);
	}

	public void setCliente(ClienteBean cliente) {
		getCabecera().setCliente(cliente);
	}

	public void setDatosFacturacion(DatosFactura datosFactura) {
		getCabecera().getCliente().setDatosFactura(datosFactura);
	}

	public DatosFactura getDatosFacturacion() {
		return getCabecera().getCliente().getDatosFactura();
	}

	public void setCajero(UsuarioBean cajero) {
		getCabecera().setCajero(cajero);
	}
		
	public C getCabecera() {
		return cabecera;
	}
	
	public void setCabecera(C cabecera) {
		this.cabecera = cabecera;
	}

	public List<L> getLineas() {
		return lineas;
	}
		
    public void addLinea(L linea) {
    	lineas.add(linea);
    }

	public void setLineas(List<L> lineas) {
		this.lineas = lineas;
	}

	public List<P> getPagos() {
		return pagos;
	}

	public void setPagos(List<P> pagos) {
		this.pagos = pagos;
	}

	public L getLinea(Integer idLinea){
        for (L linea : lineas) {
            if (linea.getIdLinea().equals(idLinea)){
                return linea;
            }
        }
        return null;
    }
	
    public List<L> getLinea (String codArticulo){        
        List<L> lineasArticulo = new ArrayList<L>();
        
        for (L linea : lineas) {
            if (linea.getArticulo().getCodArticulo().equals(codArticulo)){
                lineasArticulo.add(linea);
            }
        }
        return lineasArticulo;
    }
	
	public P getPago(String codMedioPago) {
        for (P pagoTicket : pagos) {
            if (pagoTicket.getMedioPago().getCodMedioPago().equals(codMedioPago)) {
                return pagoTicket;
            }
        }
        return null;
    }

    public void addPago(P pago) {
    	pagos.add(pago);
    }
    
    /**
     * Elimina todos los pagos con codigo codMedioPago de la lista de pagos
     *
     * @param codMedioPago
     */
    public void removePago(String codMedioPago) {
    	ListIterator<P> listIterator = pagos.listIterator();
    	while(listIterator.hasNext()){
			P pagoTicket = listIterator.next(); 
            if (pagoTicket.getMedioPago().getCodMedioPago().equals(codMedioPago)) {
                listIterator.remove();
            }
        }
    }
        
	public String getCodTarifa() {
    	if (tarifas != null && tarifas.size() > 0) {
    		return (String)tarifas.iterator().next();
    	}
    	
		ClienteBean cliente = getCabecera().getCliente();
		if (cliente != null && cliente.getCodtar() != null) {
			return cliente.getCodtar();
		}
		
		Tienda tienda = getCabecera().getTienda();
		ClienteBean clienteTienda = tienda.getCliente();
		if (clienteTienda != null && clienteTienda.getCodtar() != null){
			return clienteTienda.getCodtar();
		} else {
			return ArticulosTarifaService.COD_TARIFA_GENERAL;
		}
	}
	
	public Map<String, Object> getExtensiones() {
		if(this.extensiones == null) {
			this.extensiones = new HashMap<String, Object>();
		}
	    return extensiones;
    }

    public void addExtension(String clave, Object valor) {
		if(this.extensiones == null) {
			this.extensiones = new HashMap<String, Object>();
		}
		this.extensiones.put(clave, valor);
    }

    public Object getExtension(String clave) {
		if(this.extensiones == null) {
			this.extensiones = new HashMap<String, Object>();
		}
		return extensiones.get(clave);
    }

    public void clearExtensiones() {
		if(this.extensiones != null) {
			this.extensiones.clear();
		}
    }
}