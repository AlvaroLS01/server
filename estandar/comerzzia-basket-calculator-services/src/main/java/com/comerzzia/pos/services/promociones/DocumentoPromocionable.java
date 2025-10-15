package com.comerzzia.pos.services.promociones;

import java.util.List;
import java.util.Map;

import com.comerzzia.pos.persistence.clientes.ClienteBean;
import com.comerzzia.pos.persistence.fidelizacion.CustomerCouponDTO;
import com.comerzzia.pos.persistence.fidelizacion.FidelizacionBean;
import com.comerzzia.pos.services.ticket.cabecera.ICabeceraTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponAplicadoTicket;
import com.comerzzia.pos.services.ticket.cupones.CuponEmitidoTicket;
import com.comerzzia.pos.services.ticket.promociones.IPromocionTicket;

@SuppressWarnings("rawtypes")
public interface DocumentoPromocionable<M extends IPromocionTicket> {

	public boolean isAdmitePromociones();

	public List<M> getPromociones();

	public M getPromocion(Long idPromocion);
	
	public M getPromocion(Long idPromocion, String codCupon);

	public void addPromocion(M promocionTicket);

	public void addPuntos(Integer puntos);

	public void resetPuntos();

	public List<CuponEmitidoTicket> getCuponesEmitidos();

	public void addCuponEmitido(CuponEmitidoTicket cupon);

	public List<CuponAplicadoTicket> getCuponesAplicados();

	public CuponAplicadoTicket getCuponAplicado(String codigo);

	public void addCuponAplicado(CuponAplicadoTicket cupon);

	public List<LineaDocumentoPromocionable> getLineasDocumentoPromocionable();

	public FidelizacionBean getDatosFidelizado();
	
	public ClienteBean getCliente();

	public String getCodTarifa();
	
    public ICabeceraTicket getCabecera();
	
	public Map<String, Object> getExtensiones();
	
	public void addExtension(String clave, Object valor);
	
	public Object getExtension(String clave);
		
	public void clearExtensiones();
	
	public List<CustomerCouponDTO> getCouponsAppliyingPromotion(Long idPromocion);
	
	public void addCouponAppliyingPromotion(Long idPromocion, CustomerCouponDTO customerCouponDTO);
	
	public void removeCouponsAppliyingPromotion(Long idPromocion);
	
	public void removeCouponsAppliyingPromotion(String couponCode);
	
	public Map<Long, List<CustomerCouponDTO>> getMapCouponsAppliyingPromotion();

}
