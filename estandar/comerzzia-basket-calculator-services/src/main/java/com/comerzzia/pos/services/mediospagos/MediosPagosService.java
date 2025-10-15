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

package com.comerzzia.pos.services.mediospagos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comerzzia.core.basketcalculator.model.mediospago.configuracion.ConfiguracionMedioPagoBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.configuraciones.ConfiguracionPasarelaBean;
import com.comerzzia.core.basketcalculator.model.pasarelas.tipos.TipoPasarelaBean;
import com.comerzzia.core.basketcalculator.servicios.mediospago.configuracion.ConfiguracionMedioPagoService;
import com.comerzzia.core.basketcalculator.servicios.pasarelas.configuraciones.ConfiguracionPasarelaService;
import com.comerzzia.core.basketcalculator.servicios.pasarelas.tipos.TipoPasarelaService;
import com.comerzzia.core.basketcalculator.servicios.sesion.DatosSesionBean;
import com.comerzzia.pos.persistence.core.documentos.tipos.TipoDocumentoBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoExample;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoKey;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoMapper;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.sesion.SesionAplicacion;
import com.comerzzia.pos.services.core.sesion.paymentMethods.PaymentMethodsData;
import com.comerzzia.pos.util.i18n.I18N;

@Service
public class MediosPagosService {
    protected final Logger log = Logger.getLogger(MediosPagosService.class);
    
    @Autowired
    Sesion sesion;
    
    @Autowired
    protected MedioPagoMapper medioPagoMapper;
    
    @Autowired
    private TipoPasarelaService tipoPasarelaService;
    
    @Autowired
    private ConfiguracionMedioPagoService confMedioPagoService;
    
    @Autowired
    private ConfiguracionPasarelaService confPasarelaService;
    
    protected static final String COD_MED_PAGO_VALE = "0020";
    
