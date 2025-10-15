package com.comerzzia.pos.services.payments.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comerzzia.core.basketcalculator.util.xml.XMLDocument;
import com.comerzzia.core.basketcalculator.util.xml.XMLDocumentNode;
import com.comerzzia.pos.persistence.core.tiendas.cajas.TiendaCajaBean;
import com.comerzzia.pos.persistence.mediosPagos.MedioPagoBean;
import com.comerzzia.pos.services.core.sesion.Sesion;
import com.comerzzia.pos.services.core.sesion.SesionAplicacion;
import com.comerzzia.pos.services.core.sesion.paymentMethods.PaymentMethodsData;
import com.comerzzia.pos.services.core.tiendas.cajas.TiendaCajaService;

@Component
public class PaymentsMethodsConfigurationImpl implements PaymentsMethodsConfiguration {
	
	public static final String PARAM_DESCRIPCION_GATEWAY = "desc_gateway";

	private Logger log = Logger.getLogger(getClass());
	
	//protected List<PaymentMethodConfiguration> paymentMethodConfigurations;
	
	protected XMLDocumentNode storeConfigXml;
	
	@Autowired
    private TiendaCajaService tiendaCajaService;
	
	@Autowired
	Sesion sesion;

	public PaymentsMethodsConfigurationImpl() {
		super();
	}

	@Override
	public List<PaymentMethodConfiguration> getPaymentsMethodsConfiguration() {	
		return sesion.getSesionCaja().getPaymentMethodsData().getPaymentMethodConfigurations();
	}

	@Override
	public void loadConfiguration(SesionAplicacion sesion, PaymentMethodsData paymentMethodsData) {
		List<PaymentMethodConfiguration> newPaymentMethodConfigurations = new ArrayList<PaymentMethodConfiguration>();
		
		Map<String, MedioPagoBean> mediosPago = paymentMethodsData.getMediosPago();
		
		for(MedioPagoBean medioPago : mediosPago.values()) {
			
			String controlClass = null;
			if(StringUtils.isBlank(controlClass)) {
				controlClass = medioPago.getClaseControl();
			}
			
			PaymentMethodConfiguration paymentMethodConfiguration = new PaymentMethodConfigurationImpl(medioPago.getCodMedioPago(), controlClass);
			
			Map<String, String> paramsGateway = loadPaymentGateway(medioPago);
			for(String key : paramsGateway.keySet()) {
				paymentMethodConfiguration.putGatewayConfigurationProperty(key, paramsGateway.get(key));
			}
			
			Map<String, String> paramsLocal = loadLocalConfiguration(sesion, medioPago);
			for(String key : paramsLocal.keySet()) {
				paymentMethodConfiguration.putStoreConfigurationProperty(key, paramsLocal.get(key));
			}
			
			newPaymentMethodConfigurations.add(paymentMethodConfiguration);
		}
		
		paymentMethodsData.setPaymentMethodConfigurations(newPaymentMethodConfigurations);
	}

	protected Map<String, String> loadLocalConfiguration(SesionAplicacion sesion, MedioPagoBean medioPago) {
		Map<String, String> params = new HashMap<String, String>();
		
		try {
			if(storeConfigXml == null) {
				TiendaCajaBean tiendaCaja = tiendaCajaService.consultarTPV(sesion.getUidActividad(), sesion.getTerminalId());
				XMLDocument xmlConfiguracion = new XMLDocument(tiendaCaja.getConfiguracion());
				
				storeConfigXml = xmlConfiguracion.getRoot();
			}
			
			XMLDocumentNode paymentsMethodNode = storeConfigXml.getNodo("medios_pago", true);
			if(paymentsMethodNode != null) {
				for(XMLDocumentNode paymentMethodNode : paymentsMethodNode.getHijos()) {
					XMLDocumentNode paymentCodeNode = paymentMethodNode.getNodo("cod_medio_pago", true);
					if(paymentCodeNode != null) {
						String paymentCode = paymentCodeNode.getValue();
						if(paymentCode.equals(medioPago.getCodMedioPago())) {
							XMLDocumentNode parametersNode = paymentMethodNode.getNodo("parametros");
							for(XMLDocumentNode parameterNode : parametersNode.getHijos()) {
								params.put(parameterNode.getNombre(), parameterNode.getValue());
							}
							break;
						}
					}
				}
			}
		}
		catch(Exception e) {
			log.error("loadLocalConfiguration() - Ha ocurrido un error al cargar la configuración de la tienda: " + e.getMessage(), e);
		}
		
		return params;
	}

	protected Map<String, String> loadPaymentGateway(MedioPagoBean medioPago) {
		Map<String, String> params = new HashMap<String, String>();
		
		try {
			if(medioPago.getConfigPasarela() != null) {
				params.put(PARAM_DESCRIPCION_GATEWAY, medioPago.getConfigPasarela().getDesconfpasarela());
				
				XMLDocument xml = new XMLDocument(medioPago.getConfigPasarela().getConfiguracion());
				
				XMLDocumentNode configurationNode = xml.getRoot();
				for(XMLDocumentNode node : configurationNode.getHijos()) {
					String key = node.getNombre();
					String value = node.getValue();
					params.put(key, value);
				}
			}
		}
		catch(Exception e) {
			log.error("loadPaymentGateway() - Ha ocurrido un error al cargar la configuración de la pasarela: " + e.getMessage(), e);
		}
		
		return params;
	}

	@Override
	public void saveConfiguration() throws Exception {
		TiendaCajaBean tiendaCaja = tiendaCajaService.consultarTPV(sesion.getUidActividad(), sesion.getAplicacion().getTerminalId());
		XMLDocument xmlConfiguracion = new XMLDocument(tiendaCaja.getConfiguracion());
		
		storeConfigXml = xmlConfiguracion.getRoot();
		
		XMLDocumentNode paymentsMethodsNode = storeConfigXml.getNodo("medios_pago", true);
		if(paymentsMethodsNode == null) {
			paymentsMethodsNode = new XMLDocumentNode(xmlConfiguracion, "medios_pago");
		}
		else {
			storeConfigXml.getNode().removeChild(paymentsMethodsNode.getNode());
		}
		
		paymentsMethodsNode = new XMLDocumentNode(xmlConfiguracion, "medios_pago");
		for(PaymentMethodConfiguration configuration : getPaymentsMethodsConfiguration()) {
			XMLDocumentNode paymentMethodNode = new XMLDocumentNode(xmlConfiguracion, "medio_pago");
			
			paymentMethodNode.añadirHijo("cod_medio_pago", configuration.getPaymentCode());
			
			XMLDocumentNode paymentMethodParamsNode = new XMLDocumentNode(xmlConfiguracion, "parametros");
			for(String key : configuration.getStoreConfigurationProperties().keySet()) {
				String value = configuration.getStoreConfigurationProperty(key);
				
				paymentMethodParamsNode.añadirHijo(key, value);
			}
			paymentMethodNode.añadirHijo(paymentMethodParamsNode);
			
			paymentsMethodsNode.añadirHijo(paymentMethodNode);
		}
		storeConfigXml.añadirHijo(paymentsMethodsNode);
		
		tiendaCaja.setConfiguracion(xmlConfiguracion.getBytes());
		tiendaCajaService.grabarConfiguracionDispositivos(tiendaCaja);
	}

}