    /** Carga en memoria los medios de pago configurados para el sistema.
     * Si alguno de los medios de pago especiales configurados para la tienda
     * no existe, lanzará una excepción.
     * @param uidActividad UidActividad con la que trabaja la aplicación
     * @param tienda Tienda con la que trabaja la aplicación
     * @throws MediosPagoServiceException 
     */
    public void cargarMediosPago(SesionAplicacion sesionAplicacion, PaymentMethodsData paymentMethodsCacheData) throws MediosPagoServiceException {

        try {
            Map<String, MedioPagoBean> mediosPago = paymentMethodsCacheData.getMediosPago();
            List<MedioPagoBean> mediosPagoTarjetas = paymentMethodsCacheData.getMediosPagoTarjetas();
            List<MedioPagoBean> mediosPagoContado = paymentMethodsCacheData.getMediosPagoContado();
            List<MedioPagoBean> mediosPagoVisibleVenta = paymentMethodsCacheData.getMediosPagoVisibleVenta();
                        
            // limpiar valores actuales
            mediosPago.clear();
            mediosPagoTarjetas.clear();
            mediosPagoContado.clear();
            mediosPagoVisibleVenta.clear();
            
            // consultamos la base de datos
            MedioPagoExample exampleMedioPago = new MedioPagoExample();
			exampleMedioPago.or().andUidActividadEqualTo(sesionAplicacion.getUidActividad()).andActivoEqualTo(Boolean.TRUE);
            log.debug("cargarMediosPago() - Cargando medios de pago para el uidActividad " + sesionAplicacion.getUidActividad());
            List<MedioPagoBean> listaMediosPagos = medioPagoMapper.selectByExample(exampleMedioPago);
            
            consultarPasarelas(sesionAplicacion, listaMediosPagos);
            
            // construimos mapa y listas
            for (MedioPagoBean medioPago : listaMediosPagos) {
                mediosPago.put(medioPago.getCodMedioPago(), medioPago);
                if(medioPago.getVisibleVenta()){
                	if(medioPago.getManual()){
                		mediosPagoVisibleVenta.add(medioPago);
		                if (medioPago.getTarjetaCredito()){
		                    mediosPagoTarjetas.add(medioPago);
		                }else{
		                    mediosPagoContado.add(medioPago);
		                }
                	}
                }
            }
            
            // tratamos medios de pago especiales
            String codMedioPagoDefecto = sesionAplicacion.getTienda().getTiendaBean().getCodMedioPagoPorDefecto();
            String codMedioPagoPromocional = sesionAplicacion.getTienda().getTiendaBean().getCodMedioPagoPromocion();
            String codMedioPagoEntregaCuenta = sesionAplicacion.getTienda().getTiendaBean().getCodMedioPagoApartado();
            
            MedioPagoBean medioPagoDefecto = mediosPago.get(codMedioPagoDefecto);
            if (medioPagoDefecto == null){
                log.error("cargarMediosPago() - No existe medio de pago por defecto");
                throw new MediosPagoServiceException(I18N.getTexto("No existe el medio de pago configurado para la tienda como Por Defecto"));
            }
            paymentMethodsCacheData.setMedioPagoDefecto(medioPagoDefecto);
            
            MedioPagoBean medioPagoPromocional = mediosPago.get(codMedioPagoPromocional);
            if (medioPagoPromocional == null){
                log.error("cargarMediosPago() - No existe medio de pago promocional");
                throw new MediosPagoServiceException(I18N.getTexto("No existe el medio de pago configurado para la tienda como Promocional"));
            }
            paymentMethodsCacheData.setMedioPagoPromocional(medioPagoPromocional);
            
            MedioPagoBean medioPagoEntregaCuenta = mediosPago.get(codMedioPagoEntregaCuenta);
            if (medioPagoEntregaCuenta == null){
                log.error("cargarMediosPago() - No existe medio de pago de entrega a cuenta");
                throw new MediosPagoServiceException(I18N.getTexto("No existe el medio de pago configurado para la tienda como Entrega a Cuenta"));
            }
            paymentMethodsCacheData.setMedioPagoEntregaCuenta(medioPagoEntregaCuenta);
            
            mediosPagoContado.remove(medioPagoDefecto);
            mediosPagoContado.remove(medioPagoEntregaCuenta);
            mediosPagoContado.remove(medioPagoPromocional);
        }
        catch(MediosPagoServiceException e){
            throw e;
        }
        catch (Exception e) {
            String msg = "Se ha producido un error cargando los medios de pagos: " + e.getMessage();
            log.error("cargarMediosPago() - " + msg, e);
            throw new MediosPagoServiceException(e);
        }
    }    

	protected void consultarPasarelas(SesionAplicacion sesionAplicacion, List<MedioPagoBean> listaMediosPagos) {
		for (MedioPagoBean medioPago : listaMediosPagos) {
			try {
				consultarPasarela(sesionAplicacion, medioPago);
			}
			catch (Exception e) {
				log.error("consultarPasarela() - Ha habido un error al consultar la pasarela para los medios de pago: " + e.getMessage(), e);
			}
		}
	}

	protected void consultarPasarela(SesionAplicacion sesionAplicacion, MedioPagoBean medioPago)  {
		String codMedioPago = medioPago.getCodMedioPago();
		
		DatosSesionBean datosSesion = new DatosSesionBean();
		datosSesion.setUidActividad(sesionAplicacion.getUidActividad());
		
		ConfiguracionMedioPagoBean configMedPago = confMedioPagoService.selectByPrimaryKey(codMedioPago, "D_TIENDAS_CAJAS_TBL.UID_CAJA", sesionAplicacion.getTerminalId(), datosSesion);
		if(configMedPago == null) {
			configMedPago = confMedioPagoService.selectByPrimaryKey(codMedioPago, "D_TIENDAS_TBL.CODALM", sesionAplicacion.getCodAlmacen(), datosSesion);
		}
		if(configMedPago == null) {
			configMedPago = confMedioPagoService.selectByPrimaryKey(codMedioPago, "D_MEDIOS_PAGO_TBL.CODMEDPAG", codMedioPago, datosSesion);
		}
		if (configMedPago != null) {					
			ConfiguracionPasarelaBean configuracionPasarela = confPasarelaService.consultarConfiguracion(configMedPago.getIdConfPasarela(), datosSesion);
			medioPago.setConfigPasarela(configuracionPasarela);
			
			if(configuracionPasarela != null) {
				TipoPasarelaBean tipoPasarela = tipoPasarelaService.consultar(configuracionPasarela.getIdTipoPasarela(), datosSesion);
				medioPago.setTipoPasarela(tipoPasarela);
			}
		}
	}

    /**
     * Devuelve el medio de pago con el código indicado. El medio de pago debe
     * de estar activo. Si el medio de pago no existe, se lanza una excepción.
     *
     * @param codMedioPago :: código del medio de pago que se quiere consultar
     * @return :: MedioPago
     * @throws MediosPagoServiceException
     * @throws MedioPagoNotFoundException :: Lanzada si el medio de pago no
     * existe.
     */
    public MedioPagoBean consultarMedioPago(String codMedioPago) throws MediosPagoServiceException, MedioPagoNotFoundException {
        try {
            MedioPagoKey keyMedioPago = new MedioPagoKey();
            keyMedioPago.setCodMedioPago(codMedioPago);
            keyMedioPago.setUidActividad(sesion.getUidActividad());
            log.debug("consultarMediosPago() - Realizando consulta de medio de pago");
            MedioPagoBean medioPagoBean = medioPagoMapper.selectByPrimaryKey(keyMedioPago);
            if (medioPagoBean != null && medioPagoBean.getActivo()) {
                return medioPagoBean;
            }
            else {
                throw new MedioPagoNotFoundException();
            }
        }
        catch (Exception e) {
            String msg = "Se ha producido un error consultando los medios de pagos con codigo " + codMedioPago + " : " + e.getMessage();
            log.error("consultarMediosPago() - " + msg);
            throw new MediosPagoServiceException(e);
        }
    }
    
    public MedioPagoBean getMedioPago(String codMedioPago){
        return sesion.getSesionCaja().getPaymentMethodsData().getMediosPago().get(codMedioPago);
    }
    
    public boolean isCodMedioPagoVale(String codMedioPago, Long idTipoDocumento){
    	boolean isCodMedioPagoVale = false;
    	try {
    		if(idTipoDocumento != null) {
		        TipoDocumentoBean documento = sesion.getAplicacion().getDocumentos().getDocumento(idTipoDocumento);
		        String codFormaPagoVale = documento.getFormaPagoVale();
		        if(codFormaPagoVale.equals(TipoDocumentoBean.PROPIEDAD_FORMA_PAGO_VALE + " NO CONFIGURADO")) {
		        	log.warn("isCodMedioPagoVale() - Medio de pago VALE para este documento no configurado. Se usará 0020 por defecto.");
		        	isCodMedioPagoVale = codMedioPago.equals(COD_MED_PAGO_VALE);
		        }
		        else {
		        	isCodMedioPagoVale = codFormaPagoVale.equals(codMedioPago);
		        }
    		}
    		else {
    			return false;
    		}
        }
        catch (Exception e) {
        	log.error("isCodMedioPagoVale() - Ha habido un error al buscar la propiedad del medio de pago: " + e.getMessage(), e);
        	isCodMedioPagoVale = codMedioPago.equals(COD_MED_PAGO_VALE);
        }
        return isCodMedioPagoVale;
    }
    
    public List<MedioPagoBean> getMediosPagoAutomaticoNoManuales(){
    	List<MedioPagoBean> mediosPagoAuto = new LinkedList<>();
    	for (MedioPagoBean medioPagoBean : sesion.getSesionCaja().getPaymentMethodsData().getMediosPago().values()) {
			if(!medioPagoBean.getManual() || medioPagoBean.getRecuentoAutomaticoCaja()){
				mediosPagoAuto.add(medioPagoBean);
			}
		}
    	return mediosPagoAuto;
    }
    
    public List<String> getCodMediosPagoAutomatico(){
    	List<String> codigos = new ArrayList<String>();
    	for(MedioPagoBean medioPago:sesion.getSesionCaja().getPaymentMethodsData().getMediosPago().values()){
    		if(medioPago.getRecuentoAutomaticoCaja()){
    			codigos.add(medioPago.getCodMedioPago());
    		}
    	}
    	return codigos;
    }
    
}
